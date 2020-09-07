package com.fancer.constant

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/8
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class HttpConstant {

    /**
     * 对应HTTP的状态码
     */
    companion object {


        const val ERROR_RESULT_TOKEN_INVALID= 0x500

        const val ERROR_PARSE = 0x501
        const val ERROR_CONNECTION_NOT = 0x502
        const val ERROR_NET_NOT = 0x503
        const val ERROR_CONNECTION_TIMEOUT = 0x504
        const val ERROR_NO = 0x505
        const val ERROR_HTTP = 0x506
        const val ERROR_TOKEN_INVALID = 0x507
        const val ERROR_HOST_UNKNOWN = 0x508

        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val METHOD_NOT_ALLOWED = 405
        const val REQUEST_TIMEOUT = 408
        const val CONFLICT = 409
        const val PRECONDITION_FAILED = 412
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504

        /*---------------------请求源类型----------------------*/
        const val REQUEST_TYPE_KY_HOME_LIST=0x001
        const val REQUEST_TYPE_KY_HOME_LIST_NEXT=0x002
    }
}