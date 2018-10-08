package cn.chenyuanming.gankmeizhi.fragment

import androidx.fragment.app.Fragment

import com.umeng.analytics.MobclickAgent

/**
 * Created by Administrator on 2016/2/2.
 */
open class BaseFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart(javaClass.simpleName) //统计页面，"MainScreen"为页面名称，可自定义
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(javaClass.simpleName)
    }

}
