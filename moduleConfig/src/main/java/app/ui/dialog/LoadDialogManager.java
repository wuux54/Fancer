package app.ui.dialog;

import android.content.Context;

import androidx.annotation.DrawableRes;

import app.R;


/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/7/31
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class LoadDialogManager {

    private LoadDialog dialog;
    private Context context;

    private LoadDialogManager(Context context) {
        this.context = context;
    }

    public static LoadDialogManager newInstance(Context context) {
        return new LoadDialogManager(context);
    }

    private void createDialog() {
        dialog = new LoadDialog(context, R.style.DialogLoadStyle);
    }

    public void showTip(String tips, @DrawableRes int icon) {
        showTip(tips, icon, true, true);
    }

    public void showTip(String tips, @DrawableRes int icon, boolean mCanTouchOutside, boolean mCancelable) {
        if (dialog == null) {
            createDialog();
        }

        dialog.setMyCancelable(mCancelable)
                .setMyCanceledOnTouchOutside(mCanTouchOutside)
                .setIcon(icon)
                .setTips(tips)
                .show();
    }

    public void showDefProgress() {
        showTip(context.getResources().getString(R.string.tipsLoadProgress), 0);
    }

    public void showDefSuccess() {
        showTip(context.getResources().getString(R.string.tipsLoadSuccess), R.mipmap.load_success);
    }

    public void showDefFail() {
        showTip(context.getResources().getString(R.string.tipsLoadFail), R.mipmap.load_fail);
    }

    public void showProgress(String tips) {
        showTip(tips, 0);
    }

    public void showSuccess(String tips) {
        showTip(tips, R.mipmap.load_success);
    }


    public void showFail(String tips) {
        showTip(tips, R.mipmap.load_fail);
    }


    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void recycleDialog() {
        hideDialog();
        if (dialog != null) {
            dialog = null;
        }
    }

}
