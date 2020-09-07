package com.fancer.demo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import app.BR

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
class TestViewModel : BaseObservable() {
    @get:Bindable
    var text: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
        }
}