package com.fancer.dragger.icomponent

import com.fancer.dragger.module.MainActivityModule
import com.fancer.mvvm.ui.activity.MainActivity
import com.fancer.mvvm.vm.MainVm
import dagger.Component
import javax.inject.Singleton

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/9
 * @E-mail: W_SpongeBob@163.com
 * @Desc：@Singleton Component中单例，如果有多个builder（），还会创建多个实例
 *
 * 步骤1：查找Module中是否存在创建该类的方法。
 * 步骤2：若存在创建类方法，查看该方法是否存在参数
 * 步骤2.1：若存在参数，则按从步骤1开始依次初始化每个参数
 * 步骤2.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束
 * 步骤3：若不存在创建类方法，则查找Inject注解的构造函数，看构造函数是否存在参数
 * 步骤3.1：若存在参数，则从步骤1开始依次初始化每个参数
 * 步骤3.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束
 */

interface MainComponent{
    @Singleton
    @Component(modules = [MainActivityModule::class])
    interface MainActivityComponent {
        fun inject(activity: MainActivity)

        fun inject(vm: MainVm)

        //1.8新特性，接口可实现静态方法（子类不继承）和default修饰方法（子类可重载）
//    companion object {
//        fun init(activity: MainActivity): MainActivityComponent {
//            return DaggerMainActivityComponent.builder()
//                .mainActivityModule(MainActivityModule(activity))
//                .build()
//        }
//    }
    }

}
