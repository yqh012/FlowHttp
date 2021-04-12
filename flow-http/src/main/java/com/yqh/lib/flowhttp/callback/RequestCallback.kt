package com.yqh.lib.flowhttp.callback

class RequestCallback<Data>(
    internal var onSuccessIO: ((Data) -> Unit)? = null,
    internal var onSuccess: ((Data) -> Unit)? = null
) : BaseRequestCallback() {

    /**
     * 在网络请求后会调用此方法
     * 随后会调用 onSuccessIO， onCompletion
     */
    fun onSuccess(success: (Data) -> Unit) {
        this.onSuccess = success
    }

    /**
     * 在 onSuccess之后调用
     * 主要用于获取到数据之后的 异步存储操作，例如数据库存储
     * 在 onCompletion 之前执行
     */
    fun onSuccessIO(successIO: (Data) -> Unit) {
        this.onSuccessIO = successIO
    }


}