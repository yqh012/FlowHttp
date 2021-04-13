package com.yqh.flowhttp.base

import com.yqh.flowhttp.http.remote.SelfRemoteDateSource
import com.yqh.lib.flowhttp.base.BaseReactiveViewModel

open class BaseViewModel : BaseReactiveViewModel() {

    protected open val remoteDataSource by lazy {
        SelfRemoteDateSource(this)
    }
}