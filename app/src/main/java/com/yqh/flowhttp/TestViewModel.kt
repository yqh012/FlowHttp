package com.yqh.flowhttp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yqh.flowhttp.base.BaseViewModel
import com.yqh.lib.flowhttp.bean.IHttpWrapBean
import kotlinx.coroutines.delay

class TestViewModel : BaseViewModel() {

    val bannerList = MutableLiveData<List<BannerInfo>>()

    fun log(msg: Any) {
        val value = "${Thread.currentThread().name} , $msg"
        Log.e("BaseReactiveActivity", value)
    }

    fun getBannerList() {

//        /**
//         * 多个接口同步请求
//         */
//        remoteDataSource.enqueueLoadings(
//            callback = {
//                onSuccess {
//                    (it as? List<*>)?.let {
//                        contentList ->
//
//                        log(contentList[0].toString())
//                        log(contentList[1].toString())
//                    }
//                }
//
//                onCompletion {
//                    log("onCompletion... ")
//                }
//                onCancelled {
//                    log("onCancelled... ")
//                }
//
//                onFailedToast { true }
//                onFailed {
//                    log("filed : ${it.message}")
//                }
//
//            }, apis = arrayOf(
//                { getBannerList() },
//                { getChatList() }
//            )
//        )


        remoteDataSource.enqueueLoading({
            delay(3000)
            getBannerList()
        }) {

            onStart {
                log("onStart...")
            }

            onCancelled {
                log("onCancelled...")
            }

            onFailed {
                log("onFailed...")
            }

            onFailedToast {
                log("onFailedToast...")
                true
            }

            onSuccess {
                log("onSuccess... ${it.toString()}")
                bannerList.value = it
            }

            onSuccessIO {
                Thread.sleep(3000)
                log("onSuccessIO... ${it.toString()}")
            }

            onCompletion {
                log("onCompletion... ")
            }
        }
    }
}