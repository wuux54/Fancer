package com.fancer.mvvm.ui.databinding

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/2
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class DataBindConfig(@LayoutRes val layoutId: Int, val vm: ViewModel) {

    private val bindingParams: SparseArray<Any> = SparseArray()

    fun getBindingParams(): SparseArray<Any> {
        return bindingParams
    }


    fun addBindingParam(
        variableId: Int,
        params: Any
    ): DataBindConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, params)
        }
        return this
    }

}