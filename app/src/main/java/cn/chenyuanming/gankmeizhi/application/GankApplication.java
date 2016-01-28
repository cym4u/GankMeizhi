package cn.chenyuanming.gankmeizhi.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/1/25.
 */
public class GankApplication extends Application {
    static GankApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }


}
