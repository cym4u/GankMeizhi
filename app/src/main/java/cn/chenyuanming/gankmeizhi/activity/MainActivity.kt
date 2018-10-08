package cn.chenyuanming.gankmeizhi.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.fragment.GankFragment
import cn.chenyuanming.gankmeizhi.utils.ClipboardHelper
import cn.chenyuanming.gankmeizhi.utils.ToastUtil
import com.google.android.material.navigation.NavigationView
import com.umeng.update.UmengUpdateAgent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_viewpager.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UmengUpdateAgent.update(this)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDrawerContent(navigationView)

        setupViewPager(viewPager)
        viewPager.currentItem = intent.getIntExtra(EXTRA_FRAG_TYPE, DEFAULT_FRAG)
        tabLayout.setupWithViewPager(viewPager)
        //        startService(new Intent(this, PrefetchService.class));
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPager.offscreenPageLimit = 4
        val adapter = Adapter(supportFragmentManager)

        addTab(adapter, GankFragment.FRAG_TYPE_ALL, resources.getString(R.string.nav_all))
        addTab(adapter, GankFragment.FRAG_TYPE_ANDROID, resources.getString(R.string.nav_android))
        addTab(adapter, GankFragment.FRAG_TYPE_IOS, resources.getString(R.string.nav_ios))
        addTab(adapter, GankFragment.FRAG_TYPE_MEIZHI, resources.getString(R.string.nav_mm))


        viewPager.adapter = adapter
    }

    private fun addTab(adapter: Adapter, fragType: Int, title: String) {
        adapter.addFragment(GankFragment.newInstance(fragType), title)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.getHeaderView(0).findViewById<View>(R.id.nav_weibo).setOnClickListener { _ ->
            ClipboardHelper.copy(this@MainActivity, resources.getString(R.string.weibo))
            ToastUtil.showShortToast("微博地址[http://weibo.com/123466678]已经复制到剪贴板╮(╯_╰)╭")
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_avatar, R.id.nav_weibo -> {
                }
                R.id.nav_all -> {
                    viewPager.currentItem = 0
                    drawerLayout.closeDrawers()
                }
                R.id.nav_mm -> {
                    viewPager.currentItem = 1
                    drawerLayout.closeDrawers()
                }
                R.id.nav_android -> {
                    viewPager.currentItem = 2
                    drawerLayout.closeDrawers()
                }
                R.id.nav_ios -> {
                    viewPager.currentItem = 3
                    drawerLayout.closeDrawers()
                }
                R.id.nav_github -> {
                    drawerLayout.closeDrawers()
                    jump2Url("https://github.com/login")
                }
                R.id.nav_trending -> {
                    drawerLayout.closeDrawers()
                    jump2Url("https://github.com/trending?l=java")
                }
                R.id.nav_about -> {
                    drawerLayout.closeDrawers()
                    startActivity(Intent(this, AboutActivity::class.java))
                }

            }
            true
        }
    }

    private fun jump2Url(value: String) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("url", value)
        startActivity(intent)
    }

    internal class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val mFragments = ArrayList<Fragment>()
        private val mFragmentTitles = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }


        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }

    companion object {
        const val DEFAULT_FRAG = 0
        const val EXTRA_FRAG_TYPE = "extraFragType"
    }
}
