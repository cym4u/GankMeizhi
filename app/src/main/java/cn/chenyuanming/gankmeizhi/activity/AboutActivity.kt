package cn.chenyuanming.gankmeizhi.activity

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.utils.PreferenceUtil
import cn.chenyuanming.gankmeizhi.utils.ShareUtils
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.ui.LibsActivity

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
class AboutActivity : LibsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        intent = LibsBuilder()
                .withActivityTitle(getResources().getString(R.string.nav_about))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withAboutAppName(getResources().getString(R.string.app_name))
                .withAboutDescription("http://chenyuanming.cn @KeepCoding")
                .withAboutVersionShown(true)
                .withAboutIconShown(true)
                .withAboutVersionShownCode(true)
                .withAboutVersionShownName(true)
                .withLicenseShown(true)
                .intent(this)

        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        var item: MenuItem
        for (i in 0 until menu.size()) {
            item = menu.getItem(i)
            val drawable = item.icon
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            if (item.itemId == R.id.action_like && PreferenceUtil.instance.getBooleanDefaultFalse("like")) {
                drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
            }
            item.icon = drawable
        }
        return super.onPrepareOptionsMenu(menu)
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_share -> ShareUtils.share(this)
            R.id.action_like -> {
                val drawable = item.icon
                val isLiked = PreferenceUtil.instance.getBooleanDefaultFalse("like")
                if (!isLiked) {
                    drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                    go2StarPage()

                } else {
                    drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                }
                PreferenceUtil.instance.putBoolean("like", !isLiked)
                item.icon = drawable
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun go2StarPage() {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", getString(R.string.project_url))
        intent.putExtra("objectId", "")
        intent.putExtra("useCache", false)
        startActivity(intent)
    }
}