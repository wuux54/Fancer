package com.fancer.mvvm.ui.fragment.vr

import com.fancer.R
import com.fancer.mvvm.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_vr_video.*


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

class VrVideoFragment : BaseFragment(R.layout.fragment_vr_video) {
    override fun init() {
        val url = arguments?.getString("video")

        if (url!=null){
            vrVideoCtl.setNetPath(url)
            vrVideoCtl.setIsPlay(true)
        }
    }

    override fun initEvent() {
    }


    override fun onDestroy() {
        super.onDestroy()
        vrVideoCtl.setIsPlay(false)
    }
}