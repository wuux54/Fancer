package app.util;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;


/**
 * 所有Activity管理类
 */
public class ActivityContainerUtils {
    private ActivityContainerUtils() {
    }

    private static ActivityContainerUtils instance = new ActivityContainerUtils();
    private static List<AppCompatActivity> activityStack = new LinkedList<>();


    public static ActivityContainerUtils getInstance() {
        return instance;
    }

    public void addActivity(AppCompatActivity aty) {
        activityStack.add(aty);
    }


    public int getCurrIndex() {
        return activityStack.size() - 1;
    }

    public int getActivitySize() {
        return activityStack.size();
    }

    public AppCompatActivity getActivity(int index) {
        if (activityStack.size() <= index) {
            return null;
        }
        return activityStack.get(index);
    }

    public void removeActivity(AppCompatActivity aty) {
        activityStack.remove(aty);
    }


    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            AppCompatActivity activity = activityStack.get(i);
            if (null != activity) {
                activity.finish();
            }
        }
        activityStack.clear();
        activityStack = null;
    }

    /**
     * 结束除了MainActivity的Activity
     */
    public void finishOtherActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            AppCompatActivity activity = activityStack.get(i);
            if (null != activity) {
                if (!activity.getClass().getName().equals("com.yd.mvp.ui.activity.MainActivity")) {
                    activity.finish();
//          activityStack.remove(i);
                }
            }
        }
//    activityStack.clear();
//    activityStack = null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            //根据进程ID，杀死该进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //退出真个应用程序
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Class targetActivity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            AppCompatActivity activity = activityStack.get(i);
            if (null != activity) {
                if (TextUtils.equals(activity.getClass().getName(), targetActivity.getName())) {
                    activity.finish();
                }
            }
        }
    }
}
