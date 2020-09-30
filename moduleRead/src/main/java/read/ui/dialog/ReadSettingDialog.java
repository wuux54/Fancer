package read.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import app.util.BrightnessUtils;
import app.util.DensityUtils;
import read.R;
import read.local.ReadSettingManager;
import read.ui.adapter.PageStyleAdapter;
import read.widget.page.BasePageLoader;
import read.widget.page.PageMode;
import read.widget.page.PageStyle;


public class ReadSettingDialog extends Dialog {
    private static final String TAG = "ReadSettingDialog";
    private static final int DEFAULT_TEXT_SIZE = 16;

    private ImageView mIvBrightnessMinus;
    private SeekBar mSbBrightness;
    private ImageView mIvBrightnessPlus;
    private CheckBox mCbBrightnessAuto;
    private TextView mTvFontMinus;
    private TextView mTvFont;
    private TextView mTvFontPlus;
    private CheckBox mCbFontDefault;
    private RadioGroup mRgPageMode;

    private RadioButton mRbSimulation;
    private RadioButton mRbCover;
    private RadioButton mRbSlide;
    private RadioButton mRbScroll;
    private RadioButton mRbNone;
    private RecyclerView mRvBg;
    private TextView mTvMore;
    /************************************/
//    private PageStyleAdapter mPageStyleAdapter;
    private ReadSettingManager mSettingManager;
    private BasePageLoader mPageLoader;
    private Activity mActivity;

    private PageMode mPageMode;
    private PageStyle mPageStyle;

    private int mBrightness;
    private int mTextSize;

    private boolean isBrightnessAuto;
    private boolean isTextDefault;


