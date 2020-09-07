package com.fancer.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome
import com.fancer.mvvm.intrf.RequestItf

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/10
 * @E-mail: W_SpongeBob@163.com
 * @Desc：数据获取
 */
class MainModel : BaseModel(), RequestItf.MainRequest {

    private var kyHomeLiveData: MutableLiveData<KYHome> = MutableLiveData()

    //向上转型，保证ui中使用时只能读数据，不能post更改数据
    override fun getLiveDataUser(): LiveData<KYHome> {
        return kyHomeLiveData
    }

    override fun getUserInfo() {
        kyHomeLiveData.postValue(KYHome())
//        NetRequestManager.getKyHomeList2(object : NetRequestCallBack<KYHome> {
//            override fun success(data: KYHome) {
//                kyHomeLiveData.postValue(data)
//            }
//
//            override fun error(errorResponse: ErrorResponse) {
//                errorResponse.errorType = REQUEST_TYPE_KY_HOME_LIST
//                errorLiveData.postValue(
//                    errorResponse
//                )
//            }
//        })

    }

    override fun errorLiveData(): LiveData<ErrorResponse> = errorLiveData


}