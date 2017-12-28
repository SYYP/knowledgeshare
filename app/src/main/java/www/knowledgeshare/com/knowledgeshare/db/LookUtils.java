package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class LookUtils {
    public static List<LookBean> search() {
        List<LookBean> all = DataSupport.findAll(LookBean.class);//这样也行
        return all;
    }

    public static void deleteOne(String title) {
        DataSupport.deleteAll(LookBean.class, "title=?", title);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(LookBean.class);
    }

    public static void add(LookBean lookBean) {
        if (isInserted(lookBean.getTitle())) {
        } else {
            lookBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
        }
    }

    public static boolean isInserted(String title) {
        List<LookBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
