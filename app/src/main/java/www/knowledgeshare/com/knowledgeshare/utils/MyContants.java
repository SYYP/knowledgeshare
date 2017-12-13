package www.knowledgeshare.com.knowledgeshare.utils;

public class MyContants {
    public static String FILENAME = "config.xml";
    public static String BASEURL = "http://thinks.iask.in/";
    public static String IMGBASEURL = "";

    //短信发送
    public static final String verifycodes = BASEURL + "api/v2/verifycodes";
    //注册第一步
    public static final String registSetOne = BASEURL + "api/v2/register/set-one";
    //注册第二步
    public static final String registSetTwo = BASEURL + "api/v2/register/set-two";
    //获取标签
    public static final String tag = BASEURL + "api/v2/tag";
    //登录授权
    public static final String login = BASEURL + "api/v2/tokens";
    //启动信息
    public static final String bootstrappers = BASEURL + "api/v2/bootstrappers";
    //
}
