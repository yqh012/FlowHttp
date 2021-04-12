package com.yqh.lib.flowhttp.callback

import com.yqh.lib.flowhttp.exception.BaseHttpException

open class BaseRequestCallback(
    internal var onStart: (() -> Unit)? = null,
    internal var onCancelled: (() -> Unit)? = null,
    internal var onFailed: ((BaseHttpException) -> Unit)? = null,
    internal var onFailToast: (() -> Boolean) = { true },
    internal var onCompletion: (() -> Unit)? = null
) {
    /**
     * 开始执行的回调方法
     * 在开始执行网络请求之前会先调用它
     */
    fun onStart(start: () -> Unit) {
        this.onStart = start
    }

    /**
     * 如果在外部取消了网络请求
     * 则会回调 onCancelled ,不会在回调 onFailed ,但是还会回调 onCompletion
     */
    fun onCancelled(cancel: () -> Unit) {
        this.onCancelled = cancel
    }

    /**
     * 在网络请求失败时会调用该方法
     * 之后会继续执行 onCompletion
     */
    fun onFailed(failed: (BaseHttpException) -> Unit) {
        this.onFailed = failed
    }

    /**
     * 网络请求时，通过 Toast 显示失败原因
     * 默认为 true  : 显示失败原因
     *       false : 不显示失败原因
     */
    fun onFailedToast(block: () -> Boolean) {
        this.onFailToast = block
    }

    /**
     * 网络请求的最终回调
     * 成功或者失败都会调用到他
     */
    fun onCompletion(complete: () -> Unit) {
        this.onCompletion = complete
    }
}