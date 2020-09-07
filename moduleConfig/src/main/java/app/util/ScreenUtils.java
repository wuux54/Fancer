package app.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

/**
 * 获得屏幕相关的辅助类
 */
public class ScreenUtils {
  private ScreenUtils() {
        /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 获得屏幕高度
   *
   * @param context
   * @return
   */
  public static int getScreenWidth(Context context) {
    WindowManager wm = (WindowManager) context
            .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(outMetrics);
    return  outMetrics.widthPixels;
  }

  /**
   * 获得屏幕宽度
   *
   * @param context
   * @return
   */
  public static int getScreenHeight(Context context) {
    WindowManager wm = (WindowManager) context
            .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(outMetrics);
    return  outMetrics.heightPixels ;
  }

  /**
   * 获得状态栏的高度
   *
   * @param context
   * @return
   */
  public static int getStatusHeight(Context context) {

    int statusHeight = -1;
    try {
      Class<?> clazz = Class.forName("com.android.internal.R$dimen");
      Object object = clazz.newInstance();
      int height = Integer.parseInt(clazz.getField("status_bar_height")
              .get(object).toString());
      statusHeight = context.getResources().getDimensionPixelSize(height);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return statusHeight;
  }

  /**
   * 获取当前屏幕截图，包含状态栏
   *
   * @param activity
   * @return
   */
  public static Bitmap snapShotWithStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = null;
    bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
    view.destroyDrawingCache();
    return bp;

  }

  /**
   * 获取当前屏幕截图，不包含状态栏
   *
   * @param activity
   * @return
   */
  public static Bitmap snapShotWithoutStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    int statusBarHeight = frame.top;

    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = null;
    bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
            - statusBarHeight);
    view.destroyDrawingCache();
    return bp;

  }


  ///

  /**
   * 获取手机显示App区域的大小（头部导航栏+ActionBar+根布局），不包括虚拟按钮
   * @return
   */
  public static int[] getAppSize(Context mContext){
    int[] size = new int[2];
    DisplayMetrics metrics = getDisplayMetrics(mContext);
    size[0] = metrics.widthPixels;
    size[1] = metrics.heightPixels;
    return size;
  }

  /**
   * 获取整个手机屏幕的大小(包括虚拟按钮)
   * 必须在onWindowFocus方法之后使用
   * @param activity
   * @return
   */
  public static int[] getScreenSize(AppCompatActivity activity){
    int[] size = new int[2];
    View decorView = activity.getWindow().getDecorView();
    size[0] = decorView.getWidth();
    size[1] = decorView.getHeight();
    return size;
  }

  /**
   * 获取导航栏的高度
   * @return
   */
  public static int getStatusBarHeight(Context mContext){
    Resources resources =mContext.getResources();
    int resourceId = resources.getIdentifier("status_bar_height","dimen","android");
    return resources.getDimensionPixelSize(resourceId);
  }

  /**
   * 获取虚拟按键的高度
   * @return
   */
  public static int getNavigationBarHeight(Context mContext) {
    int navigationBarHeight = 0;
    Resources rs = mContext.getResources();
    int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
    if (id > 0 && hasNavigationBar(mContext)) {
      navigationBarHeight = rs.getDimensionPixelSize(id);
    }
    return navigationBarHeight;
  }

  /**
   * 是否存在虚拟按键
   * @return
   */
  private static boolean hasNavigationBar(Context mContext) {
    boolean hasNavigationBar = false;
    Resources rs = mContext.getResources();
    int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
    if (id > 0) {
      hasNavigationBar = rs.getBoolean(id);
    }
    try {
      Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
      Method m = systemPropertiesClass.getMethod("get", String.class);
      String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
      if ("1".equals(navBarOverride)) {
        hasNavigationBar = false;
      } else if ("0".equals(navBarOverride)) {
        hasNavigationBar = true;
      }
    } catch (Exception e) {
    }
    return hasNavigationBar;
  }

  public static DisplayMetrics getDisplayMetrics(Context mContext){
    DisplayMetrics metrics =
            mContext
            .getResources()
            .getDisplayMetrics();
    return metrics;
  }
}  
