package cn.chenyuanming.gankmeizhi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.jude.swipbackhelper.SwipeBackHelper
import com.umeng.analytics.MobclickAgent

/**
 * Created by Chen Yuanming on 2016/2/1.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwipeBackHelper.onCreate(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }

    public override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart(javaClass.simpleName)
        MobclickAgent.onResume(this)
    }

    public override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(javaClass.simpleName)
        MobclickAgent.onPause(this)
    }
}
