package com.yqh.lib.flowhttp.source

import com.yqh.lib.flowhttp.bean.IHttpWrapBean
import com.yqh.lib.flowhttp.cache.CacheStrategy
import com.yqh.lib.flowhttp.callback.RequestCallback
import com.yqh.lib.flowhttp.exception.ServerCodeBadException
import com.yqh.lib.flowhttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class RemoteSource<Api : Any>(
    uiActionEvent: IUIActionEvent,
    apiServiceClass: Class<Api>
) : BaseRemoteSource<Api>(uiActionEvent, apiServiceClass) {

    /**
     * 带有 loading 的请求方式
     */
    fun <Response> enqueueLoading(
        api: suspend Api.() -> IHttpWrapBean<Response>,
        baseUrl: String = "",
        callback: (RequestCallback<Response>.() -> Unit)? = null
    ): Job = enqueue(
        api = api,
        showLoading = true,
        baseUrl = baseUrl,
        callbackFun = callback
    )


    /**
     * 不带有 loadding 的请求方式
     */
    fun <Response> enqueue(
        api: suspend Api.() -> IHttpWrapBean<Response>,
        showLoading: Boolean = false,
        baseUrl: String = "",
        callbackFun: (RequestCallback<Response>.() -> Unit)? = null
    ): Job = lifecycleSupportScope.launch(Dispatchers.Main) {
        val callback = if (callbackFun == null) null else RequestCallback<Response>().apply {
            callbackFun.invoke(this)
        }
        val currentJob = coroutineContext[Job]
        flow {
            val response = api.invoke(getApiServer(baseUrl)).also {
                if (!it.success) throw ServerCodeBadException(it)
            }
            emit(response.data)
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                if (showLoading) showLoading(currentJob)
                callback?.onStart?.invoke()
            }
            .catch {
                handleException(it, callback)
            }
            .onCompletion {
                callback?.onCompletion?.invoke()
                if (showLoading) hideLoading()
            }
            .collectLatest { response ->
                callback?.let {
                    it.onSuccess?.invoke(response)
                }

                withContext(NonCancellable) {
                    withContext(Dispatchers.IO) {
                        callback?.onSuccessIO?.invoke(response)
                    }
                }
            }
    }

    /**
     * 支持多个请求同步请求
     * 带有loading框
     */
    fun enqueueLoadings(
        baseUrl: String = "",
        callback: (RequestCallback<*>.() -> Unit)? = null,
        vararg apis: suspend Api.() -> IHttpWrapBean<*>
    ): Job = enqueues(true, baseUrl, callback, *apis)


    /**
     * 支持多个请求同步请求
     * 默认不带有loading框
     */
    fun enqueues(
        showLoading: Boolean = false,
        baseUrl: String,
        callbackFun: (RequestCallback<*>.() -> Unit)? = null,
        vararg apis: suspend Api.() -> IHttpWrapBean<*>
    ): Job = lifecycleSupportScope.launch(Dispatchers.Main) {
        val callback = if (callbackFun == null) null else RequestCallback<Any>().apply {
            callbackFun.invoke(this)
        }
        supervisorScope {
            val currentJob = coroutineContext[Job]
            try {

                callback?.onStart?.invoke()
                if (showLoading) showLoading(currentJob)
                try {
                    val response = apis.map {
                        async { it.invoke(getApiServer(baseUrl)) }
                    }.awaitAll()

                    val failed = response.find { it.httpIsFailed }
                    if (failed != null) throw ServerCodeBadException(failed)
                    getResponse(callback, response)

                } catch (exception: Exception) {
                    println(exception.message)
                    handleException(exception, callback)
                }

            } finally {
                try {
                    callback?.onCompletion?.invoke()
                } finally {
                    if (showLoading) hideLoading()
                }
            }
        }
    }

    private suspend fun getResponse(
        callback: RequestCallback<Any>?,
        response: List<IHttpWrapBean<*>>
    ) {
        callback?.let {
            val realResponse = response?.map { it.data }
            withContext(NonCancellable) {
                callback.onSuccess?.let {
                    withContext(Dispatchers.Main) {
                        it.invoke(realResponse)
                    }
                }

                callback.onSuccessIO?.let {
                    withContext(Dispatchers.IO) {
                        it.invoke(realResponse)
                    }
                }
            }
        }
    }

}