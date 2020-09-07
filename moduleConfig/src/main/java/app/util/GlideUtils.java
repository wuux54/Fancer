package app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import app.R;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;


/**
 * Glide 图片处理工具类
 */

public class GlideUtils {
    /**
     * 加载图片本地 通过id
     *
     * @param context
     * @param id
     * @param imageView
     */
    public static void loadImage(Context context, int id, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(id)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 加载图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 加载图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageNoAnim(Context context, String url, ImageView imageView, int errorImage) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(errorImage)
                .placeholder(errorImage)//R.color.image_default_gray_color)
                .dontAnimate()
                .fallback(errorImage)//R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                .skipMemoryCache(false)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 加载图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageSetWidth(Context context, String url, ImageView imageView, int errorImage, int width) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(errorImage)
                .placeholder(R.color.image_default_gray_color)
                .dontAnimate()
                .fitCenter()
                .override(width, SIZE_ORIGINAL)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }


    /**
     * 加载图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView, int errorImage, int otherErrorImage) {
        RequestOptions options = new RequestOptions()
//            .centerCrop()
                .error(TextUtils.isEmpty(url) ? otherErrorImage : errorImage)
                .placeholder(errorImage)//R.color.image_default_gray_color)
//            .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }


    /**
     * 加载图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView, int errorImage) {
        RequestOptions options = new RequestOptions()
//            .centerCrop()
                .error(errorImage)
                .placeholder(errorImage)//R.color.image_default_gray_color)
//            .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 不能设置占位图，设置后部分没有固定大小的图片不显示，有待优化
     *
     * @param context
     * @param url
     * @param imageView
     * @param errorImage
     */
    public static void loadImageNoPlace(Context context, String url, ImageView imageView, int errorImage) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(errorImage)
//            .placeholder(R.color.image_default_gray_color)
                .fallback(errorImage)//R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 加载图片根据地址判断是否缓存
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageAutoCached(Context context, String url, ImageView imageView, int errorImage) {
        if (url.contains("http")) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(errorImage)
                    .placeholder(R.color.image_default_gray_color)
                    .fallback(R.color.image_default_gray_color)
                    .priority(Priority.HIGH)
//            .skipMemoryCache(false);
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                    .into(imageView);
        } else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(errorImage)
                    .placeholder(R.color.image_default_gray_color)
                    .fallback(R.color.image_default_gray_color)
                    .priority(Priority.HIGH)
//            .skipMemoryCache(false);
//            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片 通过url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageCircle(Context context, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }

    /**
     * 加载圆形图片 通过id
     *
     * @param context
     * @param id
     * @param imageView
     */
    public static void loadImageCircle(Context context, int id, ImageView imageView) {
        RequestOptions options = RequestOptions.circleCropTransform()
                .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(id)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }


    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     */
    public static void loadImageRounded(Context context, String url, ImageView imageView, int radius) {
        RequestOptions options = new RequestOptions()
//            .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
//            .transform(new RoundedCorners(radius))
                .transform(new CornersTransform(context, DensityUtils.dp2px(context, radius)))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }


    /**
     * 加载本地圆角图片 通过id
     *
     * @param context
     * @param id
     * @param imageView
     * @param radius    dp
     */
    public static void loadImageRounded(Context context, int id, ImageView imageView, int radius) {
        RequestOptions options = new RequestOptions()
//            .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .transform(new RoundedCorners(radius))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(id)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView);
    }


    public static void loadBitmap(Context context, String path, final ImageView imageView) {
        RequestOptions options = RequestOptions.circleCropTransform()
                .centerCrop()
                .error(R.color.image_default_gray_color)
                .placeholder(R.color.image_default_gray_color)
                .fallback(R.color.image_default_gray_color)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(options)
                .into(new SimpleTarget<Bitmap>(200, 200) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
//                dismissDialog();
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}