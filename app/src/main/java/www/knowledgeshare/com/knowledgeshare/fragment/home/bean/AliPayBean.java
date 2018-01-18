package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

/**
 * Created by Administrator on 2018/1/18.
 */

public class AliPayBean {

    /**
     * alipay : app_id=2017122601239334&biz_content=%7B%22body%22%3A%22%E8%AE%A2%E9%98%85%E8%AF%BE%E7%A8%8B%22%2C%22subject%22%3A%22%E8%AE%A2%E9%98%85%22%2C%22out_trade_no%22%3A%22890569609809450001%22%2C%22total_amount%22%3A%22160.00%22%2C%22seller_id%22%3A%222088921394091295%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%221%22%2C%22passback_params%22%3A%22dingyue%22%2C%22timeout_express%22%3A%2210m%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F39.107.91.92%3A82%2Fapi%2Fv2%2Fali_notify&sign_type=RSA2&timestamp=2018-01-18+18%3A09%3A19&version=1.0&sign=hO0RkERc8p5Rjc4QyaPs3y5O2lV3ka0nUd712zrSQjiNwKFs%2BixvlTfDfDQ8J6LCXZybmF6Twdjy%2FLNux%2F6gngc9OFB2DoM3JcTNPnriEdW%2F4K%2FLr4jyL5iNTXEXvq%2BcjrU0x85UWosvZtg2gfg3W4OrXmE03GE%2F6GJ4lUtZ1X8EPHyCpF0d5a3TszBilZk%2Bi8T354c3h2ZveduwzhjV3F5gjyfpn6rGCy3mX1%2FfhbSjUzlaOBfTuRCwiKAe%2B0ieWb3H2klmLG6WNX0T47oq16qRgvWHtHMO222wGHOR7sAk1kUlSN6i4FkHNylAsqY1ZSybLsmIpVv1E8alN0Zq0w%3D%3D
     */
    private String alipay;

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getAlipay() {
        return alipay;
    }
}
