package com.fancer.mvvm.vm

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.fancer.mvvm.model.BaseModel
import javax.inject.Inject

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc：mvvm viewModel -> 类似mvp，单独处理view和model。
 * 区别是，activity不持有viewModel对象，让databing生成的处理类做交互操作。解耦了一层v->p持有处理
 * 如果需要服用，每个VM就创建不同的Model可执行不同网络请求
 *
 * BaseVm
 * 实现dataBinding Observable;
 * 继承lifecycle 包中的ViewModel（LiveData处理）;
 */
abstract class BaseVm<T : BaseModel> : ViewModel(), Observable {

    @Inject
    lateinit var model: T


    //子类如果有多个model，必须重写这个，model绑定生命周期
    open fun bindLifecycle(owner: LifecycleOwner) {
        model.bindLifecycle(owner = owner)
    }

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    open fun BaseVm() {}

    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    open fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    open fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }


}