package com.yqh.flowhttp.base

import com.yqh.flowhttp.http.remote.SelfRemoteDateSource
import com.yqh.lib.flowhttp.base.BaseReactiveViewModel

open class BaseViewModel : BaseReactiveViewModel() {
    /**
     * 此处可以由子类自己实现自己需要的 remoteDataSource
     * 来做自己的不同的 RemoteSource 处理
     */
    protected open val remoteDataSource by lazy {
        SelfRemoteDateSource(this)
    }
}