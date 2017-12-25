package www.knowledgeshare.com.knowledgeshare.fragment.mine;

/**
 * Created by Administrator on 2017/12/18.
 */

public interface ItemTouchHelperAdapter {
    //数据交换
    void onItemMove(int fromPosition, int toPosition);
    //数据删除
    void onItemDissmiss(int position);
}
