package cn.chenyuanming.gankmeizhi.beans.db;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.chenyuanming.gankmeizhi.beans.CommonGoodsBean;

/**
 * Created by Chen Yuanming on 2016/1/25.
 */
@Table("goods")
public class DbGoodsBean implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    public int id;
    public List<CommonGoodsBean.Results> allResults = new ArrayList<>();
    public List<CommonGoodsBean.Results> meizhiResults = new ArrayList<>();
    public List<CommonGoodsBean.Results> androidResults = new ArrayList<>();
    public List<CommonGoodsBean.Results> iosResults = new ArrayList<>();

}
