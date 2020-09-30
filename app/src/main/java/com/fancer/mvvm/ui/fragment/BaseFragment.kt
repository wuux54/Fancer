package com.fancer.mvvm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import app.util.StatusBarUtils
import com.fancer.R
import com.fancer.lifecycle.FragmentLifecycleObserver

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

abstract class BaseFragment(@LayoutRes private val layoutId: Int) :
    Fragment(layoutId) {

    lateinit var mContext: Context

    open val TAG: String = this::class.java.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(FragmentLifecycleObserver())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHeaderSet()
        init()
        initEvent()
        goProcess()
    }

    abstract fun goProcess()

    open fun viewHeaderSet() {
        val viewHeader = viewHeader() ?: return
        val viewHeaderStatus = viewHeader.findViewById<View>(R.id.fl_header_status)
        viewHeaderStatus.visibility = View.VISIBLE
        viewHeaderStatus.setBackgroundColor(viewStatusColor())
        val statusLayoutParams = viewHeaderStatus.layoutParams as ConstraintLayout.LayoutParams
        statusLayoutParams.height = StatusBarUtils.getStatusBarHeight(context)

    }

    open fun viewHeader(): View? {
        return null
    }

    open fun viewStatusColor(): Int {
        return R.color.white
    }

    abstract fun init()
    abstract fun initEvent()

}