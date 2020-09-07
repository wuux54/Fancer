package com.fancer.mvvm.ui.fragment.ky

import com.fancer.R
import com.fancer.databinding.FragmentKyMineBinding
import com.fancer.dragger.icomponent.DaggerKyComponentItf_KyMineFragmentComponent
import com.fancer.dragger.module.ky.KyMineFragmentModule
import com.fancer.mvvm.model.KyMineModel
import com.fancer.mvvm.ui.fragment.BaseNetFragment
import com.fancer.mvvm.vm.ky.KyMineVm

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/28
 * @E-mail: W_SpongeBob@163.com
 * @Desc：主页
 */
class KyMineFragment : BaseNetFragment<KyMineModel,KyMineVm, FragmentKyMineBinding>(R.layout.fragment_ky_mine) {

    override fun inject() {
        val dagger =
            DaggerKyComponentItf_KyMineFragmentComponent.builder()
                .kyMineFragmentModule(
                    KyMineFragmentModule(
                        this
                    )
                )
                .build()

        dagger.inject(this)
        dagger.inject(viewModel)
    }

    override fun bindVm() {
    }

    override fun init() {
    }

    override fun initEvent() {
    }
}