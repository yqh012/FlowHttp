package com.yqh.common.utils

import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("PrivateApi", "DiscouragedPrivateApi")
object AppUtil {
    val application: Application by lazy {
        Class.forName("android.app.ActivityThread")
            .getDeclaredMethod("currentApplication")
            .invoke(null) as Application
    }

}