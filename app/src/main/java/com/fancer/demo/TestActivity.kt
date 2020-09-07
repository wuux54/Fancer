package com.fancer.demo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.fancer.BR
import com.fancer.R
import kotlinx.android.synthetic.main.activity_test.*

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
class TestActivity : AppCompatActivity(R.layout.activity_test) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataList: Array<String> = Array(3) {
            "data->$it"
        }
        dataList[0]="1111111111"


        vp_test.adapter = TestAdapter(
            dataList,
            this,
            BR.itemTest,
            R.layout.item_test,
            LayoutInflater.from(this)
        )

    }
}