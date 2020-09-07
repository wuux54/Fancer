package com.fancer.mvvm.intrf

import androidx.lifecycle.LiveData
import com.fancer.entity.response.ErrorResponse
import com.fancer.entity.response.KYHome

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/23
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
interface RequestItf {
    interface ErrorResponseItf {
        fun errorLiveData(): LiveData<ErrorResponse>
    }

    interface MainRequest : ErrorResponseItf {

        fun getLiveDataUser(): LiveData<KYHome>

        fun getUserInfo()
    }

    interface KyHomeRequest : ErrorResponseItf {

        fun getLiveDataKyHomeList(): LiveData<KYHome>

        fun getKyHomeList()

        fun getKyHomeList(url:String)

    }
}