package cn.chenyuanming.gankmeizhi.utils;

import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;

import cn.chenyuanming.gankmeizhi.application.GankApplication;

/**
 * Created by Administrator on 2016/1/28.
 */
public class DbHelper {
    private static DbHelper helper;

    private DbHelper() {
        liteOrm = LiteOrm.newSingleInstance(GankApplication.getContext(), "gankmeizhi.db");
    }

    private LiteOrm liteOrm;

    public static DbHelper getHelper() {
        if (helper == null) {
            helper = new DbHelper();
        }
        return helper;
    }

    public LiteOrm getLiteOrm() {
        return liteOrm;
    }


    public <T> List<T> getData(Class<T> clazz) {
        List<T> list = getHelper().getLiteOrm().query(clazz);
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            try {
                list.add(clazz.newInstance());
                getHelper().getLiteOrm().save(list);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
