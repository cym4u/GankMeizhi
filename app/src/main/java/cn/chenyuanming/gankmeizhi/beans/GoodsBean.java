package cn.chenyuanming.gankmeizhi.beans;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
public class GoodsBean implements Serializable {

    /**
     * error : false
     * results : [{"who":"LHF","publishedAt":"2016-01-22T05:14:47.818Z","desc":"转战WebApp: 最适合Android开发者的WebApp框架","type":"Android","url":"http://linfaxin.com/2016/01/20/%E8%BD%AC%E6%88%98WebApp-%E6%9C%80%E9%80%82%E5%90%88Android%E5%BC%80%E5%8F%91%E8%80%85%E7%9A%84WebApp%E6%A1%86%E6%9E%B6/","used":true,"objectId":"569fa47779bc440059dac6b4","createdAt":"2016-01-20T15:15:03.976Z","updatedAt":"2016-01-22T05:14:48.413Z"},{"who":"mthli","publishedAt":"2016-01-21T04:50:42.994Z","desc":"Link Bubble 浏览器开源","type":"Android","url":"https://github.com/brave/browser-android","used":true,"objectId":"56a050f12e958a00593e84a3","createdAt":"2016-01-21T03:30:57.645Z","updatedAt":"2016-01-21T04:50:43.558Z"},{"who":"MVP","publishedAt":"2016-01-21T04:50:42.993Z","desc":"react-native-maps,一个react-native的MapView组件，适用于android和iOS","type":"Android","url":"https://github.com/lelandrichardson/react-native-maps","used":true,"objectId":"56a04c56d342d30054ddd6e4","createdAt":"2016-01-21T03:11:18.527Z","updatedAt":"2016-01-21T04:50:43.565Z"},{"who":"MVP","publishedAt":"2016-01-21T04:50:42.991Z","desc":"Picasso 图片转换器","type":"Android","url":"https://github.com/wasabeef/picasso-transformations","used":true,"objectId":"56a04c381532bc0053baa7dd","createdAt":"2016-01-21T03:10:48.317Z","updatedAt":"2016-01-21T04:50:43.515Z"},{"who":"Dear宅学长","publishedAt":"2016-01-21T04:50:42.985Z","desc":"图片处理SDK(功能蛮丰富的)","type":"Android","url":"https://github.com/jarlen/PhotoEditDemo?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io","used":true,"objectId":"569f30431532bc005411db91","createdAt":"2016-01-20T06:59:15.772Z","updatedAt":"2016-01-21T04:50:43.511Z"},{"who":"Jason","publishedAt":"2016-01-21T04:50:42.978Z","desc":"渐变的圆形进度条与轻量横向进度条","type":"Android","url":"https://github.com/lingochamp/MagicProgressWidget","used":true,"objectId":"568de0e4e4b055624a0deca3","createdAt":"2016-01-07T03:52:04.081Z","updatedAt":"2016-01-21T04:50:43.558Z"},{"who":"Jason","publishedAt":"2016-01-21T04:50:42.976Z","desc":"仿iOS的loading效果","type":"Android","url":"https://github.com/Kaopiz/KProgressHUD","used":true,"objectId":"568ddde6e4b055624a0dd880","createdAt":"2016-01-07T03:39:18.034Z","updatedAt":"2016-01-21T04:50:43.504Z"},{"who":"mthli","publishedAt":"2016-01-20T04:59:02.797Z","desc":"BlockCanary 开源了，轻松找出Android App界面卡顿元凶","type":"Android","url":"https://github.com/moduth/blockcanary","used":true,"objectId":"569efc6b7db2a20052189c7c","createdAt":"2016-01-20T03:18:03.377Z","updatedAt":"2016-01-20T04:59:03.472Z"},{"who":"Dear宅学长","publishedAt":"2016-01-20T04:59:02.796Z","desc":"仿苹果手表Launcher","type":"Android","url":"https://github.com/ZhaoKaiQiang/AppleWatchView","used":true,"objectId":"569efc68d342d30053be5ffe","createdAt":"2016-01-20T03:18:00.886Z","updatedAt":"2016-01-20T04:59:03.520Z"},{"who":"Jason","publishedAt":"2016-01-20T04:59:02.791Z","desc":"Android主题应用引擎","type":"Android","url":"https://github.com/afollestad/app-theme-engine","used":true,"objectId":"569e607379bc440059d0fe7e","createdAt":"2016-01-19T16:12:35.568Z","updatedAt":"2016-01-20T04:59:03.474Z"}]
     */

    public boolean error;
    /**
     * who : LHF
     * publishedAt : 2016-01-22T05:14:47.818Z
     * desc : 转战WebApp: 最适合Android开发者的WebApp框架
     * type : Android
     * url : http://linfaxin.com/2016/01/20/%E8%BD%AC%E6%88%98WebApp-%E6%9C%80%E9%80%82%E5%90%88Android%E5%BC%80%E5%8F%91%E8%80%85%E7%9A%84WebApp%E6%A1%86%E6%9E%B6/
     * used : true
     * objectId : 569fa47779bc440059dac6b4
     * createdAt : 2016-01-20T15:15:03.976Z
     * updatedAt : 2016-01-22T05:14:48.413Z
     */

    public List<Results> results;

    @Table("Info")
    public static class Results implements Serializable {
        public String who;
        public String publishedAt;
        public String desc;
        public String type;
        public String url;
        public boolean used;
        @PrimaryKey(AssignType.BY_MYSELF)
        public String objectId;
        public String createdAt;
        public String updatedAt;
        public boolean isRead;

        @Override
        public String toString() {
            return "Results{" +
                    "who='" + who + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", objectId='" + objectId + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    '}';
        }
    }
}
