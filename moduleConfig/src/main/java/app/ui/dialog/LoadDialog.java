package app.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import app.R;


/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/21
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class LoadDialog extends Dialog {
    private ProgressBar progressBar;
    private ImageView ivIcon;
    private TextView tvTips;

    private Context mContext;
    private @DrawableRes
    int mIcon;
    private String mTips;
    private boolean mCanTouchOutside = false;
    private boolean mCancelable = false;


    public LoadDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LoadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }


    public LoadDialog setTips(String tips) {
        this.mTips = tips;
        return this;
    }

    public LoadDialog setIcon(int icon) {
        this.mIcon = icon;
        return this;
    }

    public LoadDialog setMyCanceledOnTouchOutside(boolean canTouchOutside) {
        this.mCanTouchOutside = canTouchOutside;
        return this;
    }
    public LoadDialog setMyCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);

//        setCanceledOnTouchOutside(mCanTouchOutside);
//        setCancelable(mCancelable);
        initView();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        ivIcon = findViewById(R.id.iv_icon);
        tvTips = findViewById(R.id.tv_tips);

        updateUi();
    }


    @Override
    public void show() {
        updateUi();
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    private void updateUi() {
        setCanceledOnTouchOutside(mCanTouchOutside);
        setCancelable(mCancelable);

        if (tvTips == null) {
            return;
        }

        if (TextUtils.isEmpty(mTips)) {
            tvTips.setVisibility(View.GONE);
        } else {
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText(mTips);
        }

        if (mIcon == 0) {
            progressBar.setVisibility(View.VISIBLE);
            ivIcon.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(mIcon);
        }
    }
}
