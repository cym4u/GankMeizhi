package cn.chenyuanming.gankmeizhi.beans;

import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
public class DayGoodsBean implements Serializable {

    /**
     * error : false
     * results : {"App":[{"who":"咕咚","publishedAt":"2016-01-22T05:14:47.797Z","desc":"一个图像处理相关的 Android 开源 App ","type":"App","url":"https://github.com/yaa110/Effects-Pro","used":true,"objectId":"5680992c60b2c1e224d6b5dc","createdAt":"2015-12-28T02:06:36.108Z","updatedAt":"2016-01-22T05:14:48.398Z"}],"iOS":[{"who":"CallMeWhy","publishedAt":"2016-01-22T05:14:47.809Z","desc":"Using Generics to improve TableView cells\n","type":"iOS","url":"http://alisoftware.github.io/swift/generics/2016/01/06/generic-tableviewcells/","used":true,"objectId":"5698c3e460b26385cc9049ca","createdAt":"2016-01-15T10:03:16.945Z","updatedAt":"2016-01-22T05:14:49.336Z"},{"who":"CallMeWhy","publishedAt":"2016-01-22T05:14:47.812Z","desc":"Swift Magic: Public Getter, Private Setter\n","type":"iOS","url":"https://www.natashatherobot.com/swift-magic-public-getter-private-setter/","used":true,"objectId":"5698c3f500b0b6cbfff44dfb","createdAt":"2016-01-15T10:03:33.747Z","updatedAt":"2016-01-22T05:14:48.357Z"},{"who":"Andrew Liu","publishedAt":"2016-01-22T05:14:47.813Z","desc":"iOS开发支付宝集成","type":"iOS","url":"http://www.jianshu.com/p/2b9bbfcb7ec4?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io","used":true,"objectId":"569e4795a633bd005b31923f","createdAt":"2016-01-19T14:26:29.449Z","updatedAt":"2016-01-22T05:14:48.370Z"},{"who":"Andrew Liu","publishedAt":"2016-01-22T05:14:47.815Z","desc":"超越继承之路：协议混合","type":"iOS","url":"http://chengway.in/chao-yue-ji-cheng-zhi-lu-xie-yi-hun-he/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io","used":true,"objectId":"569e47dec24aa80053b4122c","createdAt":"2016-01-19T14:27:42.765Z","updatedAt":"2016-01-22T05:14:49.337Z"},{"who":"Andrew Liu","publishedAt":"2016-01-22T05:14:47.817Z","desc":"波浪进度指示器 WaveLoadingIndicator","type":"iOS","url":"http://zyden.vicp.cc/waveloadingindicator/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io","used":true,"objectId":"569e480ddf0eea0054639986","createdAt":"2016-01-19T14:28:29.964Z","updatedAt":"2016-01-22T05:14:48.413Z"}],"Android":[{"who":"LHF","publishedAt":"2016-01-22T05:14:47.818Z","desc":"转战WebApp: 最适合Android开发者的WebApp框架","type":"Android","url":"http://linfaxin.com/2016/01/20/%E8%BD%AC%E6%88%98WebApp-%E6%9C%80%E9%80%82%E5%90%88Android%E5%BC%80%E5%8F%91%E8%80%85%E7%9A%84WebApp%E6%A1%86%E6%9E%B6/","used":true,"objectId":"569fa47779bc440059dac6b4","createdAt":"2016-01-20T15:15:03.976Z","updatedAt":"2016-01-22T05:14:48.413Z"},{"who":"AllenJuns","publishedAt":"2016-01-22T05:14:47.820Z","desc":"结合七牛提供的API，让加载图片更有效、更节流、更简单、更可控、更酷","type":"Android","url":"https://github.com/lingochamp/QiniuImageLoader","used":true,"objectId":"56a06f7da3413100526a6f6d","createdAt":"2016-01-21T05:41:17.522Z","updatedAt":"2016-01-22T05:14:49.196Z"},{"who":"Jason","publishedAt":"2016-01-22T05:14:47.821Z","desc":"仿QQ消息未读拖拽清除，\u201c一键退朝\u201d，\u201c一键下班\u201d","type":"Android","url":"https://github.com/Qiaoidea/QQTipsView","used":true,"objectId":"56a090c475c4cd6397fcb094","createdAt":"2016-01-21T08:03:16.733Z","updatedAt":"2016-01-22T05:14:49.157Z"},{"who":"Jason","publishedAt":"2016-01-22T05:14:47.830Z","desc":"图片裁剪库(Yalantis出品)","type":"Android","url":"https://github.com/Yalantis/uCrop","used":true,"objectId":"56a10061c24aa800540f8503","createdAt":"2016-01-21T15:59:29.941Z","updatedAt":"2016-01-22T05:14:49.195Z"},{"who":"LHF","publishedAt":"2016-01-22T05:14:47.834Z","desc":"难以置信！雷电OS优化工具竟是\u2026\u2026","type":"Android","url":"http://www.cfan.com.cn/2016/0121/124752.shtml#rd?sukey=7f8f3cb2e9b0da459ac96dea5563f5c1a68a875f873b0e5ee9c6d2c05c375e354d450dec0d86109002e6994fe01a3578","used":true,"objectId":"56a1b10aa34131005275048d","createdAt":"2016-01-22T04:33:14.545Z","updatedAt":"2016-01-22T05:14:49.243Z"}],"前端":[{"who":"Dear宅学长","publishedAt":"2016-01-22T05:14:47.823Z","desc":"devtool 可让你直接在 Chrome 开发者工具直接运行 Node.js 程序, 调试","type":"前端","url":"https://github.com/Jam3/devtool","used":true,"objectId":"56a0cdd07db2a2005a0ce967","createdAt":"2016-01-21T12:23:44.854Z","updatedAt":"2016-01-22T05:14:49.172Z"}],"瞎推荐":[{"who":"lxxself","publishedAt":"2016-01-22T05:14:47.825Z","desc":"php版本的Youtube镜像源码","type":"瞎推荐","url":"https://gochrome.info/youtube-vps/#button_file","used":true,"objectId":"56a0f19a128fe100513dcfec","createdAt":"2016-01-21T14:56:26.678Z","updatedAt":"2016-01-22T05:14:49.183Z"}],"休息视频":[{"who":"LHF","publishedAt":"2016-01-22T05:14:47.826Z","desc":"国外网友在斯洛伐克山脉上拍摄到的冰面。干净得不敢相信。","type":"休息视频","url":"http://video.weibo.com/show?fid=1034:97df7b19ccab3244cda44cf41baae9d7","used":true,"objectId":"56a0f5172e958a0051569497","createdAt":"2016-01-21T15:11:19.070Z","updatedAt":"2016-01-22T05:14:49.187Z"}],"福利":[{"who":"张涵宇","publishedAt":"2016-01-22T05:14:47.832Z","desc":"1.22","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1f082c0b6zyj20f00f0gnr.jpg","used":true,"objectId":"56a1933ea34131005273e41f","createdAt":"2016-01-22T02:26:06.396Z","updatedAt":"2016-01-22T05:14:49.253Z"}]}
     * category : ["App","iOS","Android","前端","瞎推荐","休息视频","福利"]
     */

