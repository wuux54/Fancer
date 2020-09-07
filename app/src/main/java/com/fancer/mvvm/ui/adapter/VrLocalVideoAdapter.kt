package com.fancer.mvvm.ui.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.util.DataCleanUtils
import app.util.DateUtil
import app.util.file.FileManager
import app.util.file.bean.VideoBean
import com.fancer.MainApplication
import com.fancer.R
import com.fancer.callback.adapter.NorAdapterClickCallBack
import com.fancer.mvvm.ui.adapter.base.BaseViewAdapter
import java.util.*

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/12
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class VrLocalVideoAdapter(context: Context) : BaseViewAdapter<VideoBean>(context) {

    private var itemCallback: NorAdapterClickCallBack<VrLocalVideoAdapter,VideoBean>? = null;

    fun setItemCallBack(itemCallback: NorAdapterClickCallBack<VrLocalVideoAdapter, VideoBean>) {
        this.itemCallback = itemCallback
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(view)


    override fun getLayoutId(viewType: Int): Int = R.layout.item_vr_local

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyViewHolder
        val videoBean = listData!![position]
        myHolder.tvVrDate.text = DateUtil.dateToString(Date(videoBean.date * 1000),DateUtil.FORMAT_ONE_DATE)
        myHolder.tvVrName.text = "未命名-$position.mp4"
        myHolder.tvVrSize.text = DataCleanUtils.getFormatSize(videoBean.size.toDouble())
        myHolder.tvVrDuration.text = DateUtil.secToTime(videoBean.duration.toInt() / 1000)

        Thread(Runnable {
            val videoThumbnail =
                FileManager.getInstance(MainApplication.applicationContext()).getVideoThumbnail(
                    videoBean.id
                )
           Handler(Looper.getMainLooper()).post {
               myHolder.ivBtm.setImageBitmap(
                   videoThumbnail)
           }
        }).start()

        myHolder.itemView.setOnClickListener {
            itemCallback!!.onItemClick(this,videoBean,position)
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivBtm = view.findViewById<ImageView>(R.id.iv_btm)
        var tvVrName = view.findViewById<TextView>(R.id.tv_VrName)
        var tvVrSize = view.findViewById<TextView>(R.id.tv_VrSize)
        var tvVrDate = view.findViewById<TextView>(R.id.tv_VrDate)
        var tvVrDuration = view.findViewById<TextView>(R.id.tv_VrDuration)
    }
}