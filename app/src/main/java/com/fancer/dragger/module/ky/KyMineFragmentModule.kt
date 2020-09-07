package com.fancer.dragger.module.ky

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.fancer.mvvm.model.KyMineModel
import com.fancer.mvvm.vm.ky.KyMineVm
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
@Singleton
@Module
class KyMineFragmentModule (private val owner: ViewModelStoreOwner){
    @Singleton
    @Provides
    fun vm() =ViewModelProvider(owner).get(KyMineVm::class.java)

    @Singleton
    @Provides
    fun model() :KyMineModel=KyMineModel()
}