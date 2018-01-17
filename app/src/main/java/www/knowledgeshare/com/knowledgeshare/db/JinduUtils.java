package www.knowledgeshare.com.knowledgeshare.db;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class JinduUtils {
    public static List<BofangjinduBean> search() {
        List<BofangjinduBean> all = DataSupport.findAll(BofangjinduBean.class);//这样也行
        return all;
    }

    public static void deleteOne(String title) {
        DataSupport.deleteAll(LookBean.class, "title=?", title);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(BofangjinduBean.class);
    }

    public static void add(BofangjinduBean bofangjinduBean) {
        if (isInserted(bofangjinduBean.getMid())) {
            bofangjinduBean.save();
        } else {
            bofangjinduBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
        }
    }

    public static boolean isInserted(String mid) {
        List<BofangjinduBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getMid().equals(mid)) {
                return true;
            }
        }
        return false;
    }
}
