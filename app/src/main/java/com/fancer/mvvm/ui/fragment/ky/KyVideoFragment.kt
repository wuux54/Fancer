package com.fancer.mvvm.ui.fragment.ky

import com.fancer.R
import com.fancer.databinding.FragmentKyVideoBinding
import com.fancer.dragger.icomponent.DaggerKyComponentItf_KyVideoFragmentComponent
import com.fancer.dragger.module.ky.KyVideoFragmentModule
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.model.KyVideoModel
import com.fancer.mvvm.ui.fragment.BaseNetFragment
import com.fancer.mvvm.vm.ky.KyVideoVm
import kotlinx.android.synthetic.main.fragment_ky_video.*

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
class KyVideoFragment :
    BaseNetFragment<KyVideoModel, KyVideoVm, FragmentKyVideoBinding>(R.layout.fragment_ky_video) {
    override fun inject() {
        val dagger =
            DaggerKyComponentItf_KyVideoFragmentComponent.builder()
                .kyVideoFragmentModule(
                    KyVideoFragmentModule(
                        this
                    )
                )
                .build()

        dagger.inject(this)
        dagger.inject(viewModel)
    }

    override fun bindVm() {
    }
    var url=""
    override fun init() {
        val data: KYHome.ItemListBean.DataBean = arguments?.get("video") as KYHome.ItemListBean.DataBean

        url = data.playUrl


        ijkVideo.setPath(url)
        ijkVideo.start()

    }



    override fun initEvent() {
        tv_Click.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        ijkVideo.releaseMedia()
    }

}