package cn.chenyuanming.gankmeizhi.beans.db;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.util.TreeSet;

/**
 * Created by Administrator on 2016/1/30.
 */
public class FavoriteBean {

    @PrimaryKey(AssignType.BY_MYSELF)
    public int id;
    public TreeSet<String> favorites = new TreeSet<>();
}
