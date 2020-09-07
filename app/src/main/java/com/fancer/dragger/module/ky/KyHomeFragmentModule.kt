package com.fancer.dragger.module.ky

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.fancer.mvvm.model.KyHomeModel
import com.fancer.mvvm.vm.ky.KyHomeVm
import dagger.Module
import dagger.Provides
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
@Module
@Singleton
class KyHomeFragmentModule(private val owner: ViewModelStoreOwner) {
    @Singleton
    @Provides
    fun vm(): KyHomeVm = ViewModelProvider(owner).get(KyHomeVm::class.java)

    @Singleton
    @Provides
    fun model():KyHomeModel=KyHomeModel()

}