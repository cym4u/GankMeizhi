package cn.chenyuanming.gankmeizhi.fragment;

import android.os.Bundle;
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
import android.widget.Toast;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.adapter.ArticleViewAdapter;
import cn.chenyuanming.gankmeizhi.adapter.MeizhiAdapter;
import cn.chenyuanming.gankmeizhi.api.GankApi;
import cn.chenyuanming.gankmeizhi.beans.GoodsBean;
import cn.chenyuanming.gankmeizhi.constants.Constants;
import cn.chenyuanming.gankmeizhi.decoration.SpacesItemDecoration;
import cn.chenyuanming.gankmeizhi.utils.DbHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class GankFragment extends Fragment {
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

        if (!isLoadCache()) {
            loadData(currentPage);
        }
//        prefetch();

        return view;
    }

    private void prefetch() {
        GankApi.getInstance().getBenefitsGoods(limit, currentPage).subscribe(goodsBean -> {
            Observable.from(goodsBean.results).subscribe(results -> {
                //每天的结果
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ").parse(results.updatedAt);
                    Log.d(TAG, "onCreate: " + date.toLocaleString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void setupSwipeRefreshLayout() {
        //设置卷内的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener((direction) -> {
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                currentPage = Constants.START;
                if (allAdapter != null) {
                    allAdapter.getDatas().clear();
                }
                if (meizhiAdapter != null) {
                    meizhiAdapter.getDatas().clear();
                }
            }
            loadData(currentPage);
        });


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
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                GankApi.getInstance().getAllGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction());
                break;
            case FRAG_TYPE_MEIZHI:
                GankApi.getInstance().getBenefitsGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction());
                break;
            case FRAG_TYPE_ANDROID:
                GankApi.getInstance().getAndroidGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction());
                break;
            case FRAG_TYPE_IOS:
                GankApi.getInstance().getIosGoods(limit, pageIndex).observeOn(AndroidSchedulers.mainThread()).subscribe(getGoodsBeanAction());
                break;
        }
    }

    private boolean isLoadCache() {

        QueryBuilder builder = QueryBuilder.create(GoodsBean.Results.class);
        if (thisFragType != FRAG_TYPE_ALL) {
            String type = thisFragType == FRAG_TYPE_MEIZHI ? "福利" :
                    (thisFragType == FRAG_TYPE_ANDROID ? "Android" : "iOS");
            builder.getwhereBuilder().and("type=?", new Object[]{type});

        }
        ArrayList<GoodsBean.Results> datas = DbHelper.getHelper().getLiteOrm().query(builder);
        if (datas != null && datas.size() > 0) {
            Log.d(TAG, "loadData: " + datas.size());
            setupRecyclerView(datas);
            return true;
        }
        return false;
    }

    @NonNull
    private Action1<GoodsBean> getGoodsBeanAction() {
        return goodsBean -> {
            setupRecyclerView(goodsBean.results);
            DbHelper.getHelper().getLiteOrm().save(goodsBean.results);
            Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
        };
    }

    private void setupRecyclerView(List<GoodsBean.Results> results) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            if (results.size() > 0) {
                currentPage++;
            } else {
                Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
            }
        }
        switch (thisFragType) {
            case (FRAG_TYPE_MEIZHI):
                results.removeAll(meizhiAdapter.getDatas());
                meizhiAdapter.getDatas().addAll(results);
//                meizhiAdapter.notifyItemRangeChanged(meizhiAdapter.getItemCount() - results.size(), results.size());
                meizhiAdapter.notifyDataSetChanged();
                break;
            default:
                results.removeAll(allAdapter.getDatas());
                allAdapter.getDatas().addAll(results);
//                allAdapter.notifyItemRangeChanged(allAdapter.getItemCount() - results.size(), results.size());
                allAdapter.notifyDataSetChanged();
                break;
        }
    }


}