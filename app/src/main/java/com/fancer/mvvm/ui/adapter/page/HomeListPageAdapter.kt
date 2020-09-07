package com.fancer.mvvm.ui.adapter.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fancer.R
import com.fancer.entity.response.KYHome


/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/3
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class HomeListPageAdapter(
    private val layoutInflater: LayoutInflater, @LayoutRes val layoutId: Int,private  val itemClick:Onclick
) :
    PagedListAdapter<KYHome.ItemListBean, HomeListPageAdapter.MyHolder>(DIFF_CALLBACK) {


    interface Onclick{
        fun onItemClick();

    }
    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<KYHome.ItemListBean> =
            object : DiffUtil.ItemCallback<KYHome.ItemListBean>() {
                override fun areItemsTheSame(
                    oldConcert: KYHome.ItemListBean,
                    newConcert: KYHome.ItemListBean
                ): Boolean {
                    return oldConcert.getId() === newConcert.getId()
                }

                override fun areContentsTheSame(
                    oldConcert: KYHome.ItemListBean,
                    newConcert: KYHome.ItemListBean
                ): Boolean {
                    return oldConcert.equals(newConcert)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(layoutInflater.inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.text.text = getItem(position)!!.data.playUrl
        holder.itemView.setOnClickListener {
            itemClick.onItemClick()
        }
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.text)

    }


}