package com.yqh.lib.flowhttp.bean

/**
 * 网络结果返回的整体类型包装
 */
interface IHttpWrapBean<Data> {

    /**
     * 接口返回的 errorCode，标识当前请求是否成功
     * 以及错误码的筛选
     */
    val errorCode: Int

    /**
     * 接口返回当前请求的状态
     * errorCode => message : 不同的错误状态对应不同的 errorCode
     */
    val message: String

    /**
     * 接口返回的真实数据
     */
    val data: Data

    /**
     * 是否请求成功的状态判断
     */
    val success: Boolean

    /**
     * 记录是请求失败了的状态
     */
    val httpIsFailed: Boolean
        get() = !success
}