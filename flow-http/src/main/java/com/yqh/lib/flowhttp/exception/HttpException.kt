package com.yqh.lib.flowhttp.exception

import com.yqh.lib.flowhttp.bean.IHttpWrapBean

/**
 * 异常封装
 * @param errorCode 返回的错误码
 * @param errorMessage 返回的错误信息
 * @param exception 异常信息
 */
open class BaseHttpException(
    val errorCode: Int,
    val errorMessage: String,
    val exception: Throwable?
) : Exception(errorMessage) {
    companion object {
        /**
         * 表示在网络请求过程中抛出了异常
         */
        const val CODE_ERROR_UNKNOWN = -19999
    }

    /**
     * 判断异常类型是否是 服务器抛出的异常 (code != success)
     */
    val isServerCodeBadException: Boolean
        get() = this is ServerCodeBadException

    /**
     * 判断是否是在网络请求过程中抛出的异常
     */
    val isLocalBadException: Boolean
        get() = this is LocalBadException
}

/**
 * 表示接口请求错误
 * 接口返回的业务错误
 * 包含 code 以及 msg 错误信息的异常
 */
class ServerCodeBadException(errorCode: Int, errorMsg: String) :
    BaseHttpException(errorCode, errorMsg, null) {
    constructor(info: IHttpWrapBean<*>) : this(info.errorCode, info.message)
}

/**
 * 请求过程中抛出的异常
 */
class LocalBadException(exception: Throwable) : BaseHttpException(
    CODE_ERROR_UNKNOWN,
    exception.message ?: "",
    exception
)

