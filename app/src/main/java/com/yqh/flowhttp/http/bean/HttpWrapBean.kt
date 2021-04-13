package com.yqh.flowhttp.http.bean

import com.google.gson.annotations.SerializedName
import com.yqh.flowhttp.http.config.Config
import com.yqh.lib.flowhttp.bean.IHttpWrapBean

class HttpWrapBean<T>(
    @SerializedName("errorCode")
    override var errorCode: Int,
    @SerializedName("errorMsg")
    override val message: String,
    override val data: T,
) : IHttpWrapBean<T> {

    /**
     * 模拟耗时请求操作，无用代码，测试用
     */
    companion object {
        fun <T> success(data: T): HttpWrapBean<T> {
            return HttpWrapBean(Config.SUCCESS_CODE, "success", data)
        }

        fun <T> failed(data: T): HttpWrapBean<T> {
            return HttpWrapBean(-200, "服务器停止维护了~~", data)
        }
    }


    override val success: Boolean
        get() = errorCode == Config.SUCCESS_CODE

}