package com.fancer.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

import app.util.SPUtils;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/10/10
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class LanguageUtils {

    private static final String LANGUAGE = "LANGUAGE";
    private static final String CONUNTRY = "CONUNTRY";

    public static boolean isSameWithSetting(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale locale;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }


        Locale localeLanguage = getLocaleLanguage(context);

        if (localeLanguage == null) {
            saveLanguageSetting(context, locale);
            return true;
        }
        return localeLanguage.getLanguage().equals(locale.getLanguage()) &&
                localeLanguage.getCountry().equals(locale.getCountry());
    }


    /**
     * 更改应用语言
     *
     * @param context
     * @param locale      语言地区
     * @param persistence 是否持久化
     */

    public static void changeAppLanguage(Context context, Locale locale,

                                         boolean persistence) {

        if (locale == null) {
            return;
        }

        Resources resources = context.getResources();
//
        DisplayMetrics metrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            configuration.setLocale(locale);

        } else {

            configuration.locale = locale;

        }

        resources.updateConfiguration(configuration, metrics);

        if (persistence) {

            saveLanguageSetting(context, locale);

        }

    }


    private static void saveLanguageSetting(Context context, Locale locale) {
        SPUtils.put(context, LANGUAGE, locale.getLanguage());
        SPUtils.put(context, CONUNTRY, locale.getCountry());
    }

    public static Locale getLocaleLanguage(Context context) {

        String language = (String) SPUtils.get(context, LANGUAGE, "");
        String country = (String) SPUtils.get(context, CONUNTRY, "");

        if (language == null || country == null
                || language.isEmpty()) {
            return null;
        }
        return new Locale(language, country);
    }
}
