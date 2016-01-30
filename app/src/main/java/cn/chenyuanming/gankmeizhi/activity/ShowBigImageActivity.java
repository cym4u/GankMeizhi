package cn.chenyuanming.gankmeizhi.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.beans.FavoriteBean;
import cn.chenyuanming.gankmeizhi.beans.GoodsBean;
import cn.chenyuanming.gankmeizhi.utils.DbHelper;
import cn.chenyuanming.gankmeizhi.utils.RxMeizhi;
import cn.chenyuanming.gankmeizhi.utils.ShareUtils;
import cn.chenyuanming.gankmeizhi.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by livin on 2016/1/28.
 */
public class ShowBigImageActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_meizhi)
    ImageView ivMeizhi;
    @Bind(R.id.iv_save)
    ImageView ivSave;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.iv_favorite)
    ImageView ivFavorite;
    GoodsBean.Results data;

    FavoriteBean favorite = DbHelper.getHelper().getData(FavoriteBean.class).get(0);

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Object object = getIntent().getSerializableExtra("data");
        if (object instanceof GoodsBean.Results) {
            data = (GoodsBean.Results) object;
            Glide.with(this).load(data.url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivMeizhi);
            changeFavoriteIcon(ivFavorite, favorite.favorites, data.objectId);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_save, R.id.iv_share, R.id.iv_favorite})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_save:
                ToastUtil.showShortToast("保存中...");
                RxMeizhi.saveImageAndGetPathObservable(this, data.url, data.desc).observeOn(AndroidSchedulers.mainThread()).subscribe(uri -> {
                    ToastUtil.showShortToast("保存成功");
                });
                break;
            case R.id.iv_share:
                RxMeizhi.saveImageAndGetPathObservable(this, data.url, data.desc).subscribe(uri -> {
                    ShareUtils.shareImage(ShowBigImageActivity.this, uri, data.desc);
                });
                break;
            case R.id.iv_favorite:
                onFavoriteClicked(ivFavorite, favorite.favorites, data.objectId);
                DbHelper.getHelper().getLiteOrm().save(favorite);
                break;
        }
    }

    private void onFavoriteClicked(ImageView ivFavorite, TreeSet<String> favorites, String objectId) {
        if (favorites.contains(objectId)) {
            favorites.remove(objectId);
        } else {
            favorites.add(objectId);
        }
        changeFavoriteIcon(ivFavorite, favorites, objectId);
    }

    private void changeFavoriteIcon(ImageView ivFavorite, TreeSet<String> favorites, String objectId) {
        Drawable drawable = ivFavorite.getDrawable();
        if (favorites.contains(objectId)) {
            drawable.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN);
            ivFavorite.setImageDrawable(drawable);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

}
