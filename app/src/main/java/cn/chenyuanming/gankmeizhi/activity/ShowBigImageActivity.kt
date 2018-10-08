package cn.chenyuanming.gankmeizhi.activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import cn.chenyuanming.gankmeizhi.beans.db.FavoriteBean
import cn.chenyuanming.gankmeizhi.utils.DbHelper
import cn.chenyuanming.gankmeizhi.utils.RxMeizhi
import cn.chenyuanming.gankmeizhi.utils.ShareUtils
import cn.chenyuanming.gankmeizhi.utils.ToastUtil
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_show_img.*
import java.util.*

/**
 * Created by livin on 2016/1/28.
 */
class ShowBigImageActivity : BaseActivity() {
    lateinit var data: CommonGoodsBean.Results

    internal var favorite = DbHelper.getHelper().getData(FavoriteBean::class.java)[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_img)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val `object` = intent.getSerializableExtra("data")
        if (`object` is CommonGoodsBean.Results) {
            data = `object`
            Glide.with(this).load(data.url).into(ivMeizhi)
            changeFavoriteIcon(ivFavorite, favorite.favorites, data.url)
        }

        ivSave.setOnClickListener {
            ToastUtil.showShortToast("保存中...")
            RxMeizhi.saveImageAndGetPathObservable(this, data.url, data.desc).observeOn(AndroidSchedulers.mainThread()).subscribe { uri -> ToastUtil.showShortToast("保存成功") }
        }

        ivShare.setOnClickListener {
            RxMeizhi.saveImageAndGetPathObservable(this, data.url, data.desc).subscribe { uri -> ShareUtils.shareImage(this@ShowBigImageActivity, uri, data.desc) }
        }

        ivFavorite.setOnClickListener {
            onFavoriteClicked(ivFavorite, favorite.favorites, data.url)
            DbHelper.getHelper().liteOrm.save(favorite)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onFavoriteClicked(ivFavorite: ImageView, favorites: TreeSet<String>, objectId: String) {
        if (favorites.contains(objectId)) {
            favorites.remove(objectId)
        } else {
            favorites.add(objectId)
        }
        changeFavoriteIcon(ivFavorite, favorites, objectId)
    }

    private fun changeFavoriteIcon(ivFavorite: ImageView, favorites: TreeSet<String>, url: String) {
        val drawable = ivFavorite.drawable
        if (favorites.contains(url)) {
            drawable.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN)
            ivFavorite.setImageDrawable(drawable)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }

}
