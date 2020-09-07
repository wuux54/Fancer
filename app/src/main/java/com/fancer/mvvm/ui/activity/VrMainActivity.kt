package com.fancer.mvvm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.util.StatusBarUtils
import com.fancer.R

class VrMainActivity : AppCompatActivity(R.layout.activity_vr_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setTranslucentForImageView(this,0, null)
    }
}
