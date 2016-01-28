package cn.chenyuanming.gankmeizhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenyuanming.gankmeizhi.PrefetchService;
import cn.chenyuanming.gankmeizhi.R;
import cn.chenyuanming.gankmeizhi.fragment.GankFragment;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    public static final int DEFAULT_FRAG = 0;
    public static final String EXTRA_FRAG_TYPE = "extraFragType";

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(navigationView);

        setupViewPager(viewPager);
        viewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_FRAG_TYPE, DEFAULT_FRAG));
        tabLayout.setupWithViewPager(viewPager);
        startService(new Intent(this, PrefetchService.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(4);
        Adapter adapter = new Adapter(getSupportFragmentManager());

        addTab(adapter, GankFragment.FRAG_TYPE_ALL, getResources().getString(R.string.nav_all));
        addTab(adapter, GankFragment.FRAG_TYPE_MEIZHI, getResources().getString(R.string.nav_mm));
        addTab(adapter, GankFragment.FRAG_TYPE_ANDROID, getResources().getString(R.string.nav_android));
        addTab(adapter, GankFragment.FRAG_TYPE_IOS, getResources().getString(R.string.nav_ios));

        viewPager.setAdapter(adapter);
    }

    private void addTab(Adapter adapter, int fragType, String title) {
        adapter.addFragment(GankFragment.newInstance(fragType), title);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_all:
                                viewPager.setCurrentItem(0);
                                drawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_mm:
                                viewPager.setCurrentItem(1);
                                drawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_android:
                                viewPager.setCurrentItem(2);
                                drawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_ios:
                                viewPager.setCurrentItem(3);
                                drawerLayout.closeDrawers();
                                return true;
                            case R.id.nav_github:
                                drawerLayout.closeDrawers();
                                jump2Url("https://github.com/login");
                                return true;
                            case R.id.nav_trending:
                                drawerLayout.closeDrawers();
                                jump2Url("https://github.com/trending?l=java");
                                return true;

                            default:
                                return true;
                        }
                    }
                });
    }

    private void jump2Url(String value) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("url", value);
        startActivity(intent);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
