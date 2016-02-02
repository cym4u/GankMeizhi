package cn.chenyuanming.gankmeizhi.utils;

import android.widget.Toast;

import cn.chenyuanming.gankmeizhi.application.GankApplication;

/**
 * Created by Chen Yuanming on 2016/1/30.
 */
public class ToastUtil {
    public static void showShortToast(String msg) {
        Toast.makeText(GankApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg) {
        Toast.makeText(GankApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
