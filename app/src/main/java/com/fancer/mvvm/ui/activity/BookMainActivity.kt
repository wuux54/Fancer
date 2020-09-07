package com.fancer.mvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.util.StatusBarUtils
import com.fancer.R

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/17
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class BookMainActivity  : AppCompatActivity(R.layout.activity_book_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setTranslucentForImageView(this,0, null)
    }
}