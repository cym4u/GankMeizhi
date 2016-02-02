package cn.chenyuanming.gankmeizhi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.adapter.ArticleViewAdapter;
import cn.chenyuanming.gankmeizhi.adapter.MeizhiAdapter;
import cn.chenyuanming.gankmeizhi.api.GankApi;
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean;
import cn.chenyuanming.gankmeizhi.beans.db.DbGoodsBean;
import cn.chenyuanming.gankmeizhi.constants.Constants;
import cn.chenyuanming.gankmeizhi.decoration.SpacesItemDecoration;
import cn.chenyuanming.gankmeizhi.utils.DbHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class GankFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipyRefreshLayout swipeRefreshLayout;

    private static final String TAG = "GankFragment";
    public static final int FRAG_TYPE_ALL = 1;
    public static final int FRAG_TYPE_MEIZHI = 2;
    public static final int FRAG_TYPE_ANDROID = 3;
    public static final int FRAG_TYPE_IOS = 4;

    public static final String ARG_FRAG_TYPE = "frag_arg_type";

    private int thisFragType = 1;
    static int limit = Constants.LIMIT;
    static int currentPage = Constants.START;
    ArticleViewAdapter allAdapter;
    MeizhiAdapter meizhiAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(true);
        }
    };

    public static Fragment newInstance(int type) {
        GankFragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_FRAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);
        setupAdapter();
        setupSwipeRefreshLayout();
        currentPage = Constants.START;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoadCache()) {
            loadData(currentPage);
            handler.sendEmptyMessageDelayed(0, 1);
        }
    }

    private void setupSwipeRefreshLayout() {
        //设置卷内的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener((direction) -> {


            clearAdapterCache(direction);
            clearDbCache();
            loadData(currentPage);
        });


    }

    private void clearDbCache() {
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                dbGoodsBean.allResults.clear();
                break;
            case FRAG_TYPE_MEIZHI:
                dbGoodsBean.meizhiResults.clear();
                break;
            case FRAG_TYPE_ANDROID:
                dbGoodsBean.androidResults.clear();
                break;
            case FRAG_TYPE_IOS:
                dbGoodsBean.iosResults.clear();
                break;
        }
    }

    private void clearAdapterCache(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            currentPage = Constants.START;
            if (allAdapter != null) {
                allAdapter.getDatas().clear();
            }
            if (meizhiAdapter != null) {
                meizhiAdapter.getDatas().clear();
            }
        }
    }

    private void setupAdapter() {
        thisFragType = getArguments().getInt(ARG_FRAG_TYPE, 0);
        if (thisFragType == FRAG_TYPE_MEIZHI) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            SpacesItemDecoration decoration = new SpacesItemDecoration(16);
            recyclerView.addItemDecoration(decoration);
            meizhiAdapter = new MeizhiAdapter(getActivity(), null);
            recyclerView.setAdapter(meizhiAdapter);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            allAdapter = new ArticleViewAdapter(getActivity(), null, thisFragType);
            recyclerView.setAdapter(allAdapter);
        }
    }

    private void loadData(int pageIndex) {
        //timeout
        Observable.timer(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(time -> swipeRefreshLayout.setRefreshing(false));
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                GankApi.getInstance().getAllGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction(), e -> showNoNetWorkToast(e));
                break;
            case FRAG_TYPE_MEIZHI:
                GankApi.getInstance().getBenefitsGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction(), e -> showNoNetWorkToast(e));
                break;
            case FRAG_TYPE_ANDROID:
                GankApi.getInstance().getAndroidGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction(), e -> showNoNetWorkToast(e));
                break;
            case FRAG_TYPE_IOS:
                GankApi.getInstance().getIosGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction(), e -> showNoNetWorkToast(e));
                break;
        }
    }

    private void showNoNetWorkToast(Throwable e) {
        Log.d(TAG, "showNoNetWorkToast() called with: " + "e = [" + e + "]");
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
//        ToastUtil.showShortToast("网络不给力，请稍后再试");
    }

    private boolean isLoadCache() {
        List<CommonGoodsBean.Results> datas = loadDataFromDb();
        if (datas != null && datas.size() > 0) {
            currentPage = datas.size() / Constants.LIMIT; //如果有缓存,加载更多处理
            setupRecyclerView(datas);
            return true;
        }
        return false;
    }

    private List<CommonGoodsBean.Results> loadDataFromDb() {
        DbGoodsBean dbGoodsBean = DbHelper.getHelper().getData(DbGoodsBean.class).get(0);
        List<CommonGoodsBean.Results> datas = new ArrayList<>();
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                datas = dbGoodsBean.allResults;
                break;
            case FRAG_TYPE_MEIZHI:
                datas = dbGoodsBean.meizhiResults;
                break;
            case FRAG_TYPE_ANDROID:
                datas = dbGoodsBean.androidResults;
                break;
            case FRAG_TYPE_IOS:
                datas = dbGoodsBean.iosResults;
                break;
        }
        return datas;
    }

    DbGoodsBean dbGoodsBean = DbHelper.getHelper().getData(DbGoodsBean.class).get(0);

    @NonNull
    private Action1<CommonGoodsBean> getGoodsBeanAction() {
        return goodsBean -> {

            setupRecyclerView(goodsBean.results);
            saveData2Db(goodsBean);
            Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
        };
    }

    private void saveData2Db(CommonGoodsBean goodsBean) {
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                dbGoodsBean.allResults.addAll(goodsBean.results);
                break;
            case FRAG_TYPE_MEIZHI:
                dbGoodsBean.meizhiResults.addAll(goodsBean.results);
                break;
            case FRAG_TYPE_ANDROID:
                dbGoodsBean.androidResults.addAll(goodsBean.results);
                break;
            case FRAG_TYPE_IOS:
                dbGoodsBean.iosResults.addAll(goodsBean.results);
                break;
        }

        DbHelper.getHelper().getLiteOrm().save(dbGoodsBean);
    }

    private void setupRecyclerView(List<CommonGoodsBean.Results> results) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (results.size() > 0) {
            currentPage++;
        }
        switch (thisFragType) {
            case (FRAG_TYPE_MEIZHI):
                if (currentPage == Constants.START) {
                    meizhiAdapter.getDatas().clear();
                }
                results.removeAll(meizhiAdapter.getDatas());
                meizhiAdapter.getDatas().addAll(results);
//                meizhiAdapter.notifyItemRangeChanged(meizhiAdapter.getItemCount() - results.size(), results.size());
                meizhiAdapter.notifyDataSetChanged();
                break;
            default:
                if (currentPage == Constants.START) {
                    allAdapter.getDatas().clear();
                }
                results.removeAll(allAdapter.getDatas());
                allAdapter.getDatas().addAll(results);
//                allAdapter.notifyItemRangeChanged(allAdapter.getItemCount() - results.size(), results.size());
                allAdapter.notifyDataSetChanged();
                break;
        }
    }


}