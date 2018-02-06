package www.knowledgeshare.com.knowledgeshare.utils;

public class MyContants {
    public static String FILENAME = "config.xml";
    public static String BASEURL = "http://39.107.91.92:82/";
    public static String LXKURL = "http://39.107.91.92:82/api/v2/";
    public static String IMGBASEURL = "";

    //短信发送
    public static final String verifycodes = BASEURL + "api/v2/verifycodes";
    //绑定微信获取验证码
    public static final String bindWxSms = BASEURL + "api/v2/verifycodes/wx-bind-code";
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
    //学习列表
    public static final String noteList = BASEURL + "api/v2/note";
    //历史今天收藏
    public static final String dayFavorite = BASEURL + "api/v2/note/day-favorite";
    //历史今天取消收藏
    public static final String dayNoFacorite = BASEURL + "api/v2/note/day-no-favorite";
    //编辑笔记
    public static final String editNote = BASEURL + "api/v2/note/edit";
    //删除笔记
    public static final String delNote = BASEURL + "api/v2/note/del";
    //个人信息
    public static final String userInfo = BASEURL + "api/v2/user/info";
    //更换头像
    public static final String uploadAvatar = BASEURL + "api/v2/user/upload-avatar";
    //个人信息更新
    public static final String editInfo = BASEURL + "api/v2/user/edit-info";
    //购买记录
    public static final String buyHistory = BASEURL + "api/v2/user/buy-history";
    //意见反馈
    public static final String feedBack = BASEURL + "api/v2/user/feedback";
    //帮助中心
    public static final String help = BASEURL + "api/v2/help";
    //注册协议
    public static final String registration = BASEURL + "api/v2/reg";
    //关于我们
    public static final String aboutUs = BASEURL + "api/v2/aboutus";
    //学习，右上角专栏课程通知
    public static final String notice = BASEURL + "api/v2/note/notices";
    //充值
    public static final String recharge = BASEURL + "api/v2/user/recharge";
    //我的收藏
    public static final String favorite = BASEURL + "api/v2/user/favorite";
    //我的金句
    public static final String gold = BASEURL + "api/v2/user/gold";
    //下载数据获取接口
    public static final String downLoad = BASEURL + "api/v2/down";
    //消息通知
    public static final String nitification = BASEURL + "api/v2/user/notifications";
    //学习笔记
    public static final String notes = BASEURL + "api/v2/user/notes";
    //签到
    public static final String signIn = BASEURL + "api/v2/user/sign-in";
    //是否绑定手机
    public static final String isBindMobile = BASEURL + "api/v2/is-bind-mobile";
    //绑定手机
    public static final String bindMobile = BASEURL + "api/v2/bind-mobile";
    //个人中心绑定微信（token）
    public static final String bindWx = BASEURL + "api/v2/user/bind-wx";
    //删除消息通知（token）
    public static final String delNotification = BASEURL + "api/v2/user/del-notification";
    //任务详情
    public static final String taskList = BASEURL + "api/v2/user/task-list";
    //我的账号 金额列表
    public static final String changeShow = BASEURL + "api/v2/user/recharge-show";
    //退出
    public static final String logout = BASEURL + "api/v2/user/logout";
}
