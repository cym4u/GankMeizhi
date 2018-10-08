package cn.chenyuanming.gankmeizhi.beans.db

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.enums.AssignType
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/30.
 */
class FavoriteBean {

    @PrimaryKey(AssignType.BY_MYSELF)
    var id: Int = 0
    var favorites = TreeSet<String>()
}
