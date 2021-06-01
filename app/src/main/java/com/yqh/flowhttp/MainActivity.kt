package com.yqh.flowhttp

import android.os.Bundle
import com.yqh.flowhttp.base.BaseActivity
import com.yqh.lib.flowhttp.viewmodel.ActionEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job

class MainActivity : BaseActivity() {

    private val model by getViewModel(TestViewModel::class.java) { livecycle ->
        bannerList.observe(livecycle) {
            bannerInfo.text = it.toString()
        }
    }

//    override fun showLoading(job: Job?) {
//        super.showLoading(job)
//    }
//
//    override fun hideLoading() {
//        super.hideLoading()
//    }
//
//    override fun showToast(msg: String) {
//        super.showToast(msg)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        model.getBannerList()
    }
}