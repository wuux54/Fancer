package com.fancer.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter

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
class TestAdapter(
    var dataList: Array<String>,
    var context: Context,
    var id: Int,
    @LayoutRes var layoutId: Int,
    var inflater: LayoutInflater
) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as ViewDataBinding).root
    }

    override fun getCount(): Int = dataList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ViewDataBinding =
            DataBindingUtil.bind(inflater.inflate(layoutId, container, false))!!
        container.addView(binding.root)
        val t = TestViewModel()
        binding.setVariable(id, t)
        t.text = "dd->" + dataList[position]
        return binding
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val binding: ViewDataBinding = `object` as ViewDataBinding
        container.removeView(binding.root)
    }
}