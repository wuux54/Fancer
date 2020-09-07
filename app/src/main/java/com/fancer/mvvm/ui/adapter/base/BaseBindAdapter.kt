package com.fancer.mvvm.ui.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/7
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
abstract class BaseBindAdapter<T>(private val context: Context, data: MutableList<T>) :
    BaseRvAdapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(getViewDataBinding(parent, viewType), viewType)
    }

    open fun getViewDataBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(context),
            getLayoutId(viewType),
            parent,
            false
        )
    }

    abstract fun getViewHolder(bind: ViewDataBinding, viewType: Int): RecyclerView.ViewHolder
}