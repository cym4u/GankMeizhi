package cn.chenyuanming.gankmeizhi.utils

import android.content.Context
import android.content.SharedPreferences
import cn.chenyuanming.gankmeizhi.application.GankApplication

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
class PreferenceUtil(val context: Context,val name: String) {

    private val mContext = GankApplication.context
    private val mName = "default"

    private val sharedPreferences: SharedPreferences
        get() = mContext.getSharedPreferences(mName, Context.MODE_APPEND)

    val all: Map<String, *>
        get() {
            val sharedPreferences = sharedPreferences
            return sharedPreferences.all
        }

    fun putString(key: String, value: String) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun putInt(key: String, value: Int) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun putLong(key: String, value: Long) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun getString(key: String): String? {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getString(key, "")
    }

    fun getString(key: String, aDefault: String): String? {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getString(key, aDefault)
    }

    fun getInt(key: String): Int {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defValue: Int): Int {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getInt(key, defValue)
    }

    fun getBoolean(key: String): Boolean {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getBoolean(key, true)
    }

    fun getBooleanDefaultFalse(key: String): Boolean {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getBoolean(key, false)
    }

    fun getLong(key: String): Long {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getLong(key, 0)
    }

    fun getLongDefault(key: String, defValue: Long): Long {
        val sharedPreferences = sharedPreferences
        return sharedPreferences.getLong(key, defValue)
    }

    fun getValue(key: String): Any? {
        val sharedPreferences = sharedPreferences
        val map = sharedPreferences.all
        return if (map != null && !map.isEmpty()) {
            map[key]
        } else null
    }

    fun clearData() {
        val sharedPreferences = sharedPreferences
        sharedPreferences.edit().clear().commit()
    }

    companion object {
        var instance = PreferenceUtil(GankApplication.context, "default")
            internal set
    }
}