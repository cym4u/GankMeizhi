package cn.chenyuanming.gankmeizhi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.activity.ShowBigImageActivity;
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean;

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
public class MeizhiAdapter extends RecyclerView.Adapter<MeizhiAdapter.MeizhiViewHolder> {
    private List<CommonGoodsBean.Results> mDatas = new ArrayList<>();
    private Context context;

    public MeizhiAdapter(Context context, List<CommonGoodsBean.Results> list) {
        this.context = context;
        if (list != null) {
            mDatas.addAll(list);
        }
    }

    @Override
    public MeizhiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_meizhi, viewGroup, false);
        return new MeizhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeizhiViewHolder meizhiViewHolder, int position) {
//        meizhiViewHolder.imageView.setImageResource(mDatas.get(position).getImg());
        Glide.with(context).load(mDatas.get(position).url).diskCacheStrategy(DiskCacheStrategy.ALL).into(meizhiViewHolder.imageView);
//        meizhiViewHolder.textView.setText(mDatas.get(position).desc);
        meizhiViewHolder.mainView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, ShowBigImageActivity.class);
            intent.putExtra("data", mDatas.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MeizhiViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;
//        @Bind(R.id.textView)
//        TextView textView;
        View mainView;

        public MeizhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mainView = itemView;
        }

    }

    public List<CommonGoodsBean.Results> getDatas() {
        return mDatas;
    }
}
