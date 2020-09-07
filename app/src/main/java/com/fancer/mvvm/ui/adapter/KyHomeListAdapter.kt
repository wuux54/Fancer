package com.fancer.mvvm.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.util.GlideUtils
import com.fancer.R
import com.fancer.callback.adapter.NorAdapterClickCallBack
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.ui.adapter.base.BaseViewAdapter
import kotlinx.android.synthetic.main.item_ky_home.view.*

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/7
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class KyHomeListAdapter(private val context: Context) :
    BaseViewAdapter<KYHome.ItemListBean>(context) {


    private var itemCallback: NorAdapterClickCallBack<KyHomeListAdapter, KYHome.ItemListBean.DataBean>? =
        null;

    fun setItemCallBack(itemCallback: NorAdapterClickCallBack<KyHomeListAdapter, KYHome.ItemListBean.DataBean>) {
        this.itemCallback = itemCallback
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_ky_home

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return HomeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = listData?.get(position)?.data
        (holder as HomeListViewHolder).tvLabel.text = itemData?.category ?: ""
        holder.tvTitle.text = itemData?.title ?: ""
        holder.tvDesc.text = itemData?.description ?: ""

        GlideUtils.loadImage(context, itemData?.cover?.detail ?: "", holder.ivBg)

        holder.itemView.setOnClickListener {
            itemCallback?.onItemClick(
                this,
                itemData,
                position
            )
        }
    }

    private class HomeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.tv_label
        val tvTitle: TextView = itemView.tv_title
        val tvDesc: TextView = itemView.tv_desc
        val ivBg: ImageView = itemView.iv_bg
    }
}