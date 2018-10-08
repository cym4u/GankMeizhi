package cn.chenyuanming.gankmeizhi.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.beans.db.FavoriteBean
import cn.chenyuanming.gankmeizhi.utils.DbHelper
import cn.chenyuanming.gankmeizhi.utils.ShareUtils
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlinx.android.synthetic.main.activity_webview.*
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
class WebViewActivity : BaseActivity() {

    internal var url = ""

    internal var favorite = DbHelper.getHelper().getData(FavoriteBean::class.java)[0]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setupSwipeRefreshLayout()
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        initWebView(webView)

        url = intent.getStringExtra("url")
        val useCache = intent.getBooleanExtra("useCache", true)
        if (useCache) {
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        } else {
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
        webView.loadUrl(url)
    }

    private fun setupSwipeRefreshLayout() {
        //设置卷内的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light)
        swipeRefreshLayout.direction = SwipyRefreshLayoutDirection.TOP
        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener { direction ->
            webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
            webView.reload()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        var item: MenuItem
        for (i in 0 until menu.size()) {
            item = menu.getItem(i)
            val drawable = item.icon
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            item.icon = drawable
            if (item.itemId == R.id.action_favorite) {
                changeFavoriteIcon(item, favorite.favorites, url)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBack()
                return true
            }
            R.id.action_share -> {
                //TODO
                ShareUtils.share(this, webView.title + " " + webView.url)
                return true
            }
            R.id.action_favorite -> {
                onFavoriteClicked(item, favorite.favorites, url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                //                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    loadingFrame.visibility = View.GONE
                }
                swipeRefreshLayout.isRefreshing = newProgress != 100
            }

        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                setHomeIndicator(view)
            }
        }
        webView.settings.builtInZoomControls = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        val cacheDirPath = filesDir.absolutePath + "/webviewcache"
        webView.settings.databasePath = cacheDirPath
        webView.settings.setAppCachePath(cacheDirPath)
        webView.settings.setAppCacheEnabled(true)

        webView.requestFocus()
        webView.settings.allowFileAccess
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack()
        }
        return true
    }

    private fun onBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
            setHomeIndicator(webView)
        } else {
            finish()
        }
    }

    private fun setHomeIndicator(webView: WebView) {
        if (webView.canGoBack()) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        } else {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    private fun onFavoriteClicked(item: MenuItem, favorites: TreeSet<String>, objectId: String) {
        if (favorites.contains(objectId)) {
            favorites.remove(objectId)
        } else {
            favorites.add(objectId)
        }
        DbHelper.getHelper().liteOrm.save(favorite)
        changeFavoriteIcon(item, favorites, objectId)
    }

    private fun changeFavoriteIcon(item: MenuItem, favorites: TreeSet<String>, objectId: String) {
        val drawable = item.icon

        if (!TextUtils.isEmpty(objectId) && favorite.favorites.contains(objectId)) {
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
            item.icon = drawable
        } else {
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            item.icon = drawable
        }
    }

}
