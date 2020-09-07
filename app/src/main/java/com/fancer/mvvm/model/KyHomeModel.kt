package com.fancer.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fancer.callback.NetRequestCallBack
import com.fancer.constant.HttpConstant.Companion.REQUEST_TYPE_KY_HOME_LIST
import com.fancer.constant.HttpConstant.Companion.REQUEST_TYPE_KY_HOME_LIST_NEXT
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.intrf.RequestItf
import com.fancer.net.NetRequestManager


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
class KyHomeModel : BaseModel(), RequestItf.KyHomeRequest {

    var homeListLiveData: MutableLiveData<KYHome> = MutableLiveData()

    override fun getLiveDataKyHomeList(): LiveData<KYHome> = homeListLiveData

    override fun errorLiveData(): LiveData<ErrorResponse> = errorLiveData

    override fun getKyHomeList() {

        NetRequestManager.getKyHomeList2(callBack = object : NetRequestCallBack<KYHome> {
            override fun success(data: KYHome) {
                homeListLiveData.postValue(data)
            }

            override fun error(errorResponse: ErrorResponse) {
                errorResponse.errorType = REQUEST_TYPE_KY_HOME_LIST
                errorLiveData.postValue(errorResponse)
            }

        })
    }

    override fun getKyHomeList(url: String) {
        NetRequestManager.getKyHomeListNext(url, object : NetRequestCallBack<KYHome> {
            override fun success(data: KYHome) {
                homeListLiveData.postValue(data)
            }

            override fun error(errorResponse: ErrorResponse) {
                errorResponse.errorType = REQUEST_TYPE_KY_HOME_LIST_NEXT
                errorLiveData.postValue(errorResponse)
            }

        })
    }


}