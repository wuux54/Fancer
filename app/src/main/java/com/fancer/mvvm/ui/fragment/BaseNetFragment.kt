package com.fancer.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import app.BR
import com.fancer.mvvm.model.BaseModel
import com.fancer.mvvm.ui.dialog.LoadDialogManager
import com.fancer.mvvm.vm.BaseVm
import javax.inject.Inject

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/28
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

abstract class BaseNetFragment<model : BaseModel, vm : BaseVm<model>, B : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    BaseFragment(layoutId) {

    private lateinit var loadDialogManager: LoadDialogManager

    //activity view绑定
    private lateinit var viewDataBinding: B

    @Inject
    open lateinit var viewModel: vm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) :  View? {
        loadDialogManager = LoadDialogManager.newInstance(context)

        inject()
        viewModel.bindLifecycle(this)

        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(BR.vm, viewModel)

        bindVm()
        return viewDataBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding.unbind()
        loadDialogManager.recycleDialog()
    }

    abstract fun inject()

    abstract fun bindVm()

    fun getDataBinding(): B = viewDataBinding


    open fun showLoad() {
        loadDialogManager.showDefProgress()
    }

    open  fun hideLoad() {
        loadDialogManager.hideDialog()
    }
}