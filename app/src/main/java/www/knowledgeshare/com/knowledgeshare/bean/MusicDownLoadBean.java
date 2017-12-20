package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MusicDownLoadBean implements Serializable {
    private static final long serialVersionUID = 2072893447591548402L;

    public String name;
    public String url;
    public String iconUrl;
    public int priority;

    public MusicDownLoadBean() {
        Random random = new Random();
        priority = random.nextInt(100);
    }
}
