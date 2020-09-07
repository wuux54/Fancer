package com.fancer.callback.adapter

import android.view.View

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/30
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
interface  NorAdapterClickCallBack<A, T> {
    fun onItemClick(adapter: A, data: T?, position: Int);
    fun onItemChildClick(adapter: A, view: View, data: T?);
}