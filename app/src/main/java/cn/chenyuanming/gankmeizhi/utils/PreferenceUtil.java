package cn.chenyuanming.gankmeizhi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

import cn.chenyuanming.gankmeizhi.application.GankApplication;

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
public class PreferenceUtil {

    private Context mContext = GankApplication.getContext();
    private String mName = "default";

    public PreferenceUtil(Context context, String name) {
        this.mContext = context;
        this.mName = name;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mName, Context.MODE_APPEND);
    }

    static PreferenceUtil instance = new PreferenceUtil(GankApplication.getContext(),"default");

    public static PreferenceUtil getInstance() {
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(key, "");
    }

    public String getString(String key, String aDefault) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(key, aDefault);
    }

    public int getInt(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getBoolean(key, true);
    }

    public boolean getBooleanDefaultFalse(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getLong(key, 0);
    }

    public long getLongDefault(String key, long defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getLong(key, defValue);
    }

    public Object getValue(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Map<String, ?> map = sharedPreferences.getAll();
        if (map != null && !map.isEmpty()) {
            return map.get(key);
        }
        return null;
    }

    public Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Map<String, ?> map = sharedPreferences.getAll();
        return map;
    }

    public void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().clear().commit();
    }
}