package cn.chenyuanming.gankmeizhi.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
object TimeHelper {
    fun getTime(time: Long): String {
        val now = System.currentTimeMillis()
        if (now - time < 0) {
            return "来自未来"
        } else {
            var temp = (now - time).toFloat()

            temp /= 1000f
            if (temp < 60) {
                return temp.toInt().toString() + "秒前"
            }
            temp /= 60f
            if (temp < 60) {
                return temp.toInt().toString() + "分钟前"
            }
            temp /= 60f
            if (temp < 24) {
                return temp.toInt().toString() + "小时前"
            }
            temp /= 24f
            if (temp < 30) {
                if (temp.toInt() == 1) {
                    return "昨天"
                }
                return if (temp.toInt() == 2) {
                    "前天"
                } else temp.toInt().toString() + "天前"
            }
            //            if (temp < 365) {
            //                return (int) temp / 30 + "个月前";
            //            }
            val date = Date(time)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.format(date)
        }
    }
}
