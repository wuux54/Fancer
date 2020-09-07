package app.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import app.R;


/**
 * @author peiqi_pig
 */
public class ToastUtil {

    private static final int TIME_DURATION = 1500;
    private static final int TYPE_NOR = 0x1101;
    private static final int TYPE_TIPS = 0x1102;

    private static int currType = TYPE_NOR;

    private static Handler sMainThreadHandler;
    private static Toast mToast;
    private static Toast mToast2;
    private static View toastView;


    private static long currData = 0L;

    private static Application application;

    public static void setApplication(Application application) {
        ToastUtil.application = application;
    }

    public static Handler getMainThreadHandler() {
        if (sMainThreadHandler == null) {
            synchronized (ToastUtil.class) {
                if (sMainThreadHandler == null) {
                    sMainThreadHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sMainThreadHandler;
    }

    public static void showToast(int resId) {
        String msg = application.getResources().getString(resId);
        showToast(msg);
    }

    public static void showToast(final String message, final int duration) {
        if (System.currentTimeMillis() - currData < TIME_DURATION) {
            return;
        }
        currData = System.currentTimeMillis();

        if (mToast != null) {
            mToast.cancel();
        }

        getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                mToast = Toast.makeText(application, message, duration);
                mToast.show();
            }
        });
    }

    public static void showToast(final String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(final String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public static void showToastShort(final String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /*----------------------------------------------自定义view的展示---------------------------------------------------------*/
    public static void showToastRes(@StringRes int resId) {
        String msg = application.getResources().getString(resId);
        showToastViewNor(msg);
    }

    public static void showErrorToast(final String errorMsg, final int errorCode) {
        showToastViewNor(errorMsg +
                " 错误码: " +
                errorCode);
    }


    public static void showToastView(final int duration,
                                     final View view,
                                     final int gravity, final int xOffset, final int yOffset) {
        if (System.currentTimeMillis() - currData < TIME_DURATION) {
            return;
        }
        currData = System.currentTimeMillis();

        if (mToast != null) {
            mToast.cancel();
        }
        getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null || mToast.getView().getId() != view.getId()) {
                    mToast = new Toast(application);
                    mToast.setView(view);
                    mToast.setGravity(gravity, xOffset, yOffset);
                    mToast.setDuration(duration);
                }
                mToast.show();
            }
        });
    }

    private static View getView(String message, @DrawableRes int icon, int viewType) {
        if (toastView == null || currType != viewType) {
            switch (viewType) {
                case TYPE_NOR:
                    toastView = LayoutInflater.from(application).inflate(R.layout.toast_normal, null, false);
                    break;
                case TYPE_TIPS:
                    toastView = LayoutInflater.from(application).inflate(R.layout.toast_tips, null, false);
                    break;
                default:
                    break;
            }
            currType = viewType;
        }

        switch (viewType) {
            case TYPE_NOR:
                ((TextView) toastView.findViewById(R.id.tv_msg)).setText(message);
                break;
            case TYPE_TIPS:
                ((TextView) toastView.findViewById(R.id.tv_tips)).setText(message);
                ((ImageView) toastView.findViewById(R.id.iv_icon)).setImageResource(icon);
                break;
            default:
                break;
        }

        return toastView;
    }

    public static void showToastViewNor(final String message) {
        showToastView(Toast.LENGTH_SHORT, getView(message, 0, TYPE_NOR), Gravity.CENTER, 0, 0);
    }

    /*---------------------------------------带ICON的提示--------------------------------------------*/

    public static void showTipsToastView(final String message, @DrawableRes int icon) {
        showToastView(Toast.LENGTH_SHORT, getView(message, icon, TYPE_TIPS),
                Gravity.CENTER, 0, 0);
    }

    public static void showDefSuccess(final String message) {
        showTipsToastView(message, R.mipmap.load_success);
    }

    public static void showDefFail(final String message) {
        showTipsToastView(message, R.mipmap.load_fail);
    }



    /*---------------------------------------新的ToastView--------------------------------------------*/

    public static void showToast2View(final int duration,
                                      final String message, final int gravity, final int xOffset, final int yOffset) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (mToast2 != null) {
            mToast2.cancel();
        }
        getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mToast2 == null) {
                    mToast2 = new Toast(application);
                    View view = LayoutInflater.from(application).inflate(R.layout.toast_normal, null, false);
                    ((TextView) view.findViewById(R.id.tv_msg)).setText(message);
                    mToast2.setView(view);
                    mToast2.setGravity(gravity, xOffset, yOffset);
                    mToast2.setDuration(duration);
                } else {
                    View view = mToast2.getView();
                    ((TextView) view.findViewById(R.id.tv_msg)).setText(message);
                }
                mToast2.show();
            }
        });
    }

    public static void showToast2ViewNor(final String message) {
        showToast2View(Toast.LENGTH_SHORT
                , message
                , Gravity.CENTER, 0, 0);
    }
}
