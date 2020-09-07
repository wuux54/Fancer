package com.fancer

import android.util.Log
import android.util.Log.VERBOSE
import androidx.multidex.MultiDexApplication
import app.config.AppConfig
import app.util.LogUtils
import app.util.ToastUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc：凡星点点，点点繁星。你会是夜空中最亮的那颗。
 */

class MainApplication : MultiDexApplication() {

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white) //全局设置主题颜色
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            // 指定为经典Header，默认是 贝塞尔雷达Header
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            // 指定为经典Header，默认是 贝塞尔雷达Header
            MaterialHeader(context)
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
    }

    companion object {
        private lateinit var appContext: MainApplication

        fun applicationContext(): MainApplication = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        LogUtils.setLevel(if (AppConfig.DEBUG) VERBOSE else Log.ERROR)
        ToastUtil.setApplication(appContext)

        //TODO: 创建全局ViewModel 作为全局公用数据
    }


//    fun getAppViewModelProvider(activity: Activity): ViewModelProvider? {
//        return ViewModelProvider(
//            activity.applicationContext as com.kunminx.puremusic.App,
//            (activity.applicationContext as com.kunminx.puremusic.App).getAppFactory(activity)
//        )
//    }
//
//    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory? {
//        val application = checkApplication(activity)
//        if (mFactory == null) {
//            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        }
//        return mFactory
//    }
//
//    private fun checkApplication(activity: Activity): Application {
//        return activity.application
//            ?: throw IllegalStateException(
//                "Your activity/fragment is not yet attached to "
//                        + "Application. You can't request ViewModel before onCreate call."
//            )
//    }
}