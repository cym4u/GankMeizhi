package cn.chenyuanming.gankmeizhi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.chenyuanming.gankmeizhi.api.GankApi;
import cn.chenyuanming.gankmeizhi.constants.Constants;
import rx.Observable;

/**
 * Created by Administrator on 2016/1/28.
 */
public class PrefetchService extends Service {
    private static final String TAG = "PrefetchService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int limit = Constants.LIMIT;
    int pageIndex = Constants.START;

    @Override
    public void onCreate() {
        super.onCreate();
        GankApi.getInstance().getBenefitsGoods(limit, pageIndex).subscribe(goodsBean -> {
            Observable.from(goodsBean.results).subscribe(results -> {
                //每天的结果
//                try {
//                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(results.updatedAt);
//                    Log.d(TAG, "onCreate: " + date.toLocaleString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                load(results.url);
            });
        });
    }

    private void load(String url) {
        WebView webView = new WebView(this);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    Log.d(TAG, "onProgressChanged: " + view.getUrl());
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webviewcache";
        webView.getSettings().setDatabasePath(cacheDirPath);
        webView.getSettings().setAppCachePath(cacheDirPath);
        webView.getSettings().setAppCacheEnabled(true);

        webView.requestFocus();
        webView.getSettings().getAllowFileAccess();

        webView.loadUrl(url);
    }
}
