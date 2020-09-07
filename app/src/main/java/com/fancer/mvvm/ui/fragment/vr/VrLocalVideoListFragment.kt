package com.fancer.mvvm.ui.fragment.vr

import android.Manifest
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.util.file.FileManager
import app.util.file.bean.VideoBean
import com.fancer.MainApplication
import com.fancer.R
import com.fancer.callback.adapter.NorAdapterClickCallBack
import com.fancer.mvvm.ui.adapter.VrLocalVideoAdapter
import com.fancer.mvvm.ui.fragment.BaseFragment
import com.permission.interfaces.WxPermissionListener
import com.permission.interfaces.WxPermissionTipViewListener
import com.permission.manager.WxPermissionManager
import kotlinx.android.synthetic.main.fragment_vr_local.*


/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/7
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

class VrLocalVideoListFragment : BaseFragment(R.layout.fragment_vr_local) {
    private lateinit var vrLocalVideoAdapter: VrLocalVideoAdapter
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var wxPermissionManager: WxPermissionManager? = null
    override fun init() {
        vrLocalVideoAdapter = VrLocalVideoAdapter(mContext)
        rv_list.adapter = vrLocalVideoAdapter
        rv_list.layoutManager = LinearLayoutManager(mContext)

        var mPaint = Paint()
        mPaint.setAntiAlias(true) //抗锯齿
        mPaint.setColor(Color.GRAY) //默认灰色

        rv_list.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDraw(c, parent, state)
                val childCount = parent.childCount
                for (i in 0..childCount) {
                    val childAt = parent.getChildAt(i)
                    val index = parent.getChildAdapterPosition(childAt)
                    if (index == 0) {
                        continue
                    }
                    if (childAt == null) {
                        continue
                    }
                    val paddingLeft = childAt.paddingLeft
                    val paddingRight = childAt.right - childAt.paddingRight

                    val paddingBottom = childAt.top
                    val paddingTop = childAt.top - 30
                    c.drawRect(
                        paddingLeft.toFloat(),
                        paddingTop.toFloat(),
                        paddingRight.toFloat(),
                        paddingBottom.toFloat(),
                        mPaint
                    )
                }
            }

            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                super.getItemOffsets(outRect, itemPosition, parent)
                outRect.set(0, if (itemPosition == 0) 0 else 20, 0, 10)
            }
        })


        requestPermission()
    }

    override fun initEvent() {
        vrLocalVideoAdapter.setItemCallBack(object :
            NorAdapterClickCallBack<VrLocalVideoAdapter, VideoBean> {
            override fun onItemClick(
                adapter: VrLocalVideoAdapter,
                data: VideoBean?,
                position: Int
            ) {
                val bundle = Bundle()
                bundle.putString("video", data!!.path)

                Navigation.findNavController(rv_list)
                    .navigate(R.id.action_vr_listFragment_to_videoFragment, bundle)
            }

            override fun onItemChildClick(
                adapter: VrLocalVideoAdapter,
                view: View,
                data: VideoBean?
            ) {
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        wxPermissionManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermission() {
        wxPermissionManager = WxPermissionManager.instanceBuilder()
            .setPermissionListener(object : WxPermissionListener {
                override fun permissions(): Array<String> {
                    return permissions
                }

                override fun activity(): Activity {
                    return activity!!
                }

                override fun requestCode(): Int {
                    return 123
                }
            })
            .setPermissionTipViewListener(object : WxPermissionTipViewListener {
                override fun tipSettingViewBundle(permissionKey: String?): Bundle? = null
                override fun tipSettingView(permissionKey: String?): View? = null
                override fun reminderViewBundle(permissionKey: String?): Bundle? = null
                override fun reminderView(permissionKey: String?): View? = null
            })
            .setPermissionResultListener { requestCode: Int, allGranted: Boolean, permission: List<String?>? ->
                if (!allGranted) {
                    wxPermissionManager?.showSettingTipView(permission);
                } else {
                    //授权成功
                    vrLocalVideoAdapter.addList(
                        FileManager.getInstance(
                            MainApplication.applicationContext()
                        ).videos
                    )

                }
            }
            .build()
    }


}