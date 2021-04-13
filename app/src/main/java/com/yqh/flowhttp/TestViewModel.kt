package com.yqh.flowhttp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yqh.flowhttp.base.BaseViewModel

class TestViewModel : BaseViewModel() {

    val bannerList = MutableLiveData<BannerInfo>()

    fun log(msg: Any) {
        val value = "${Thread.currentThread().name} , $msg"
        Log.e("TestViewModel", value)
    }

    fun getBannerList() {
        remoteDataSource.enqueueLoading({
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
            }

            onSuccessIO {
                log("onSuccessIO... ${it.toString()}")
            }

            onCompletion {
                log("onCompletion... ")
            }
        }
    }
}