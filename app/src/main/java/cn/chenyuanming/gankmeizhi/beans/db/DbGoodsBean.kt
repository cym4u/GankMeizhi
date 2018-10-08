package cn.chenyuanming.gankmeizhi.beans.db

import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable

/**
 * Created by Chen Yuanming on 2016/1/25.
 */
@Table("goods")
class DbGoodsBean : Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    var id: Int = 0
    var allResults: MutableList<CommonGoodsBean.Results> = mutableListOf()
    var meizhiResults: MutableList<CommonGoodsBean.Results> = mutableListOf()
    var androidResults: MutableList<CommonGoodsBean.Results> = mutableListOf()
    var iosResults: MutableList<CommonGoodsBean.Results> = mutableListOf()
}
