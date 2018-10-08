package cn.chenyuanming.gankmeizhi.utils

import cn.chenyuanming.gankmeizhi.application.GankApplication
import com.litesuits.orm.LiteOrm
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
class DbHelper private constructor() {

    val liteOrm: LiteOrm

    init {
        liteOrm = LiteOrm.newSingleInstance(GankApplication.context, "gankmeizhi.db")
    }


    fun <T> getData(clazz: Class<T>): MutableList<T> {
        var list: MutableList<T>? = getHelper().liteOrm.query(clazz)
        if (list == null || list.size == 0) {
            list = ArrayList()
            try {
                list.add(clazz.newInstance())
                getHelper().liteOrm.save(list)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        return list
    }

    companion object {
        private  var helper: DbHelper = DbHelper()

        fun getHelper(): DbHelper {
            return helper
        }
    }
}
