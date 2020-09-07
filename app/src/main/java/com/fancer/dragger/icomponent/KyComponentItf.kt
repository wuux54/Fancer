package com.fancer.dragger.icomponent

import com.fancer.dragger.module.ky.KyHomeFragmentModule
import com.fancer.dragger.module.ky.KyMainFragmentModule
import com.fancer.dragger.module.ky.KyMineFragmentModule
import com.fancer.dragger.module.ky.KyVideoFragmentModule
import com.fancer.mvvm.ui.fragment.ky.KyHomeFragment
import com.fancer.mvvm.ui.fragment.ky.KyMainFragment
import com.fancer.mvvm.ui.fragment.ky.KyMineFragment
import com.fancer.mvvm.ui.fragment.ky.KyVideoFragment
import com.fancer.mvvm.vm.ky.KyHomeVm
import com.fancer.mvvm.vm.ky.KyMainVm
import com.fancer.mvvm.vm.ky.KyMineVm
import com.fancer.mvvm.vm.ky.KyVideoVm
import dagger.Component
import javax.inject.Singleton

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/29
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
interface KyComponentItf {
    @Singleton
    @Component(modules = [KyMineFragmentModule::class])
    interface KyMineFragmentComponent {
        fun inject(fragment: KyMineFragment)

        fun inject(vm: KyMineVm)
    }

    @Singleton
    @Component(modules = [KyMainFragmentModule::class])
    interface KyMainFragmentComponent {
        fun inject(fragment: KyMainFragment)
        fun inject(vm: KyMainVm)

    }

    @Singleton
    @Component(modules = [KyHomeFragmentModule::class])
    interface KyHomeFragmentComponent {
        fun inject(fragment: KyHomeFragment)
        fun inject(vm: KyHomeVm)

    }

    @Singleton
    @Component(modules = [KyVideoFragmentModule::class])
    interface KyVideoFragmentComponent {
        fun inject(fragment: KyVideoFragment)
        fun inject(vm: KyVideoVm)
    }
}