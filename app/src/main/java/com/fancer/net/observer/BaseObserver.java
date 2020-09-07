package com.fancer.net.observer;

import com.fancer.MainApplication;
import com.fancer.constant.HttpConstant;
import com.fancer.entity.response.ErrorResponse;
import com.fancer.net.exception.RetrofitErrorException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import app.util.NetUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/22
 * @E-mail: W_SpongeBob@163.com
 * @Desc： RxJava 需要取消订阅操作，防止内存泄漏
 */
abstract public class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable disposable) {
    }

    @Override
    public void onNext(T t) {
        if (t==null){
            onError( HttpConstant.ERROR_NO, "未知错误");
        }else {
            onSuccess(t);
        }
    }


    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        ErrorResponse errorResponse = errorResponse(e);
        onError(errorResponse.getErrorCode(), errorResponse.getErrorMsg());
    }

    public static ErrorResponse errorResponse(Throwable e){
        int errorCode;
        String errorMsg;
        if (!NetUtils.isConnected(MainApplication.Companion.applicationContext())) {
            //网络未连接
            errorCode = HttpConstant.ERROR_NET_NOT;
            errorMsg = "网络未连接,请检查您的网络";
        } else if (e instanceof RetrofitErrorException) {
            switch (((RetrofitErrorException) e).getErrorCode()) {
                case HttpConstant.ERROR_RESULT_TOKEN_INVALID:
                    errorCode = HttpConstant.ERROR_TOKEN_INVALID;
                    errorMsg = "用户认证过期";
                    break;
                default:
                    errorCode = ((RetrofitErrorException) e).getErrorCode();
                    errorMsg = ((RetrofitErrorException) e).getErrorMessage();
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            errorCode = HttpConstant.ERROR_PARSE;
            errorMsg = "数据解析错误";
        } else if (e instanceof java.net.UnknownHostException) {
            //网络未连接
            errorCode = HttpConstant.ERROR_HOST_UNKNOWN;
            errorMsg = "网络未连接";
        } else if (e instanceof SocketTimeoutException) {
            //网络未连接
            errorCode = HttpConstant.ERROR_CONNECTION_TIMEOUT;
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            //均视为网络错误
            errorCode = HttpConstant.ERROR_CONNECTION_NOT;
            errorMsg = "服务器连接失败";
        } else if (e instanceof HttpException) {
            switch (((HttpException) e).code()) {
                case HttpConstant.BAD_REQUEST:
                case HttpConstant.UNAUTHORIZED:
                case HttpConstant.FORBIDDEN:
                case HttpConstant.NOT_FOUND:
                case HttpConstant.METHOD_NOT_ALLOWED:
                case HttpConstant.REQUEST_TIMEOUT:
                case HttpConstant.CONFLICT:
                case HttpConstant.PRECONDITION_FAILED:
                case HttpConstant.GATEWAY_TIMEOUT:
                case HttpConstant.INTERNAL_SERVER_ERROR:
                case HttpConstant.BAD_GATEWAY:
                case HttpConstant.SERVICE_UNAVAILABLE:
                    //均视为网络错误
                default:
                    errorCode = HttpConstant.ERROR_HTTP;
                    errorMsg = "网络错误";
                    break;
            }
        } else {
            errorCode = HttpConstant.ERROR_NO;
            errorMsg = "未知错误";
        }

        return new ErrorResponse(errorCode,errorMsg,-1);
    }

    /**
     * 错误返回
     *
     * @param errorCode 错误码
     * @param errorMsg  错误日志
     */
    abstract public void onError(int errorCode, String errorMsg);


    /**
     * 成功结果
     *
     * @param data 返回的数据
     */
    abstract public void onSuccess(T data);
}
