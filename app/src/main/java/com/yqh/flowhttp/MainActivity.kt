package com.yqh.flowhttp

import android.os.Bundle
import com.yqh.flowhttp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val model by getViewModel(TestViewModel::class.java) {
        bannerList.observe(it){
            bannerInfo.text = it.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        model.getBannerList()
    }
}