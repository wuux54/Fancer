package com.fancer.callback

import com.fancer.entity.response.ErrorResponse

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/2
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
interface NetRequestCallBack<T> {
    fun success(data: T)

    fun error(errorResponse:ErrorResponse)
}