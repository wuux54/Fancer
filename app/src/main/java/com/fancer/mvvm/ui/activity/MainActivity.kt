package com.fancer.mvvm.ui.activity

import android.content.Intent
import com.fancer.R
import com.fancer.databinding.ActivityMainBinding
import com.fancer.dragger.icomponent.DaggerMainComponent_MainActivityComponent
import com.fancer.dragger.module.MainActivityModule
import com.fancer.mvvm.model.MainModel
import com.fancer.mvvm.vm.MainVm

class MainActivity : BaseActivity<MainModel, MainVm, ActivityMainBinding>(R.layout.activity_main) {
    override fun inject() {
        val mainActivityComponent =
            DaggerMainComponent_MainActivityComponent.builder()
                .mainActivityModule(MainActivityModule(this))
                .build()

        mainActivityComponent.inject(this)
        mainActivityComponent.inject(viewModel)
    }

    override fun bindVm() {
    }

    override fun init() {
    }

    override fun initEvent() {
        startActivity(Intent(this, VrMainActivity::class.java))
//        viewModel.getLiveDataUser().observe(this, Observer {
//            if (it == null) {
//                viewModel.updateText("null")
//            } else {
////                viewModel.updateText(it.nextPageUrl)
//                startActivity(Intent(this, KyMainActivity::class.java))
//            }
//        })
//
//        viewModel.errorLiveData().observe(this, Observer {
//            viewModel.updateText(it.errorMsg + "-->" + it.errorCode)
//
//        })
    }
}
