package cn.chenyuanming.gankmeizhi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.activity.ShowBigImageActivity;
import cn.chenyuanming.gankmeizhi.activity.WebViewActivity;
import cn.chenyuanming.gankmeizhi.beans.FavoriteBean;
import cn.chenyuanming.gankmeizhi.beans.GoodsBean;
import cn.chenyuanming.gankmeizhi.beans.ReadArticles;
import cn.chenyuanming.gankmeizhi.utils.DbHelper;

/**
 * Created by Administrator on 2016/1/28.
 */
public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    private List<GoodsBean.Results> mDatas = new ArrayList<>();

    FavoriteBean favorite = DbHelper.getHelper().getData(FavoriteBean.class).get(0);
    ReadArticles readArticles = DbHelper.getHelper().getData(ReadArticles.class).get(0);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_meizhi)
        ImageView iv_meizhi;
        @Bind(R.id.tv_type)
        TextView tv_type;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_author)
        TextView tv_author;
        @Bind(R.id.iv_share)
        ImageView iv_share;
        @Bind(R.id.iv_favorite)
        ImageView ivFavorite;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public String getValueAt(int position) {
        return mDatas.get(position).desc;
    }

    Context context;
    int fragType;

    public ArticleViewAdapter(Context context, List<GoodsBean.Results> items, int fragType) {
        this.context = context;
        if (items != null) {
            mDatas.addAll(items);
        }
        this.fragType = fragType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GoodsBean.Results data = mDatas.get(position);

        holder.tv_title.setText(data.desc);
        holder.tv_author.setText("推荐 by @" + data.who);
        holder.tv_type.setText("#" + data.type + " ");
        if (readArticles.articles.contains(data.objectId)) {
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.lightBlack));
        } else {
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
        }
        if (data.type.equals("福利")) {
            holder.iv_meizhi.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.url).into(holder.iv_meizhi);
            holder.iv_meizhi.setOnClickListener((v) -> {
                Intent intent = new Intent(context, ShowBigImageActivity.class);
                intent.putExtra("data", mDatas.get(position));
                context.startActivity(intent);
            });
        } else {
            holder.iv_meizhi.setVisibility(View.GONE);
            holder.tv_title.setOnClickListener(v -> {
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.lightBlack));
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", mDatas.get(position).url);
                intent.putExtra("objectId", mDatas.get(position).objectId);
                context.startActivity(intent);
                readArticles.articles.add(data.objectId);
                DbHelper.getHelper().getLiteOrm().save(readArticles);
            });
        }

        setFavoriteIcon(holder.ivFavorite, favorite.favorites, data.objectId);
        holder.ivFavorite.setOnClickListener(v -> {
            onFavoriteClicked(holder.ivFavorite, favorite.favorites, data.objectId);
            DbHelper.getHelper().getLiteOrm().save(favorite);
        });
    }

    private void onFavoriteClicked(ImageView ivFavorite, TreeSet<String> favorites, String objectId) {
        if (favorites.contains(objectId)) {
            favorites.remove(objectId);
        } else {
            favorites.add(objectId);
        }
        setFavoriteIcon(ivFavorite, favorites, objectId);
    }

    private void setFavoriteIcon(ImageView ivFavorite, TreeSet<String> favorites, String objectId) {
        Drawable drawable = ivFavorite.getDrawable();
        if (favorites.contains(objectId)) {
            drawable.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN);
            ivFavorite.setImageDrawable(drawable);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<GoodsBean.Results> getDatas() {
        return mDatas;
    }

}
