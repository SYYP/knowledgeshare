package www.knowledgeshare.com.knowledgeshare.db;

import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;

import static org.litepal.crud.DataSupport.where;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DownUtils {

    public static List<DownLoadListBean> search() {
        List<DownLoadListBean> all = DataSupport.findAll(DownLoadListBean.class);//这样也行
        return all;
    }

    public static List<DownLoadListBean> search(String fid){
         /*
         where()方法接收任意个字符串参数，其中第一个参数用于进行条件约束，
         从第二个参数开始，都是用于替换第一个参数中的占位符的。那这个where()方法就对应了一条SQL语句中的where部分。
         */
//                List<ShopCarBean> Users = where("id>?", "0").find(ShopCarBean.class);
        List<DownLoadListBean> flList = where("id=?",fid).find(DownLoadListBean.class);
        return flList;
    }

    public static void deleteOne(String name) {
        DataSupport.deleteAll(DownLoadListBean.class, "name=?", name);
    }

    public static void deleteAll() {
        DataSupport.deleteAll(DownLoadListBean.class);
    }

    public static void add(DownLoadListBean loadListBean) {
        if (isInserted(loadListBean.getName())) {
            Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
        } else {
            boolean save = loadListBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
            if (save) {
                Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance(), "加入下载列表失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isInserted(String name) {
        List<DownLoadListBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
