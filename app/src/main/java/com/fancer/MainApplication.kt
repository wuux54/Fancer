package com.fancer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.util.Log.VERBOSE
import androidx.multidex.MultiDexApplication
import app.config.AppConfig
import app.util.LogUtils
import app.util.ToastUtil
import com.fancer.utils.LanguageUtils
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
        registerActivityLifecycleCallbacks(activityLifecycle)
        //TODO: 创建全局ViewModel 作为全局公用数据
    }

    private val activityLifecycle = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (!LanguageUtils.isSameWithSetting(activity)) {
                LanguageUtils.changeAppLanguage(
                    activity,
                    LanguageUtils.getLocaleLanguage(activity)
                    , true
                )
            }
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }


        override fun onActivityResumed(activity: Activity) {
        }
    }

}