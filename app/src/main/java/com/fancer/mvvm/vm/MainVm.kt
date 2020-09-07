package com.fancer.mvvm.vm

import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fancer.BR
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.intrf.RequestItf
import com.fancer.mvvm.model.MainModel


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
 */
open class MainVm : BaseVm<MainModel>(), RequestItf.MainRequest {


    override fun bindLifecycle(owner: LifecycleOwner) {
        super.bindLifecycle(owner)
    }

    // Create a LiveData with a String
    private var currentName: MutableLiveData<String>? = null;

    fun getCurrentName(): MutableLiveData<String> {
        if (currentName == null) {
            currentName = MutableLiveData()
        }
        return currentName as MutableLiveData<String>
    }


    var test: String = "main"
        @Bindable
        get


    var i: Int = 0
    fun btnClick() {
        getUserInfo();
    }


    fun updateText(value: String) {
        test = value
        notifyPropertyChanged(BR.test)
    }

    override fun getUserInfo() {
        model.getUserInfo()
    }

    override fun getLiveDataUser(): LiveData<KYHome> = model.getLiveDataUser()

    override fun errorLiveData(): LiveData<ErrorResponse> = model.errorLiveData()

}