package cn.chenyuanming.gankmeizhi.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.adapter.ArticleViewAdapter
import cn.chenyuanming.gankmeizhi.adapter.MeizhiAdapter
import cn.chenyuanming.gankmeizhi.api.GankApi
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import cn.chenyuanming.gankmeizhi.beans.db.DbGoodsBean
import cn.chenyuanming.gankmeizhi.constants.Constants
import cn.chenyuanming.gankmeizhi.decoration.SpacesItemDecoration
import cn.chenyuanming.gankmeizhi.utils.DbHelper
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_article_list.*
import java.util.concurrent.TimeUnit

class GankFragment : BaseFragment() {

    private var thisFragType = 1
    private var allAdapter: ArticleViewAdapter? = null
    private var meizhiAdapter: MeizhiAdapter? = null

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            swipeRefreshLayout.isRefreshing = true
        }
    }

    private//如果有缓存,加载更多处理
    val isLoadCache: Boolean
        get() {
            val datas = loadDataFromDb()
            if ( datas.size > 0) {
                currentPage = datas.size / Constants.LIMIT
                setupRecyclerView(datas)
                return true
            }
            return false
        }

    internal var dbGoodsBean = DbHelper.getHelper().getData(DbGoodsBean::class.java)[0]


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
        currentPage = Constants.START

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        setupSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoadCache) {
            loadData(currentPage)
            handler.sendEmptyMessageDelayed(0, 1)
        }
    }

    private fun setupSwipeRefreshLayout() {
        //设置卷内的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light)
        swipeRefreshLayout.direction = SwipyRefreshLayoutDirection.BOTH
        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener { direction ->


            clearAdapterCache(direction)
            clearDbCache()
            loadData(currentPage)
        }


    }

    private fun clearDbCache() {
        when (thisFragType) {
            FRAG_TYPE_ALL -> dbGoodsBean.allResults.clear()
            FRAG_TYPE_MEIZHI -> dbGoodsBean.meizhiResults.clear()
            FRAG_TYPE_ANDROID -> dbGoodsBean.androidResults.clear()
            FRAG_TYPE_IOS -> dbGoodsBean.iosResults.clear()
        }
    }

    private fun clearAdapterCache(direction: SwipyRefreshLayoutDirection) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            currentPage = Constants.START
            allAdapter?.datas?.clear()
            meizhiAdapter?.datas?.clear()
        }
    }

    private fun setupAdapter() {
        thisFragType = arguments?.getInt(ARG_FRAG_TYPE, 0) ?: 0
        if (thisFragType == FRAG_TYPE_MEIZHI) {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val decoration = SpacesItemDecoration(16)
            recyclerView.addItemDecoration(decoration)
            meizhiAdapter = MeizhiAdapter(null)
            recyclerView.adapter = meizhiAdapter
        } else {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            allAdapter = ArticleViewAdapter(null, thisFragType)
            recyclerView.adapter = allAdapter
        }
    }

    val consumer: (CommonGoodsBean) -> Unit = { goodsBean ->
        setupRecyclerView(goodsBean.results)
        saveData2Db(goodsBean)
        Log.i(TAG, thisFragType.toString() + "onCreateView: " + goodsBean.results)
    }

    private fun loadData(pageIndex: Int) {
        //timeout
        Observable.timer(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { time -> swipeRefreshLayout?.isRefreshing = false }
        when (thisFragType) {
            FRAG_TYPE_ALL -> GankApi.getInstance().getAllGoods(limit, pageIndex).subscribe(consumer, { e -> showNoNetWorkToast(e) })
            FRAG_TYPE_MEIZHI -> GankApi.getInstance().getBenefitsGoods(limit, pageIndex).subscribe(consumer, { e -> showNoNetWorkToast(e) })
            FRAG_TYPE_ANDROID -> GankApi.getInstance().getAndroidGoods(limit, pageIndex).subscribe(consumer, { e -> showNoNetWorkToast(e) })
            FRAG_TYPE_IOS -> GankApi.getInstance().getIosGoods(limit, pageIndex).subscribe(consumer, { e -> showNoNetWorkToast(e) })
        }
    }

    private fun showNoNetWorkToast(e: Throwable) {
        Log.d(TAG, "showNoNetWorkToast() called with: e = [$e]")
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        //        ToastUtil.showShortToast("网络不给力，请稍后再试");
    }

    private fun loadDataFromDb(): MutableList<CommonGoodsBean.Results> {
        val dbGoodsBean = DbHelper.getHelper().getData(DbGoodsBean::class.java)[0]
        var data: MutableList<CommonGoodsBean.Results> = mutableListOf()
        when (thisFragType) {
            FRAG_TYPE_ALL -> data = dbGoodsBean.allResults
            FRAG_TYPE_MEIZHI -> data = dbGoodsBean.meizhiResults
            FRAG_TYPE_ANDROID -> data = dbGoodsBean.androidResults
            FRAG_TYPE_IOS -> data = dbGoodsBean.iosResults

        }
        return data
    }

    private fun saveData2Db(goodsBean: CommonGoodsBean) {
        when (thisFragType) {
            FRAG_TYPE_ALL -> dbGoodsBean.allResults.addAll(goodsBean.results)
            FRAG_TYPE_MEIZHI -> dbGoodsBean.meizhiResults.addAll(goodsBean.results)
            FRAG_TYPE_ANDROID -> dbGoodsBean.androidResults.addAll(goodsBean.results)
            FRAG_TYPE_IOS -> dbGoodsBean.iosResults.addAll(goodsBean.results)
        }

        DbHelper.getHelper().liteOrm.save(dbGoodsBean)
    }

    private fun setupRecyclerView(results: MutableList<CommonGoodsBean.Results>) {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        if (results.size > 0) {
            currentPage++
        }
        when (thisFragType) {
            FRAG_TYPE_MEIZHI -> {
                meizhiAdapter?.let { meizhiAdapter ->
                    if (currentPage == Constants.START) {
                        meizhiAdapter.datas.clear()
                    }
                    results.removeAll(meizhiAdapter.datas)
                    meizhiAdapter.datas.addAll(results)
                    //                meizhiAdapter.notifyItemRangeChanged(meizhiAdapter.getItemCount() - results.size(), results.size());
                    meizhiAdapter.notifyDataSetChanged()
                }

            }
            else -> {
                allAdapter?.let { allAdapter ->
                    if (currentPage == Constants.START) {
                        allAdapter.datas.clear()
                    }
                    results.removeAll(allAdapter.datas)
                    allAdapter.datas.addAll(results)
                    //                allAdapter.notifyItemRangeChanged(allAdapter.getItemCount() - results.size(), results.size());
                    allAdapter.notifyDataSetChanged()
                }

            }
        }
    }

    companion object {

        private val TAG = "GankFragment"
        val FRAG_TYPE_ALL = 1
        val FRAG_TYPE_MEIZHI = 2
        val FRAG_TYPE_ANDROID = 3
        val FRAG_TYPE_IOS = 4

        val ARG_FRAG_TYPE = "frag_arg_type"
        internal var limit = Constants.LIMIT
        internal var currentPage = Constants.START

        fun newInstance(type: Int): Fragment {
            val fragment = GankFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_FRAG_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }


}