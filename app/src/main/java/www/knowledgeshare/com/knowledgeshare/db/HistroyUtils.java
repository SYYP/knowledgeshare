package www.knowledgeshare.com.knowledgeshare.db;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class HistroyUtils {

    public static List<BofangHistroyBean> search() {
        List<BofangHistroyBean> all = DataSupport.order("time desc").find(BofangHistroyBean.class);//这样也行
        return all;
    }

    public static int getOneDuration(String type, String video_name) {
        List<BofangHistroyBean> search = search();
        if (search != null && search.size() > 0) {
            for (int i = 0; i < search.size(); i++) {
                BofangHistroyBean bofangHistroyBean = search.get(i);
                if (bofangHistroyBean.getType().equals(type) && bofangHistroyBean.getVideo_name().equals(video_name)) {
                    return bofangHistroyBean.getDuration();
                }
            }
        }
        return 0;
    }

    public static void setOneDuration(BofangHistroyBean bofangHistroyBean) {
        if (isInserted2(bofangHistroyBean.getType(), bofangHistroyBean.getVideo_name())) {
            updateDuration(bofangHistroyBean.getType(),bofangHistroyBean.getVideo_name(),bofangHistroyBean.getDuration());
        } else {
            bofangHistroyBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
        }
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

    public static void updateTime(long time, String video_name) {
        ContentValues values = new ContentValues();
        values.put("time", time);
        DataSupport.updateAll(BofangHistroyBean.class, values, "video_name=?", video_name);
    }

    public static void updateDuration(String type, String video_name, long duration) {
        ContentValues values = new ContentValues();
        values.put("duration", duration);
        DataSupport.updateAll(BofangHistroyBean.class, values, "type=? and video_name=?", type, video_name);
    }

    public static boolean isInserted(String video_name) {
        List<BofangHistroyBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getVideo_name() != null && all.get(i).getVideo_name().equals(video_name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInserted2(String type, String video_name) {
        List<BofangHistroyBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            BofangHistroyBean bofangHistroyBean = all.get(i);
            if (bofangHistroyBean.getType().equals(type) &&
                    bofangHistroyBean.getVideo_name().equals(video_name)) {
                return true;
            }
        }
        return false;
    }
}
