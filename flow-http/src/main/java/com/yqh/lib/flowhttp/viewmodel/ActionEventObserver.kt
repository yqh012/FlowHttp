package com.yqh.lib.flowhttp.viewmodel

import androidx.lifecycle.*
import com.yqh.lib.flowhttp.coroutine.ICoroutine
import kotlinx.coroutines.Job

interface IUIActionEvent : ICoroutine {

    fun showLoading(job: Job?)

    fun hideLoading()

    fun showToast(msg: String)

    fun finishView()

}

interface IViewModelActionEvent : IUIActionEvent {
    val actionEventState: MutableLiveData<ActionEvent>

    override fun showLoading(job: Job?) {
        actionEventState.value = ActionEvent.ShowLoading(job)
    }

    override fun hideLoading() {
        actionEventState.value = ActionEvent.HideLoading
    }

    override fun showToast(msg: String) {
        actionEventState.value = ActionEvent.ShowToast(msg)
    }

    override fun finishView() {
        actionEventState.value = ActionEvent.Finish
    }
}

interface IUIActionEventObserver : IUIActionEvent {

    val lLifecycleOwner: LifecycleOwner

    fun <VM> getViewModel(
        clazz: Class<VM>,
        factory: ViewModelProvider.Factory? = null,
        initializer: (VM.(LifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): Lazy<VM> where VM : ViewModel, VM : IViewModelActionEvent {
        return lazy {
            getViewModelFast(clazz, factory, initializer)
        }
    }

    fun <VM> getViewModelFast(
        clazz: Class<VM>,
        factory: ViewModelProvider.Factory? = null,
        initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): VM where VM : ViewModel, VM : IViewModelActionEvent {
        return when (val localValue = lLifecycleOwner) {
            is ViewModelStoreOwner -> {
                if (factory == null) {
                    ViewModelProvider(localValue).get(clazz)
                } else {
                    ViewModelProvider(localValue, factory).get(clazz)
                }
            }
            else -> {
                factory?.create(clazz) ?: clazz.newInstance()
            }
        }.apply {
            generateActionEvent(this)
            initializer?.invoke(this, lLifecycleOwner)
        }
    }

    fun <VM> generateActionEvent(viewModel: VM) where VM : ViewModel, VM : IViewModelActionEvent {
        viewModel.actionEventState.observe(lLifecycleOwner, Observer {
            when (it) {
                is ActionEvent.ShowLoading -> this@IUIActionEventObserver.showLoading(it.job)
                is ActionEvent.HideLoading -> this@IUIActionEventObserver.hideLoading()
                is ActionEvent.ShowToast -> {
                    if (it.msg.isNotBlank())
                        this@IUIActionEventObserver.showToast(it.msg)
                }
                is ActionEvent.Finish -> this@IUIActionEventObserver.finishView()
            }
        })
    }

}