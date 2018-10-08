package cn.chenyuanming.gankmeizhi.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.UiThread
import cn.chenyuanming.gankmeizhi.api.GankApi
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import cn.chenyuanming.gankmeizhi.constants.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
class PrefetchService : Service() {

    internal var limit = Constants.LIMIT
    internal var pageIndex = Constants.START

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        GankApi.getInstance().getBenefitsGoods(limit, pageIndex).subscribe { goodsBean ->
            Observable.fromIterable<CommonGoodsBean.Results>(goodsBean.results).observeOn(AndroidSchedulers.mainThread()).subscribe { results ->
                //每天的结果
                try {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(results.updatedAt)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                load(results.url)
            }
        }
    }

    @UiThread
    private fun load(url: String) {
        val webView = WebView(this)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    Log.d(TAG, "onProgressChanged: " + view.url)
                }
            }
        }
        webView.webViewClient = WebViewClient()
        webView.settings.builtInZoomControls = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        val cacheDirPath = filesDir.absolutePath + "/webviewcache"
        webView.settings.databasePath = cacheDirPath
        webView.settings.setAppCachePath(cacheDirPath)
        webView.settings.setAppCacheEnabled(true)

        webView.requestFocus()
        webView.settings.allowFileAccess

        webView.loadUrl(url)
    }

    companion object {
        private val TAG = "PrefetchService"
    }
}
