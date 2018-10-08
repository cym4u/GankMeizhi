package cn.chenyuanming.gankmeizhi.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
object RecyclerViewLoadHelper {


    fun isToBottom(recyclerView: RecyclerView): Boolean {
        var lastVisibleItemPosition = -1
        val lastPositions: IntArray
        val layoutManager = recyclerView.layoutManager
        if (null != layoutManager) {
            val layoutManagerType: LAYOUT_MANAGER_TYPE
            if (layoutManager is LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR
            } else if (layoutManager is GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID
            } else if (layoutManager is StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID
            } else {
                throw ClassCastException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }

            when (layoutManagerType) {
                RecyclerViewLoadHelper.LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                RecyclerViewLoadHelper.LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                RecyclerViewLoadHelper.LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                    val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                    lastVisibleItemPosition = findMax(lastPositions)
                }
            }

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            if (visibleItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 1) {
                //到达最后
                return true
            }

        }
        return false
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
