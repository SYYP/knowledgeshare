package www.knowledgeshare.com.knowledgeshare.db;

/**
 * Created by lxk on 2017/7/6.
 */

public class LitePalHelper {

  /*  public static List<ShopCarBean> search() {
         *//*
         * where()方法接收任意个字符串参数，其中第一个参数用于进行条件约束，
         从第二个参数开始，都是用于替换第一个参数中的占位符的。那这个where()方法就对应了一条SQL语句中的where部分。
         * *//*
        //        List<ShopCarBean> Users = where("id>?", "0").find(ShopCarBean.class);
        List<ShopCarBean> all = DataSupport.findAll(ShopCarBean.class);//这样也行
        return all;
    }

    public static void updateCount(String goodId, int count) {
        ContentValues values = new ContentValues();
        values.put("duration", count);
        //这段代码将id为1的User的name进行修改，注意update方法是DataSupport中的一个静态方法。
        //        DataSupport.updateCount(User.class, values, 1);//这个方法只能根据id来进行修改
        //        Toast.makeText(MyApplication.getGloableContext(), " "+count, Toast.LENGTH_SHORT).show();
        DataSupport.updateAll(ShopCarBean.class, values, "goodId=?", goodId);


        //        ContentValues values2 = new ContentValues();
        //        values.put("phoneNumber", "11111");
        //        DataSupport.updateAll(Phone.class, values2, "id=?", "1");
    }

    public static void updateCheck(String goodId, boolean isChecked) {
        ContentValues values = new ContentValues();
        values.put("isChecked", isChecked);
        DataSupport.updateAll(ShopCarBean.class, values, "goodId=?", goodId);
    }

    public static void deleteOne(String goodId) {
        DataSupport.deleteAll(ShopCarBean.class, "goodId=?", goodId);
        //        int deleteCount = DataSupport.delete(ShopCarBean.class, 1);//这个方法只能把根据id
        //        System.out.println(deleteCount);
        *//*
        * 删除id为1的User， 这里要多说几句了， 不就删除id是1的User么？
        肯定就是1条啊， 获取count是多余的吧？
        嘿嘿， 再次证明一下litepal的强大吧， 删除一条数据，
        litepal会把与该数据关联的其他表中的数据一并删除了，
        比如现在删除了id为1的User， 那Phone表中属于User的数据将全部被删除！！
        毕竟那些成为垃圾数据了。
        * *//*
        //        DataSupport.deleteAll(User.class, "id>?", "1");
        //        DataSupport.deleteAll(User.class);//在不指定约束条件的情况下，deleteAll()方法就会删除表中所有的数据了。
        //        search();
        //        DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");
        // 多条件时只能用单词and连接条件
    }

    public static void deleteAll() {
        DataSupport.deleteAll(ShopCarBean.class);
    }

    public static void add(ShopCarBean shopCarBean) {
        //如果之前创建过这条数据，删除之后又创建了一遍，那么这条数据的id就会增加1
        //set方法就相当于向数据库中添加数据了
        //        p.setName("二狗");//因为继承了DataSupport的类就是创建了一张表，表的名字就是类名，所以这样就是往数据表中添加了数据了
        //        p.setGender("男");
        //        p.setPhoneNumber("123456");
        //        p.setHeight(100);
        //        if (shopCarBean.getMaxCount() <= 0) {
        //            Toast.makeText(MyApplication.getInstance(), "商品库存不足，请挑选其他商品吧", Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        if (isInserted(shopCarBean.getGoodId())) {
            //数量加1
            updateCount(shopCarBean.getGoodId(), shopCarBean.getDuration() + 1);
            Toast.makeText(MyApplication.getInstance(), "已成功加入共享车", Toast.LENGTH_SHORT).show();
        } else {
            boolean save = shopCarBean.save();//添加完数据别忘了保存，这个save()方法是实体类继承的DataSupport类中的
            if (save) {
                Toast.makeText(MyApplication.getInstance(), "已成功加入共享车", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance(), "加入共享车失败", Toast.LENGTH_SHORT).show();
            }
        }
        //        List<User> list = new ArrayList<>();
        //        list.add(p);
        //        DataSupport.saveAll(list);//这个是保存所有数据
        //        save()是不会抛出异常的，且返回的是boolean,来判断数据是否保存成

    }

    public static boolean isInserted(String goodId) {
        List<ShopCarBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getGoodId().equals(goodId)) {
                return true;
            }
        }
        return false;
    }

    public static ShopCarBean getInsertedOneBean(String goodId) {
        List<ShopCarBean> all = search();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getGoodId().equals(goodId)) {
                return all.get(i);
            }
        }
        return null;
    }*/
}
