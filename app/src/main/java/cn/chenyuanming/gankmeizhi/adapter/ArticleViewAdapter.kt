package cn.chenyuanming.gankmeizhi.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.activity.ShowBigImageActivity
import cn.chenyuanming.gankmeizhi.activity.WebViewActivity
import cn.chenyuanming.gankmeizhi.application.GankApplication.Companion.context
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import cn.chenyuanming.gankmeizhi.beans.db.FavoriteBean
import cn.chenyuanming.gankmeizhi.beans.db.ReadArticles
import cn.chenyuanming.gankmeizhi.utils.DbHelper
import cn.chenyuanming.gankmeizhi.utils.ShareUtils
import cn.chenyuanming.gankmeizhi.utils.TimeHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_webview.view.*
import kotlinx.android.synthetic.main.item_article.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
class ArticleViewAdapter(items: List<CommonGoodsBean.Results>?, internal var fragType: Int) : RecyclerView.Adapter<ArticleViewAdapter.ViewHolder>() {

    private val mDatas = ArrayList<CommonGoodsBean.Results>()

    internal var favorite = DbHelper.getHelper().getData(FavoriteBean::class.java)[0]
    internal var readArticles = DbHelper.getHelper().getData(ReadArticles::class.java)[0]
    internal var currDate = Date()

    val datas: MutableList<CommonGoodsBean.Results>
        get() = mDatas

    class ViewHolder(internal var mainView: View) : RecyclerView.ViewHolder(mainView) {

        init {
            context = mainView.context
            defaultShare = mainView.ivShare.drawable
            defaultShare.setColorFilter(Color.parseColor("#bfc8d6"), PorterDuff.Mode.SRC_IN)
            mainView.ivShare.setImageDrawable(defaultShare)
        }
    }

    fun getValueAt(position: Int): String? {
        return mDatas[position].desc
    }

    init {
        if (items != null) {
            mDatas.addAll(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mDatas[position]

        holder.mainView.tvTitle.text = data.desc

        try {
            val publishDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(data.publishedAt)
            holder.mainView.tvTime.text = TimeHelper.getTime(publishDate.time)
        } catch (e: ParseException) {
            holder.mainView.tvTime.text = data.updatedAt.substring(0, data.updatedAt.indexOf("T"))
            e.printStackTrace()
        }

        holder.mainView.tvAuthor.text = "by @" + data.who
        holder.mainView.tvType.text = data.type
        if (readArticles.articles.contains(data.url)) {
            holder.mainView.tvTitle.setTextColor(context.resources.getColor(R.color.lightBlack))
        } else {
            holder.mainView.tvTitle.setTextColor(context.resources.getColor(R.color.black))
        }


        if (data.type == "福利") {
            holder.mainView.tvTitle.visibility = View.GONE
            holder.mainView.ivMeizhi.visibility = View.VISIBLE
            Glide.with(context).load(data.url).into(holder.mainView.ivMeizhi)
            holder.mainView.setOnClickListener { v ->
                val intent = Intent(context, ShowBigImageActivity::class.java)
                intent.putExtra("data", mDatas[position])
                context.startActivity(intent)
            }
        } else {
            holder.mainView.tvTitle.visibility = View.VISIBLE
            holder.mainView.ivMeizhi.visibility = View.GONE
            holder.mainView.setOnClickListener { v ->
                holder.mainView.tvTitle.setTextColor(context.resources.getColor(R.color.lightBlack))
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", mDatas[position].url)
                context.startActivity(intent)
                readArticles.articles.add(data.url)
                DbHelper.getHelper().liteOrm.save(readArticles)
            }
        }

        setFavoriteIcon(holder.mainView.ivFavorite, favorite.favorites, data.url)
        holder.mainView.ivFavorite.setOnClickListener { v ->
            onFavoriteClicked(holder.mainView.ivFavorite, favorite.favorites, data.url)
            DbHelper.getHelper().liteOrm.save(favorite)
        }

        holder.mainView.ivShare.setOnClickListener { v -> ShareUtils.share(context, data.desc + data.url) }

//        when (data.type) {
//            "Android" -> holder.mainView.tvType.setBackgroundResource(R.drawable.shapeType_android)
//            "iOS" -> holder.mainView.tvType.setBackgroundResource(R.drawable.shapeType_ios)
//            "拓展资源", "App" -> holder.mainView.tvType.setBackgroundResource(R.drawable.shapeType_extend)
//            "休息视频" -> holder.mainView.tvType.setBackgroundResource(R.drawable.shapeType_relax)
//            else -> holder.mainView.tvType.setBackgroundResource(R.drawable.shapeType_relax)
//        }
    }

    private fun initWebview(webView: WebView) {
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.settings.builtInZoomControls = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.settings.setAppCacheEnabled(true)

        webView.requestFocus()
        webView.settings.allowFileAccess
    }

    private fun onFavoriteClicked(ivFavorite: ImageView, favorites: TreeSet<String>, objectId: String) {
        if (favorites.contains(objectId)) {
            favorites.remove(objectId)
        } else {
            favorites.add(objectId)
        }
        setFavoriteIcon(ivFavorite, favorites, objectId)
    }

    private fun setFavoriteIcon(ivFavorite: ImageView, favorites: TreeSet<String>, objectId: String) {
        val drawable = ivFavorite.drawable
        if (favorites.contains(objectId)) {
            drawable.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN)
            ivFavorite.setImageDrawable(drawable)
        } else {
            drawable.setColorFilter(Color.parseColor("#bfc8d6"), PorterDuff.Mode.SRC_IN)
            ivFavorite.setImageDrawable(drawable)
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    companion object {
        lateinit var defaultShare: Drawable
        lateinit var context: Context
    }

}
