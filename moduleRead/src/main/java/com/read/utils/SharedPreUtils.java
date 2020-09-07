package com.read.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by newbiechen on 17-4-16.
 */

public class SharedPreUtils {
    private static final String SHARED_NAME = "IReader_pref";
    private static SharedPreUtils sInstance;
    private SharedPreferences sharedReadable;
    private SharedPreferences.Editor sharedWritable;

    private static Application appContext;

    public static void setApplication(Application app){
        appContext = app;
    }

    private SharedPreUtils(){
        sharedReadable = appContext
                .getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS);
        sharedWritable = sharedReadable.edit();
    }

    public static SharedPreUtils getInstance(){
        if(sInstance == null){
            synchronized (SharedPreUtils.class){
                if (sInstance == null){
                    sInstance = new SharedPreUtils();
                }
            }
        }
        return sInstance;
    }

    public String getString(String key){
        return sharedReadable.getString(key,"");
    }

    public void putString(String key, String value){
        sharedWritable.putString(key,value);
        sharedWritable.commit();
    }

    public void putInt(String key, int value){
        sharedWritable.putInt(key, value);
        sharedWritable.commit();
    }

    public void putBoolean(String key, boolean value){
        sharedWritable.putBoolean(key, value);
        sharedWritable.commit();
    }

    public int getInt(String key, int def){
        return sharedReadable.getInt(key, def);
    }

    public boolean getBoolean(String key, boolean def){
        return sharedReadable.getBoolean(key, def);
    }
}
