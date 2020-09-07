package com.fancer.mvvm.ui.adapter.page

import androidx.paging.PageKeyedDataSource
import app.util.LogUtils
import com.fancer.callback.NetRequestCallBack
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome
import com.fancer.net.NetRequestManager


/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/6
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class HomeDataSource : PageKeyedDataSource<String, KYHome.ItemListBean>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, KYHome.ItemListBean>
    ) {
        LogUtils.d("dd","loadInitial")
            NetRequestManager.getKyHomeList2(object : NetRequestCallBack<KYHome> {
                override fun success(d: KYHome) {
                    callback.onResult(d.itemList,null,d.nextPageUrl)
                }

                override fun error(errorResponse: ErrorResponse) {
                }
            })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, KYHome.ItemListBean>
    ) {
        NetRequestManager.getKyHomeListNext(params.key, object :
            NetRequestCallBack<KYHome> {
            override fun success(d: KYHome) {
                callback.onResult(d.itemList,d.nextPageUrl)
            }

            override fun error(errorResponse: ErrorResponse) {
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, KYHome.ItemListBean>
    ) {
    }

}