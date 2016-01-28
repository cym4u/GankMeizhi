package cn.chenyuanming.gankmeizhi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.activity.WebViewActivity;
import cn.chenyuanming.gankmeizhi.beans.GoodsBean;
import cn.chenyuanming.gankmeizhi.beans.ReadArticles;
import cn.chenyuanming.gankmeizhi.fragment.GankFragment;
import cn.chenyuanming.gankmeizhi.utils.DbHelper;

/**
 * Created by Administrator on 2016/1/28.
 */
public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    private List<GoodsBean.Results> mDatas = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView)
        TextView mTextView;
        @Bind(R.id.imageView)
        ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public String getValueAt(int position) {
        return mDatas.get(position).desc;
    }

    Context context;
    int fragType;
    ReadArticles readArticles ;
    public ArticleViewAdapter(Context context, List<GoodsBean.Results> items, int fragType) {
        this.context = context;
        if (items != null) {
            mDatas.addAll(items);
        }
        this.fragType = fragType;
        List<ReadArticles> list = DbHelper.getHelper().getData(ReadArticles.class);
        if(list.size()==0){
            readArticles = new ReadArticles();
            DbHelper.getHelper().getLiteOrm().save(readArticles);
        }else{
            readArticles = list.get(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GoodsBean.Results data = mDatas.get(position);
        String type = "";
        type = (fragType != GankFragment.FRAG_TYPE_ALL) ? "" : ("(" + data.type + ")");
        holder.mTextView.setText(type + data.desc);
        if (readArticles.articles.contains(data.objectId)) {
            holder.mTextView.setTextColor(context.getResources().getColor(R.color.lightBlack));
        } else {
            holder.mTextView.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.mTextView.setOnClickListener(v -> {
            holder.mTextView.setTextColor(context.getResources().getColor(R.color.lightBlack));

            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", mDatas.get(position).url);
            context.startActivity(intent);
            readArticles.articles.add(data.objectId);
            DbHelper.getHelper().getLiteOrm().save(readArticles);
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<GoodsBean.Results> getDatas() {
        return mDatas;
    }
}
