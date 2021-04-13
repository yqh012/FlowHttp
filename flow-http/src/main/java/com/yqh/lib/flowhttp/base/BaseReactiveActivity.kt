package com.yqh.lib.flowhttp.base

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.yqh.lib.flowhttp.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope


open class BaseReactiveActivity : AppCompatActivity(), IUIActionEventObserver {
    override val lLifecycleOwner: LifecycleOwner by lazy { this }
    override val lifecycleSupportScope: CoroutineScope = lifecycleScope

    override fun showLoading() {
        Log.e("BaseReactiveActivity", "showLoading ... ")
    }

    override fun hideLoading() {
        Log.e("BaseReactiveActivity", "hideLoading ... ")
    }

    override fun showToast(msg: String) {
        Log.e("BaseReactiveActivity", "showToast ... ")
        if (msg.isNotBlank()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun finishView() {
        Log.e("BaseReactiveActivity", "finish ... ")
        finish()
    }

}