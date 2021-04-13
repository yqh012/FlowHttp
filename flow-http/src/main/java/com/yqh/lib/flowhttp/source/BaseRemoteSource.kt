package com.yqh.lib.flowhttp.source

import android.util.LruCache
import com.yqh.lib.flowhttp.callback.BaseRequestCallback
import com.yqh.lib.flowhttp.coroutine.ICoroutine
import com.yqh.lib.flowhttp.exception.BaseHttpException
import com.yqh.lib.flowhttp.exception.LocalBadException
import com.yqh.lib.flowhttp.exception.ServerCodeBadException
import com.yqh.lib.flowhttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

abstract class BaseRemoteSource<Api : Any>(
    protected val actionEvent: IUIActionEvent?,
    protected val apiServiceClass: Class<Api>
) : ICoroutine {
    companion object {
        /**
         * ApiService 缓存
         */
        private val apiServiceCache = LruCache<String, Any>(30)

        /**
         * Retrofit 缓存
         */
        private val retrofitCache = LruCache<String, Retrofit>(3)

        /**
         * 构建默认的 OkHttpClient
         */
        private val defaultOkHttpClient by lazy {
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }

        /**
         * 构建默认的 Retrofit
         */
        private fun createDefaultRetrofit(baseUrl: String): Retrofit =
            Retrofit.Builder()
                .client(defaultOkHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    /**
     * 和生命周期绑定的协程作用域
     */
    override val lifecycleSupportScope: CoroutineScope
        get() = actionEvent?.lifecycleSupportScope ?: GlobalScope


    /**
     * 子类实现此字段以获取baseUrl，默认的 BaseUrl
     */
    protected abstract val baseUrl: String

    /**
     * 子类自定义实现 Retrofit
     */
    protected open fun createRetrofit(baseUrl: String): Retrofit = createDefaultRetrofit(baseUrl)

    /**
     * 过滤 baseUrl 是否为空，若为空则使用默认的BaseUrl
     */
    protected fun generateBaseUrl(baseUrl: String): String =
        baseUrl.takeIf { it.isNotBlank() } ?: this.baseUrl

    fun getApiServer(baseUrl: String = ""): Api =
        getApiService(generateBaseUrl(baseUrl), apiServiceClass)

    /**
     * 获取 ApiServer
     */
    private fun getApiService(baseUrl: String, apiServiceClass: Class<Api>): Api {
        val key = baseUrl + apiServiceClass.canonicalName
        val get = apiServiceCache.get(key)?.let {
            it as? Api
        }
        //先从缓存中取
        if (get != null) return get

        //若未取到，则通过 retrofit 创建新的 apiService 并存入缓存池中。
        val retrofit = retrofitCache.get(baseUrl) ?: createRetrofit(baseUrl).apply {
            retrofitCache.put(baseUrl, this)
        }

        return retrofit.create(apiServiceClass).apply {
            apiServiceCache.put(key, this)
        }
    }

    protected fun handleException(exception: Throwable, callback: BaseRequestCallback?) {
        if (callback == null) return
        when (exception) {
            is CancellationException -> {
                callback.onCancelled?.invoke()
            }
            else -> {
                val httpException = generateBaseExceptionReal(exception)
                if (exceptionHandle(httpException)) {
                    callback.onFailed?.invoke(httpException)
                    if (callback.onFailToast()) {
                        exceptionFormat(httpException).takeIf { it.isNotBlank() }
                            ?.let { showToast(it) }
                    }
                }
            }
        }
    }

    internal fun generateBaseExceptionReal(exception: Throwable): BaseHttpException =
        generateBaseException(exception).also { exceptionRecord(it) }

    /**
     * 如果外部需要对 exception 进行特殊处理，则可以重写此方法，用于改变 Exception的类型
     * 例如，在 token 失效时接口一般会返回特定的 errorCode 用于表明移动端需要更新 token 了
     * 此时外部就可以实现一个 BaseHttpException 的子类 TokenInvalidException 并在此处返回
     * 从而做到接口异常原因强提醒的效果，而不用去纠结 errorCode 是多少，通过异常来判断错误类型
     */
    protected fun generateBaseException(exception: Throwable): BaseHttpException =
        if (exception is ServerCodeBadException) exception else LocalBadException(exception)

    /**
     * 将网络请求过程中出现的异常反馈到外部，以便记录问题
     */
    protected open fun exceptionRecord(exception: Throwable) {}

    /**
     * 用于由外部来控制当抛出异常时是否走 onFail 回调，
     * 返回 true 则回调 onFail
     * 返回 false 则不回调
     */
    protected open fun exceptionHandle(httpException: BaseHttpException): Boolean = true

    /**
     * 用于对 BaseHttpException 进行格式化
     * 以便在请求失败时弹出错误信息
     */
    protected open fun exceptionFormat(httpException: BaseHttpException): String =
        when (httpException.exception) {
            null -> httpException.errorMessage
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException -> "链接超时，请检查您的网络设置。"
            else -> "请求过程中抛出异常，code : ${httpException?.errorCode} , message : ${httpException.errorMessage}"
        }

    protected fun showLoading() {
        actionEvent?.showLoading()
    }

    protected fun hideLoading() {
        actionEvent?.hideLoading()
    }

    abstract fun showToast(msg: String)
}