package com.fancer.mvvm.ui.adapter.page

import androidx.paging.DataSource
import com.fancer.entity.response.KYHome


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
class HomeDataFactory : DataSource.Factory<String, KYHome.ItemListBean>() {
    override fun create(): DataSource<String, KYHome.ItemListBean> {
        return HomeDataSource()
    }
}