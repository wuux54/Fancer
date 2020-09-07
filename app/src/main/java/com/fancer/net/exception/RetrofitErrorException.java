package com.fancer.net.exception;

import android.text.TextUtils;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/7/22
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class RetrofitErrorException extends Exception {
    private int errorCode;
    private String errorMessage;

    public RetrofitErrorException(int errorCode,
                                  String errorMessage) {
        this.errorCode = errorCode;


        this.errorMessage = TextUtils.isEmpty(errorMessage) ? "未知错误" : errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
