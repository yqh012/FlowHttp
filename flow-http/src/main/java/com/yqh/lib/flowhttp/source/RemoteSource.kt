package com.yqh.lib.flowhttp.source

import com.yqh.lib.flowhttp.bean.IHttpWrapBean
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
    ): Job {
        return enqueue(api = api, showLoading = true, baseUrl = baseUrl, callbackFun = callback)
    }

    /**
     * 不带有 loadding 的请求方式
     */
    fun <Response> enqueue(
        api: suspend Api.() -> IHttpWrapBean<Response>,
        showLoading: Boolean = false,
        baseUrl: String,
        callbackFun: (RequestCallback<Response>.() -> Unit)? = null
    ): Job = lifecycleSupportScope.launch(Dispatchers.Main) {
        val callback = if (callbackFun == null) null else RequestCallback<Response>().apply {
            callbackFun.invoke(this)
        }

        flow {
            val response = api.invoke(getApiServer(baseUrl)).also {
                if (!it.success) throw ServerCodeBadException(it)
            }
            emit(response.data)
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                if (showLoading) showLoading()
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
                        callback?.onCompletion?.invoke()
                    }
                }
            }
    }


}