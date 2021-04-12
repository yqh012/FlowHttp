package com.yqh.lib.flowhttp.viewmodel

sealed class ActionEvent {

    object ShowLoading : ActionEvent()

    object HideLoading : ActionEvent()

    object Finish : ActionEvent()

    class ShowToast(val msg: String) : ActionEvent()

}