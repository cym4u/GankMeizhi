package cn.chenyuanming.gankmeizhi.api

import cn.chenyuanming.gankmeizhi.application.GankApplication
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import cn.chenyuanming.gankmeizhi.beans.DayGoodsBean
import cn.chenyuanming.gankmeizhi.constants.Constants
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.File


/**
 * Created by Chen Yuanming on 2016/1/25.
 */
class GankApi private constructor() {

    private val gankService: GankService

    init {
        val cache: Cache
        var okHttpClient: OkHttpClient? = null
        try {
            val cacheDir = File(GankApplication.context.getCacheDir().getPath(), "gank_cache.json")
            cache = Cache(cacheDir, (10 * 1024 * 1024).toLong())
            okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            //            okHttpClient.setCache(cache);//TODO
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val restAdapter = Retrofit.Builder()
                .baseUrl(Constants.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //                .setRequestInterceptor(mRequestInterceptor)
                .build()
        gankService = restAdapter.create(GankService::class.java)
    }

    interface GankService {

        @GET("data/Android/{limit}/{page}")
        fun getAndroidGoods(
                @Path("limit") limit: Int,
                @Path("page") page: Int
        ): Observable<CommonGoodsBean>

        @GET("data/iOS/{limit}/{page}")
        fun getIosGoods(
                @Path("limit") limit: Int,
                @Path("page") page: Int
        ): Observable<CommonGoodsBean>

        @GET("data/all/{limit}/{page}")
        fun getAllGoods(
                @Path("limit") limit: Int,
                @Path("page") page: Int
        ): Observable<CommonGoodsBean>

        @GET("data/福利/{limit}/{page}")
        fun getBenefitsGoods(
                @Path("limit") limit: Int,
                @Path("page") page: Int
        ): Observable<CommonGoodsBean>

        @GET("/day/{year}/{month}/{day}")
        fun getGoodsByDay(
                @Path("year") year: Int,
                @Path("month") month: Int,
                @Path("day") day: Int
        ): Observable<DayGoodsBean>
    }

    fun getCommonGoods(type: String, limit: Int, page: Int): Observable<CommonGoodsBean> {
        if ("Android".equals(type, ignoreCase = true)) {
            return gankService.getAndroidGoods(limit, page)
        }
        return if ("IOS".equals(type, ignoreCase = true)) {
            gankService.getIosGoods(limit, page)
        } else gankService.getAndroidGoods(limit, page)
    }

    fun getAndroidGoods(limit: Int, page: Int): Observable<CommonGoodsBean> {
        return gankService.getAndroidGoods(limit, page).compose(applySchedulers())
    }


    fun getIosGoods(limit: Int, page: Int): Observable<CommonGoodsBean> {
        return gankService.getIosGoods(limit, page).compose(applySchedulers())
    }

    fun getAllGoods(limit: Int, page: Int): Observable<CommonGoodsBean> {
        return gankService.getAllGoods(limit, page).compose(applySchedulers())
    }

    fun getBenefitsGoods(limit: Int, page: Int): Observable<CommonGoodsBean> {
        return gankService.getBenefitsGoods(limit, page).compose(applySchedulers())
    }

    fun getGoodsByDay(year: Int, month: Int, day: Int): Observable<DayGoodsBean> {
        return gankService.getGoodsByDay(year, month, day).compose(applySchedulers())
    }

    private fun <T> applySchedulers() = ObservableTransformer<T, T> { upstream ->
        upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private var instance: GankApi? = null

        fun getInstance(): GankApi {
            if (instance == null) {
                synchronized(GankApi::class.java) {
                    if (instance == null) {
                        instance = GankApi()
                    }
                }
            }
            return instance!!
        }
    }
}
