package cn.chenyuanming.gankmeizhi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.chenyuanming.gankmeizhi.beans.GoodsBean;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class GankFragment extends Fragment {
    private static final String TAG = "GankFragment";
    public static final int FRAG_TYPE_ALL = 1;
    public static final int FRAG_TYPE_MEIZHI = 2;
    public static final int FRAG_TYPE_ANDROID = 3;
    public static final int FRAG_TYPE_IOS = 4;

    public static final String ARG_FRAG_TYPE = "frag_arg_type";

    private int thisFragType = 1;
    private Activity mainActivity;

    public static Fragment newInstance(int type) {
        GankFragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_FRAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    boolean isLoading = false;
    int limit = Constants.LIMIT;
    int startPage = Constants.START;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.fragment_prod_list, container, false);

        mainActivity = getActivity();
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecylerViewLoadUtils.canDoRefresh(rv)&&!isLoading) {
                    Log.d(TAG, "onScrolled: 加载。。。");
                    isLoading = true;
                    startPage++;
                    loadData(startPage);
                }
            }
        });

        thisFragType = getArguments().getInt(ARG_FRAG_TYPE, 0);
        Log.d("MSW", "The Frag Type is: " + thisFragType);

        loadData(startPage);

        return rv;
    }

    private void loadData(int startPage) {
        switch (thisFragType) {
            case FRAG_TYPE_ALL:
                GankApi.getInstance().getAllGoods(limit, startPage).observeOn(AndroidSchedulers.mainThread()).subscribe((goodsBean) -> {
                    Log.i(TAG, "onCreateView: " + goodsBean);
                    Observable.from(goodsBean.results).subscribe(results -> {
                        setupRecyclerView(rv, goodsBean.results);
                        Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
                    });
                });

                break;
            case FRAG_TYPE_MEIZHI:
                GankApi.getInstance().getBenefitsGoods(limit, startPage).observeOn(AndroidSchedulers.mainThread()).subscribe(goodsBean -> {
                    setupRecyclerView(rv, goodsBean.results);
                    Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
                });
                break;
            case FRAG_TYPE_ANDROID:
                GankApi.getInstance().getAndroidGoods(limit, startPage).observeOn(AndroidSchedulers.mainThread()).subscribe(goodsBean -> {
                    setupRecyclerView(rv, goodsBean.results);
                    Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
                });
                break;
            case FRAG_TYPE_IOS:
                GankApi.getInstance().getIosGoods(limit, startPage).observeOn(AndroidSchedulers.mainThread()).subscribe(goodsBean -> {
                    setupRecyclerView(rv, goodsBean.results);
                    Log.i(TAG, thisFragType + "onCreateView: " + goodsBean.results);
                });
                break;
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<GoodsBean.Results> results) {
        isLoading = false;
        switch (thisFragType) {
            case (FRAG_TYPE_ALL):
            default:
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//                recyclerView.addItemDecoration(new GridDividerDecoration(recyclerView.getContext()));
                recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), results));
                break;
//            case (FRAG_TYPE_DEVICE):
//                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
//                recyclerView.addItemDecoration(new GridDividerDecoration(recyclerView.getContext()));
//                recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
//                        getDataList()));
//                break;
            case (FRAG_TYPE_MEIZHI):
//                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                //TODO - since this is varied content length, it would be better as cards
                // https://www.google.com/design/spec/components/cards.html#cards-content
//                recyclerView.addItemDecoration(new GridDividerDecoration(recyclerView.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                        results));

                break;
        }
    }


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private List<GoodsBean.Results> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mTextView;
            public final ImageView mImageView;

            public ViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mImageView = (ImageView) view.findViewById(R.id.iv);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position).desc;
        }

        Context context;

        public SimpleStringRecyclerViewAdapter(Context context, List<GoodsBean.Results> items) {
            this.context = context;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mTextView.setText(mValues.get(position).desc);
            Glide.with(context).load(mValues.get(position).url).into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    public static class SimpleStaggaredRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStaggaredRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<GoodsBean.Results> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(R.id.text_title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position).desc;
        }

        public SimpleStaggaredRecyclerViewAdapter(Context context, List<GoodsBean.Results> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stag_list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).desc;
            holder.mTextView.setText(mValues.get(position).desc);
            // We will set random length text to offset this view for staggarred effect

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(mValues.get(position).url)
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