    public ReadSettingDialog(@NonNull Activity activity, BasePageLoader mPageLoader) {
        super(activity, R.style.ReadSettingDialog);
        mActivity = activity;
        this.mPageLoader = mPageLoader;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_read_setting);
        setUpWindow();
        initView();
        initData();
        initWidget();
        initClick();
    }

    private void initView() {
        mIvBrightnessMinus = findViewById(R.id.read_setting_iv_brightness_minus);

        mSbBrightness = findViewById(R.id.read_setting_sb_brightness);

        mIvBrightnessPlus = findViewById(R.id.read_setting_iv_brightness_plus);

        mCbBrightnessAuto = findViewById(R.id.read_setting_cb_brightness_auto);

        mTvFontMinus = findViewById(R.id.read_setting_tv_font_minus);

        mTvFont = findViewById(R.id.read_setting_tv_font);

        mTvFontPlus = findViewById(R.id.read_setting_tv_font_plus);

        mCbFontDefault = findViewById(R.id.read_setting_cb_font_default);

        mRgPageMode = findViewById(R.id.read_setting_rg_page_mode);


        mRbSimulation = findViewById(R.id.read_setting_rb_simulation);

        mRbCover = findViewById(R.id.read_setting_rb_cover);

        mRbSlide = findViewById(R.id.read_setting_rb_slide);

        mRbScroll = findViewById(R.id.read_setting_rb_scroll);

        mRbNone = findViewById(R.id.read_setting_rb_none);

        mRvBg = findViewById(R.id.read_setting_rv_bg);

        mTvMore = findViewById(R.id.read_setting_tv_more);
    }

    //设置Dialog显示的位置
    private void setUpWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

    private void initData() {
        mSettingManager = ReadSettingManager.getInstance();

        isBrightnessAuto = mSettingManager.isBrightnessAuto();
        mBrightness = mSettingManager.getBrightness();
        mTextSize = mSettingManager.getTextSize(getContext());
        isTextDefault = mSettingManager.isDefaultTextSize();
        mPageMode = mSettingManager.getPageMode();
        mPageStyle = mSettingManager.getPageStyle();
    }

    private void initWidget() {
        mSbBrightness.setProgress(mBrightness);
        mTvFont.setText(mTextSize + "");
        mCbBrightnessAuto.setChecked(isBrightnessAuto);
        mCbFontDefault.setChecked(isTextDefault);
        initPageMode();
        //RecyclerView
        setUpAdapter();
    }
    PageStyleAdapter  mPageStyleAdapter;
    private void setUpAdapter() {
        Drawable[] drawables = {
                getDrawable(R.color.nb_read_bg_1)
                , getDrawable(R.color.nb_read_bg_2)
                , getDrawable(R.color.nb_read_bg_3)
                , getDrawable(R.color.nb_read_bg_4)
                , getDrawable(R.color.nb_read_bg_5)};
//
        mPageStyleAdapter = new PageStyleAdapter();
        mRvBg.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mRvBg.setAdapter(mPageStyleAdapter);
        mPageStyleAdapter.refreshItems(Arrays.asList(drawables));

        mPageStyleAdapter.setPageStyleChecked(mPageStyle);

    }

    private void initPageMode() {
        switch (mPageMode) {
            case SIMULATION:
                mRbSimulation.setChecked(true);
                break;
            case COVER:
                mRbCover.setChecked(true);
                break;
            case SLIDE:
                mRbSlide.setChecked(true);
                break;
            case NONE:
                mRbNone.setChecked(true);
                break;
            case SCROLL:
                mRbScroll.setChecked(true);
                break;
        }
    }

    private Drawable getDrawable(int drawRes) {
        return ContextCompat.getDrawable(getContext(), drawRes);
    }

    private void initClick() {
        //亮度调节
        mIvBrightnessMinus.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      if (mCbBrightnessAuto.isChecked()) {
                                                          mCbBrightnessAuto.setChecked(false);
                                                      }
                                                      int progress = mSbBrightness.getProgress() - 1;
                                                      if (progress < 0) return;
                                                      mSbBrightness.setProgress(progress);
                                                      BrightnessUtils.setBrightness(mActivity, progress);
                                                  }
                                              }
        );
        mIvBrightnessPlus.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (mCbBrightnessAuto.isChecked()) {
                                                         mCbBrightnessAuto.setChecked(false);
                                                     }
                                                     int progress = mSbBrightness.getProgress() + 1;
                                                     if (progress > mSbBrightness.getMax()) return;
                                                     mSbBrightness.setProgress(progress);
                                                     BrightnessUtils.setBrightness(mActivity, progress);
                                                     //设置进度
                                                     ReadSettingManager.getInstance().setBrightness(progress);
                                                 }
                                             }
        );

        mSbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (mCbBrightnessAuto.isChecked()) {
                    mCbBrightnessAuto.setChecked(false);
                }
                //设置当前 Activity 的亮度
                BrightnessUtils.setBrightness(mActivity, progress);
                //存储亮度的进度条
                ReadSettingManager.getInstance().setBrightness(progress);
            }
        });

        mCbBrightnessAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                             if (isChecked) {
                                                                 //获取屏幕的亮度
                                                                 BrightnessUtils.setBrightness(mActivity, BrightnessUtils.getScreenBrightness(mActivity));
                                                             } else {
                                                                 //获取进度条的亮度
                                                                 BrightnessUtils.setBrightness(mActivity, mSbBrightness.getProgress());
                                                             }
                                                             ReadSettingManager.getInstance().setAutoBrightness(isChecked);
                                                         }
                                                     }
        );

        //字体大小调节
        mTvFontMinus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCbFontDefault.isChecked()) {
                            mCbFontDefault.setChecked(false);
                        }
                        int fontSize = Integer.valueOf(mTvFont.getText().toString()) - 1;
                        if (fontSize < 0) {
                            return;
                        }
                        mTvFont.setText(fontSize + "");
                        mPageLoader.setTextSize(fontSize);
                    }
                });

        mTvFontPlus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCbFontDefault.isChecked()) {
                            mCbFontDefault.setChecked(false);
                        }
                        int fontSize = Integer.valueOf(mTvFont.getText().toString()) + 1;
                        mTvFont.setText(fontSize + "");
                        mPageLoader.setTextSize(fontSize);
                    }
                }
        );

        mCbFontDefault.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            int fontSize = DensityUtils.dp2px(getContext(), DEFAULT_TEXT_SIZE);
                            mTvFont.setText(fontSize + "");
                            mPageLoader.setTextSize(fontSize);
                        }
                    }
                }
        );

        //Page Mode 切换
        mRgPageMode.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        PageMode pageMode;
//                        switch (checkedId) {
//                            case R.id.read_setting_rb_simulation:
//                                pageMode = PageMode.SIMULATION;
//                                break;
//                            case R.id.read_setting_rb_cover:
//                                pageMode = PageMode.COVER;
//                                break;
//                            case R.id.read_setting_rb_slide:
//                                pageMode = PageMode.SLIDE;
//                                break;
//                            case R.id.read_setting_rb_scroll:
//                                pageMode = PageMode.SCROLL;
//                                break;
//                            case R.id.read_setting_rb_none:
//                                pageMode = PageMode.NONE;
//                                break;
//                            default:
//                                pageMode = PageMode.SIMULATION;
//                                break;
//                        }
//                        mPageLoader.setPageMode(pageMode);
                    }
                }
        );

//        背景的点击事件
        mPageStyleAdapter.setOnItemClickListener(
                (view, pos) -> mPageLoader.setPageStyle(PageStyle.values()[pos])
        );

        //更多设置
        mTvMore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //                    Intent intent = new Intent(getContext(), MoreSettingActivity.class);
//                    mActivity.startActivityForResult(intent, ReadActivity.REQUEST_MORE_SETTING);
                        //关闭当前设置
                        dismiss();
                    }
                }
        );
    }

    public boolean isBrightFollowSystem() {
        if (mCbBrightnessAuto == null) {
            return false;
        }
        return mCbBrightnessAuto.isChecked();
    }
}
