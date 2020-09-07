package com.fancer.mvvm.vm.ky

import androidx.lifecycle.LiveData
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.intrf.RequestItf
import com.fancer.mvvm.model.KyHomeModel
import com.fancer.mvvm.vm.BaseVm


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

class KyHomeVm : BaseVm<KyHomeModel>(), RequestItf.KyHomeRequest {

    override fun getLiveDataKyHomeList(): LiveData<KYHome> = model.getLiveDataKyHomeList()

    override fun getKyHomeList() {
        model.getKyHomeList()
    }

    override fun getKyHomeList(url: String) {
        if(url.isEmpty()){
            return
        }
        model.getKyHomeList(url)
    }

    override fun errorLiveData(): LiveData<ErrorResponse> = model.errorLiveData()



}