package cn.chenyuanming.gankmeizhi.beans.db;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * Created by Chen Yuanming on 2016/1/28.
 */
@Table("ReadArticles")
public class ReadArticles implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    public int id;
    public TreeSet<String> articles = new TreeSet<>();
}
