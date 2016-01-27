package cn.chenyuanming.gankmeizhi;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    public static final int OS_FRAG = 0;
    public static final int DEVICE_FRAG = 1;
    public static final int FAV_FRAG = 2;
    public static final String EXTRA_FRAG_TYPE = "extraFragType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_FRAG_TYPE, OS_FRAG));
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
//                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                                startActivity(intent);
                                return true;

                            default:
                                return true;
                        }
                    }
                });
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
