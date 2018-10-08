package cn.chenyuanming.gankmeizhi.utils

import android.content.Context
import android.text.ClipboardManager

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
object ClipboardHelper {

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    fun copy(context: Context, content: String) {
        // 得到剪贴板管理器
        val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.text = content.trim { it <= ' ' }
    }

}
