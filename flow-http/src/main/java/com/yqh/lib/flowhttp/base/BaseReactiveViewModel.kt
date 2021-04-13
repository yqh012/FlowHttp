package com.yqh.lib.flowhttp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yqh.lib.flowhttp.viewmodel.ActionEvent
import com.yqh.lib.flowhttp.viewmodel.IViewModelActionEvent
import kotlinx.coroutines.CoroutineScope

open class BaseReactiveViewModel : ViewModel(), IViewModelActionEvent {

    override val actionEventState: MutableLiveData<ActionEvent> = MutableLiveData<ActionEvent>()

    override val lifecycleSupportScope: CoroutineScope = viewModelScope
}