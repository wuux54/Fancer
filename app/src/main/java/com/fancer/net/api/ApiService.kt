package com.fancer.net.api

import com.fancer.demo.Bean
import com.fancer.entity.response.KYHome
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * 作者: 凡星-fancer
 * 日期: 2019/7/16.
 * 邮箱: W_SpongeBob@163.com
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 *
 * @author 凡星-fancer
 */
interface ApiService {
    /**
     * 开眼首页
     * @return 首页数据
     */
    @GET("tabs/selected")
    fun kyHomeList(): Observable<KYHome>

    /**
     * 开眼首页
     * @return 首页数据
     */
    @GET("tabs/selected")
    fun kyHomeList2(): Deferred<KYHome>

    @GET("/posts")
    fun getPosts(): Deferred<List<Bean>>


    @GET
    fun kyHomeListNext(@Url url: String): Deferred<KYHome>
}