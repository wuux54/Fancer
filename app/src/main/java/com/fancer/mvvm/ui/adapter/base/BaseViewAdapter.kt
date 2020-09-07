package com.fancer.mvvm.ui.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
abstract class BaseViewAdapter<T>(private val context: Context) :
    BaseRvAdapter<T>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(getView(parent, viewType), viewType)
    }

    open fun getView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(getLayoutId(viewType),parent,false)
    }

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

}