package com.fancer.dragger.module

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.fancer.mvvm.model.MainModel
import com.fancer.mvvm.vm.MainVm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/8
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

@Module
class MainActivityModule(private val owner: ViewModelStoreOwner) {
    @Provides
    @Singleton
    fun viewModel(): MainVm {
        //VM 也可以用@Inject标记构造。这样可以取消Module类中的写入
        return ViewModelProvider(owner).get(MainVm::class.java)
    }

    @Provides
    @Singleton
    fun mainModel(): MainModel {
        //VM 也可以用@Inject标记构造。这样可以取消Module类中的写入
        return MainModel()
    }
}