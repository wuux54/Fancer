package com.fancer.mvvm.ui.adapter.base

import androidx.annotation.LayoutRes
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
abstract class BaseRvAdapter<T> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var listData: MutableList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        get() = field


    fun addList(data: MutableList<T>) {
        if (data.size > 0) {
            if (listData == null) {
                listData = data
                return
            }
            val startIndex = listData!!.size
            listData!!.addAll(data)
            notifyItemRangeChanged(startIndex, data.size)
        }
    }


    fun deleteItem(position: Int) {
        listData!!.removeAt(position)
        notifyItemRangeRemoved(position, 1)
    }


    override fun getItemCount(): Int = if (listData == null) 0 else listData!!.size

    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int


}