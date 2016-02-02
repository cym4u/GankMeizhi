package cn.chenyuanming.gankmeizhi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
public class TimeHelper {
    public static String getTime(long time) {
        long now = System.currentTimeMillis();
        if (now - time < 0) {
            return "来自未来";
        } else {
            float temp = now - time;

            temp /= 1000;
            if (temp < 60) {
                return (int) temp + "秒前";
            }
            temp /= 60;
            if (temp < 60) {
                return (int) temp + "分钟前";
            }
            temp /= 60;
            if (temp < 24) {
                return (int) temp + "小时前";
            }
            temp /= 24;
            if (temp < 30) {
                if ((int) temp == 1) {
                    return "昨天";
                }
                if ((int) temp == 2) {
                    return "前天";
                }
                return (int) temp + "天前";
            }
//            if (temp < 365) {
//                return (int) temp / 30 + "个月前";
//            }
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }
}