    public boolean error;
    public Results results;
    public List<String> category;

    @Table("daliInfo")
    public static class Results implements Serializable {
        /**
         * who : 咕咚
         * publishedAt : 2016-01-22T05:14:47.797Z
         * desc : 一个图像处理相关的 Android 开源 App
         * type : App
         * url : https://github.com/yaa110/Effects-Pro
         * used : true
         * objectId : 5680992c60b2c1e224d6b5dc
         * createdAt : 2015-12-28T02:06:36.108Z
         * updatedAt : 2016-01-22T05:14:48.398Z
         */

        public List<Results> App;
        /**
         * who : CallMeWhy
         * publishedAt : 2016-01-22T05:14:47.809Z
         * desc : Using Generics to improve TableView cells
         * <p>
         * type : iOS
         * url : http://alisoftware.github.io/swift/generics/2016/01/06/generic-tableviewcells/
         * used : true
         * objectId : 5698c3e460b26385cc9049ca
         * createdAt : 2016-01-15T10:03:16.945Z
         * updatedAt : 2016-01-22T05:14:49.336Z
         */

        public List<Results> iOS;
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

        public List<Results> Android;
        /**
         * who : Dear宅学长
         * publishedAt : 2016-01-22T05:14:47.823Z
         * desc : devtool 可让你直接在 Chrome 开发者工具直接运行 Node.js 程序, 调试
         * type : 前端
         * url : https://github.com/Jam3/devtool
         * used : true
         * objectId : 56a0cdd07db2a2005a0ce967
         * createdAt : 2016-01-21T12:23:44.854Z
         * updatedAt : 2016-01-22T05:14:49.172Z
         */

        public List<Results> 前端;
        /**
         * who : lxxself
         * publishedAt : 2016-01-22T05:14:47.825Z
         * desc : php版本的Youtube镜像源码
         * type : 瞎推荐
         * url : https://gochrome.info/youtube-vps/#button_file
         * used : true
         * objectId : 56a0f19a128fe100513dcfec
         * createdAt : 2016-01-21T14:56:26.678Z
         * updatedAt : 2016-01-22T05:14:49.183Z
         */

        public List<Results> 瞎推荐;
        /**
         * who : LHF
         * publishedAt : 2016-01-22T05:14:47.826Z
         * desc : 国外网友在斯洛伐克山脉上拍摄到的冰面。干净得不敢相信。
         * type : 休息视频
         * url : http://video.weibo.com/show?fid=1034:97df7b19ccab3244cda44cf41baae9d7
         * used : true
         * objectId : 56a0f5172e958a0051569497
         * createdAt : 2016-01-21T15:11:19.070Z
         * updatedAt : 2016-01-22T05:14:49.187Z
         */

        public List<Results> 休息视频;
        /**
         * who : 张涵宇
         * publishedAt : 2016-01-22T05:14:47.832Z
         * desc : 1.22
         * type : 福利
         * url : http://ww4.sinaimg.cn/large/7a8aed7bjw1f082c0b6zyj20f00f0gnr.jpg
         * used : true
         * objectId : 56a1933ea34131005273e41f
         * createdAt : 2016-01-22T02:26:06.396Z
         * updatedAt : 2016-01-22T05:14:49.253Z
         */

        public List<Results> 福利;

//        @Table("Info")
//        public static class App implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class IOS implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class Android implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class 前端 implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class 瞎推荐 implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class 休息视频 implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
//
//        public static class 福利 implements Serializable {
//            public String who;
//            public String publishedAt;
//            public String desc;
//            public String type;
//            public String url;
//            public boolean used;
//            public String objectId;
//            public String createdAt;
//            public String updatedAt;
//        }
    }
}
