package app.util;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.os.Process;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/9/6
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class WebViewUtils {

    public static WebViewUtils newInstance() {
        return new WebViewUtils();
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================
    };

    public void setWebSetting(WebSettings settings) {
        //设置WebView可触摸放大缩小
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        settings.setUseWideViewPort(false);
        // 设置WebView支持运行普通的Javascript
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);

        settings.setLoadWithOverviewMode(true);

        //保证h5 填充，防止加载不全
        settings.setDomStorageEnabled(true);

        settings.setSavePassword(false);
        settings.setSaveFormData(false);


        //（默认）根据cache-control决定是否从网络上取数据。
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 阻止网络图片加載
        settings.setBlockNetworkImage(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
    }

    /**
     * 初始化WebView配置
     */
    public void initWebViewConfig(WebView webView, WebListener webListener) {
        // 设置WebView初始化尺寸，参数为百分比
//        webView.setInitialScale(100);
        setWebSetting(webView.getSettings());
        addWebViewClient(webView, webListener);
        addChromeClient(webView, webListener);
    }


    private void addWebViewClient(WebView webView, final WebListener webListener) {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (webListener != null) {
                    webListener.onPageStarted(view, url, favicon);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String url;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    url = request.getUrl().toString();
//                } else {
//                    url = request.toString();
//                }
////                view.loadUrl(url);
////                return false;
//
//                if (startUrl != null && startUrl.equals(url)) {
//                    view.loadUrl(url);
//                } else {
//                    //交给系统处理
//                    return super.shouldOverrideUrlLoading(view, url);
//                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webListener != null) {
                    webListener.onPageFinished(view, url);
                }
            }

            /**
             * 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
             *
             * @param view
             */
            // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }

                if (webListener != null) {
                    webListener.onReceivedError(view, errorCode, description, failingUrl);
                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (webListener != null) {
                    webListener.onReceivedError(view, request, error);
                }
            }
        });

    }

    private void addChromeClient(WebView webView, final WebListener webListener) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (webListener != null) {
                    webListener.onProgressChanged(view, newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            //=========HTML5定位==========================================================
            //需要先加入权限
            //<uses-permission android:name="android.permission.INTERNET"/>
            //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
            //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
            //=========HTML5定位==========================================================

            //=========多窗口的问题==========================================================
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();
                return true;
            }
        });
    }

    public void onPause(WebView webView) {
        if (webView == null) {
            return;
        }
        webView.onPause();
        webView.pauseTimers(); //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    public void onResume(WebView webView) {
        if (webView == null) {
            return;
        }
        webView.onResume();
        webView.resumeTimers();
    }

    /**
     * 销毁资源
     */
    public void destroy(WebView webView) {
        if (webView == null) {
            return;
        }
        //停止加载
        webView.stopLoading();
        //把webView从视图中移除
        ((ViewGroup) webView.getParent()).removeView(webView);
        //移除webView上子view
        webView.removeAllViews();
        //清除缓存
        webView.clearCache(true);
        //清除历史
        webView.clearHistory();
        //销毁webView自身
        webView.destroy();

//        if (webView != null) {
//            webView.clearHistory();
//            ((ViewGroup) webView.getParent()).removeView(webView);
//            webView.loadUrl("about:blank");
//            webView.stopLoading();
//            webView.setWebChromeClient(null);
//            webView.setWebViewClient(null);
//            webView.destroy();
//            webView = null;
//        }
    }

    /**
     * 销毁资源
     */
    public void destroyForProcess(WebView webView) {
        if (webView == null) {
            Process.killProcess(Process.myPid());
            return;
        }
        //停止加载
        webView.stopLoading();
        //把webView从视图中移除
        ((ViewGroup) webView.getParent()).removeView(webView);
        //移除webView上子view
        webView.removeAllViews();
        //清除缓存
        webView.clearCache(true);
        //清除历史
        webView.clearHistory();
        //销毁webView自身
        webView.destroy();
        //杀死WebView所在的进程
        Process.killProcess(Process.myPid());
    }


    public boolean onKeyDown(int keyCode, WebView webView) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return false;
    }


    public interface WebListener {

        void onPageFinished(WebView view, String url);

        void onProgressChanged(WebView view, int newProgress);

        void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

        void onPageStarted(WebView view, String url, Bitmap favicon);
    }
}
