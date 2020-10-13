package com.fancer.mvvm.ui.fragment.ky

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.util.StatusBarUtils
import app.util.ToastUtil
import com.fancer.BR
import com.fancer.R
import com.fancer.callback.adapter.NorAdapterClickCallBack
import com.fancer.databinding.FragmentKyHomeBinding
import com.fancer.dragger.icomponent.DaggerKyComponentItf_KyHomeFragmentComponent
import com.fancer.dragger.module.ky.KyHomeFragmentModule
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.model.KyHomeModel
import com.fancer.mvvm.ui.adapter.KyHomeListAdapter
import com.fancer.mvvm.ui.fragment.BaseNetFragment
import com.fancer.mvvm.vm.ky.KyHomeVm
import kotlinx.android.synthetic.main.fragment_ky_home.*

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/28
 * @E-mail: W_SpongeBob@163.com
 * @Desc：主页
 */
class KyHomeFragment :
    BaseNetFragment<KyHomeModel, KyHomeVm, FragmentKyHomeBinding>(R.layout.fragment_ky_home) {

    private lateinit var listAdapterKy: KyHomeListAdapter
    private var urlNext: String = ""
    override fun inject() {
        val dagger =
            DaggerKyComponentItf_KyHomeFragmentComponent.builder()
                .kyHomeFragmentModule(
                    KyHomeFragmentModule(
                        this
                    )
                )
                .build()

        dagger.inject(this)
        dagger.inject(viewModel)
    }

    override fun dataBindVariable() {
        getDataBinding().setVariable(BR.clickProxy, this)
    }

    override fun init() {
        (toolbar.layoutParams as FrameLayout.LayoutParams).topMargin =
            StatusBarUtils.getStatusBarHeight(context)

        listAdapterKy = KyHomeListAdapter(requireContext())
        rv_content.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_content.addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
        rv_content.adapter = listAdapterKy
    }

    override fun initEvent() {
        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

        listAdapterKy.setItemCallBack(object :
            NorAdapterClickCallBack<KyHomeListAdapter, KYHome.ItemListBean.DataBean> {
            override fun onItemClick(
                adapterKy: KyHomeListAdapter,
                data: KYHome.ItemListBean.DataBean?,
                position: Int
            ) {
                if (data != null) {
                    val beginTransaction = activity?.supportFragmentManager?.beginTransaction();
                    val kyVideoFragment =
                        KyVideoFragment()
                    val b = Bundle()
                    b.putSerializable("video", data)
                    kyVideoFragment.arguments = b
                    beginTransaction?.add(R.id.nav_host_fragment, kyVideoFragment)
                    beginTransaction?.addToBackStack(null)
                    beginTransaction?.commit()
                }

            }

            override fun onItemChildClick(
                adapterKy: KyHomeListAdapter,
                view: View,
                data: KYHome.ItemListBean.DataBean?
            ) {
            }

        })

        viewModel.getLiveDataKyHomeList().observe(this, Observer {
            if (urlNext.isEmpty()) {
                listAdapterKy.listData = it.itemList.filter {
                    it.data.playUrl?.isNotEmpty() != null
                }.toMutableList()
            } else if (urlNext != it.nextPageUrl) {
                listAdapterKy.addList(it.itemList.filter {
                    it.data.playUrl?.isNotEmpty() != null
                }.toMutableList())
            }

            if (it.nextPageUrl == null) {
                refresh.finishLoadMore(1,true,true)

            } else {
                urlNext = it.nextPageUrl
            }
            hideLoad()
        })

        viewModel.errorLiveData().observe(this, Observer {
            ToastUtil.showErrorToast(it.errorMsg, it.errorCode)
            hideLoad()
        })

        refresh.setOnRefreshListener {
            urlNext = ""
            getHomeList()
        }

        refresh.setOnLoadMoreListener {
            viewModel.getKyHomeList(urlNext)
            refresh.finishLoadMore()
        }
        getHomeList()
    }


    fun clickProxy(v: View) {
    }


    private fun getHomeList() {
        showLoad()
        viewModel.getKyHomeList()
    }

    override fun showLoad() {
        super.showLoad()
    }

    override fun hideLoad() {
        super.hideLoad()
        refresh.finishRefresh()
    }

    override fun goProcess() {
    }


}