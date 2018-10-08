package cn.chenyuanming.gankmeizhi.beans.db

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable
import java.util.*

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
@Table("ReadArticles")
class ReadArticles : Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    var id: Int = 0
    var articles = TreeSet<String>()
}
