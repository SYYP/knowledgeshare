package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class StudyTimeUtils {
    public static List<StudyTimeBean> search() {
        List<StudyTimeBean> all = DataSupport.findAll(StudyTimeBean.class);//这样也行
        return all;
    }

    public static void deleteOne(String id) {
        DataSupport.deleteAll(StudyTimeBean.class, "id=?", id);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(StudyTimeBean.class);
    }

    public static void add(StudyTimeBean studyTimeBean) {
        studyTimeBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
    }

}
