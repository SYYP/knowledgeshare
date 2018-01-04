package www.knowledgeshare.com.knowledgeshare.db;

import android.content.ContentValues;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.crud.DataSupport.where;

/**
 * Created by Administrator on 2017/12/28.
 */

public class StudyTimeUtils {
    public static List<StudyTimeBean> search() {
        List<StudyTimeBean> all = DataSupport.findAll(StudyTimeBean.class);//这样也行
        return all;
    }

    public static long getTotalTime() {
        List<StudyTimeBean> list = search();
        long totaltime = 0;
        for (int i = 0; i < list.size(); i++) {
            long time = list.get(i).getTime();
            totaltime += time;
        }
        return totaltime / 1000;//服务器要传秒
    }

    public static void deleteOne(String id) {
        DataSupport.deleteAll(StudyTimeBean.class, "id=?", id);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(StudyTimeBean.class);
    }

    public static void add(StudyTimeBean studyTimeBean) {
        boolean save = studyTimeBean.save();
        if (save) {//注意bean类的id必须是int类型的，否则会保存失败
        } else {
        }
    }

    public static void updateTime(String type, String id, long time) {
        ContentValues values = new ContentValues();
        values.put("time", time);
        DataSupport.updateAll(StudyTimeBean.class, values, "type=? and id=?", type, id);
    }

    public static long getOneTime(String type, String id) {
        List<StudyTimeBean> studyTimeBeen = where("type=? and id=?", type, id).find(StudyTimeBean.class);
        if (studyTimeBeen != null && studyTimeBeen.size() > 0) {
            long time = studyTimeBeen.get(0).getTime();
            return time;
        }
        return 0;
    }

    public static boolean isHave(String type, String id) {
        List<StudyTimeBean> studyTimeBeen = where("type=? and id=?", type, id).find(StudyTimeBean.class);
        if (studyTimeBeen != null && studyTimeBeen.size() > 0) {
            return true;
        }
        return false;
    }

    public static String getDate() {
        List<StudyTimeBean> studyTimeBeen = search();
        if (studyTimeBeen != null && studyTimeBeen.size() > 0) {
            String date = studyTimeBeen.get(studyTimeBeen.size() - 1).getDate();
            return date;
        }
        return "";
    }
}
