package cn.chenyuanming.gankmeizhi.utils

import android.widget.Toast

import cn.chenyuanming.gankmeizhi.application.GankApplication

/**
 * Created by Chen Yuanming on 2016/1/30.
 */
object ToastUtil {
    fun showShortToast(msg: String) {
        Toast.makeText(GankApplication.context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(msg: String) {
        Toast.makeText(GankApplication.context, msg, Toast.LENGTH_LONG).show()
    }
}
