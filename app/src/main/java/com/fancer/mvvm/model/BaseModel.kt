package com.fancer.mvvm.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fancer.entity.response.ErrorResponse

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc：Model 处理网络请求数据缓存等。
 */
open class BaseModel {
    protected var errorLiveData: MutableLiveData<ErrorResponse> = MutableLiveData()

    //持有生命周期，仍然会持有activity中的变量。
    private lateinit var owner: LifecycleOwner

    fun getLifecycleOwner() = owner

    fun bindLifecycle(owner: LifecycleOwner) {
        this.owner = owner
    }

}