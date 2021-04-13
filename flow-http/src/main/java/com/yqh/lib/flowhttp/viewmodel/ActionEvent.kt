package com.yqh.lib.flowhttp.viewmodel

import kotlinx.coroutines.Job

sealed class ActionEvent {

    class ShowLoading(val job: Job?) : ActionEvent()

    object HideLoading : ActionEvent()

    object Finish : ActionEvent()

    class ShowToast(val msg: String) : ActionEvent()

}