package com.fancer.mvvm.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import app.util.StatusBarUtils
import com.fancer.BR
import com.fancer.R
import com.fancer.lifecycle.MyLifecycleObserver
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
 * @Date: 2020/6/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
abstract class BaseActivity<model : BaseModel, vm : BaseVm<model>, B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    AppCompatActivity() {

    val TAG: String = this::class.java.simpleName

    private lateinit var loadDialogManager: LoadDialogManager
    //activity view绑定
    private lateinit var viewDataBinding: B

    @Inject
    open lateinit var viewModel: vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadDialogManager = LoadDialogManager.newInstance(this)

        lifecycle.addObserver(MyLifecycleObserver())
        inject()

        viewModel.bindLifecycle(this)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutRes)
        viewDataBinding.setVariable(BR.vm, viewModel)

        bindVm()
        viewHeaderSet()
        init()
        initEvent()
    }

    fun getDataBinding() = viewDataBinding


    open fun viewHeaderSet() {
        val viewHeader = viewHeader() ?: return
        val viewHeaderStatus = viewHeader.findViewById<View>(R.id.fl_header_status)
        viewHeaderStatus.visibility = View.VISIBLE
        viewHeaderStatus.setBackgroundColor(viewStatusColor())
        val statusLayoutParams = viewHeaderStatus.layoutParams as ConstraintLayout.LayoutParams
        statusLayoutParams.height = StatusBarUtils.getStatusBarHeight(this)

    }

    open fun viewHeader(): View? {
        return null
    }

    open fun viewStatusColor(): Int {
        return R.color.white
    }

    abstract fun inject()

    abstract fun bindVm()

    abstract fun init()

    abstract fun initEvent()

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
        loadDialogManager.recycleDialog()
    }


    protected fun showLoad() {
        loadDialogManager.showDefProgress()
    }

    protected fun hideLoad() {
        loadDialogManager.hideDialog()
    }
}