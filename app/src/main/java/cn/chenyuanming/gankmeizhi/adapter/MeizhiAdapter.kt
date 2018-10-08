package cn.chenyuanming.gankmeizhi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.chenyuanming.gankmeizhi.R
import cn.chenyuanming.gankmeizhi.activity.ShowBigImageActivity
import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_meizhi.view.*
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
class MeizhiAdapter(list: List<CommonGoodsBean.Results>?) : RecyclerView.Adapter<MeizhiAdapter.MeizhiViewHolder>() {
    private val mDatas = ArrayList<CommonGoodsBean.Results>()

    val datas: MutableList<CommonGoodsBean.Results>
        get() = mDatas

    init {
        if (list != null) {
            mDatas.addAll(list)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MeizhiViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_meizhi, viewGroup, false)
        return MeizhiViewHolder(view)
    }

    override fun onBindViewHolder(meizhiViewHolder: MeizhiViewHolder, position: Int) {
        //        meizhiViewHolder.imageView.setImageResource(mDatas.get(position).getImg());
        val context = meizhiViewHolder.itemView.context
        Glide.with(context).load(mDatas[position].url).into(meizhiViewHolder.itemView.imageView)
        //        meizhiViewHolder.textView.setText(mDatas.get(position).desc);
        meizhiViewHolder.itemView.setOnClickListener { v ->
            val intent = Intent(context, ShowBigImageActivity::class.java)
            intent.putExtra("data", mDatas[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    class MeizhiViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
