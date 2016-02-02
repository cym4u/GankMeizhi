package cn.chenyuanming.gankmeizhi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;

import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.utils.PreferenceUtil;
import cn.chenyuanming.gankmeizhi.utils.ShareUtils;

/**
 * Created by Chen Yuanming on 2016/2/2.
 */
public class AboutActivity extends LibsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setIntent(new LibsBuilder()
                .withActivityTitle(getResources().getString(R.string.nav_about))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withAboutAppName(getResources().getString(R.string.app_name))
                .withAboutDescription("http://chenyuanming.cn @KeepCoding")
                .withAboutVersionShown(true)
                .withAboutIconShown(true)
                .withAboutVersionShownCode(true)
                .withAboutVersionShownName(true)
                .withLicenseShown(true)
                .intent(this));

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        for (int i = 0; i < menu.size(); i++) {
            item = menu.getItem(i);
            Drawable drawable = item.getIcon();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            if (item.getItemId() == R.id.action_like && PreferenceUtil.getInstance().getBooleanDefaultFalse("like")) {
                drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            }
            item.setIcon(drawable);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareUtils.share(this);
                break;
            case R.id.action_like:
                Drawable drawable = item.getIcon();
                boolean isLiked = PreferenceUtil.getInstance().getBooleanDefaultFalse("like");
                if (!isLiked) {
                    drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    go2StarPage();

                } else {
                    drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                }
                PreferenceUtil.getInstance().putBoolean("like", !isLiked);
                item.setIcon(drawable);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void go2StarPage() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", getString(R.string.project_url));
        intent.putExtra("objectId", "");
        intent.putExtra("useCache", false);
        startActivity(intent);
    }
}