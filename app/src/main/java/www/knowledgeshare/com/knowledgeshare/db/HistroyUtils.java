package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class HistroyUtils {

    public static List<BofangHistroyBean> search() {
        List<BofangHistroyBean> all = DataSupport.findAll(BofangHistroyBean.class);//这样也行
        return all;
    }

    public static void deleteOne(String video_name) {
        DataSupport.deleteAll(BofangHistroyBean.class, "video_name=?", video_name);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(BofangHistroyBean.class);
    }

    public static void add(BofangHistroyBean bofangHistroyBean) {
        if (isInserted(bofangHistroyBean.getVideo_name())) {
        } else {
            bofangHistroyBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
        }
    }

    public static boolean isInserted(String video_name) {
        List<BofangHistroyBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getVideo_name().equals(video_name)) {
                return true;
            }
        }
        return false;
    }
}
