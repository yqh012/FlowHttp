package com.yqh.lib.flowhttp.coroutine

import kotlinx.coroutines.CoroutineScope

interface ICoroutine {

    /**
     * 声明 BaseViewModel，BaseRemoteSource，BaseView 中和生命周期绑定的协程作用域
     */
    val lifecycleSupportScope: CoroutineScope

}