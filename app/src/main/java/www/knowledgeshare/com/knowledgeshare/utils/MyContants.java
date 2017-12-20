package www.knowledgeshare.com.knowledgeshare.utils;

public class MyContants {
    public static String FILENAME = "config.xml";
    public static String BASEURL = "http://thinks.iask.in/";
    public static String LXKURL = "http://thinks.iask.in/api/v2/";
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
    //找回密码发送短信
    public static final String getPwdCode = BASEURL + "api/v2/verifycodes/get-pwd-code";
    //密码找回第一步验证手机号
    public static final String getOnePwd = BASEURL + "api/v2/password/get-one-pwd";
    //重置密码第二步
    public static final String getTwoPwd = BASEURL + "api/v2/password/set-two-pwd";
    //购物车列表
    public static final String cartList = BASEURL + "api/v2/order/cart-list";
    //购物车结算
    public static final String submitOrder = BASEURL + "api/v2/order/submit-order";
    //删除购物车
    public static final String delCart = BASEURL + "api/v2/order/del-cart";
    //修改密码
    public static final String editPwd = BASEURL + "api/v2/user/edit-pwd";
    //我的订阅
    public static final String rss = BASEURL + "api/v2/user/rss";
    //已购 轻松音乐课
    public static final String buyXk = BASEURL + "api/v2/user/buy-xk";
    //已购 音乐大师班
    public static final String buyZl = BASEURL + "api/v2/user/buy-zl";
    //添加笔记
    public static final String addNote = BASEURL + "api/v2/note/add";

}
