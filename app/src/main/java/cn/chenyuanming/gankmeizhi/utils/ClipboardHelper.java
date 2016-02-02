package cn.chenyuanming.gankmeizhi.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.ClipboardManager;

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
public class ClipboardHelper {

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    @SuppressWarnings("deprecation")
    public static void copy(Context context, @NonNull String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

}
