package cn.chenyuanming.gankmeizhi.application

import android.app.Application
import android.content.Context

/**
 * Created by Chen Yuanming on 2016/1/25.
 */
class GankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GankApplication

        val context: Context
            get() = instance
    }


}
