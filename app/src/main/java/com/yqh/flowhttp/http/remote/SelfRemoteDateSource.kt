package com.yqh.flowhttp.http.remote

import com.yqh.flowhttp.http.api.ApiService
import com.yqh.flowhttp.http.config.Config
import com.yqh.lib.flowhttp.source.RemoteSource
import com.yqh.lib.flowhttp.viewmodel.IUIActionEvent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SelfRemoteDateSource(uiActionEvent: IUIActionEvent) :
    RemoteSource<ApiService>(uiActionEvent, ApiService::class.java) {

    companion object {
        private val okhttpClient: OkHttpClient by lazy {
            createHttpClient()
        }

        private fun createHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .readTimeout(1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(1000L, TimeUnit.MILLISECONDS)
                .connectTimeout(1000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
            return builder.build()
        }
    }

    override val baseUrl: String
        get() = Config.BASE_URL

    override fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun exceptionRecord(exception: Throwable) {
        /**
         * 网络请求的异常，统一在此处都可以获取得到
         */
    }
}