package www.knowledgeshare.com.knowledgeshare.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;

public class MyUtils {
    private static int mScreenWidth, mScreenHeight;

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    // base64图片转字符串
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getToken() {
        return getMD5(getCurrentDate() + "xfkj");
    }


    /*将日期转为时间戳*/
    public static long getStringToDate(String time) {
        //        String timestamp = String.format("%010d", stringToDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    //  时间戳转为日期  /年/月/日
    public static String getDateToString(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long lcc_time = Long.valueOf(time);
        String format = sdf.format(new Date(lcc_time * 1000L));
        return format;
    }

    public static String go(Long ttl) {
        Date nowTime = new Date(System.currentTimeMillis() + ttl);
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    //  时间戳转为日期  /年/月/日
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = sdf.format(curDate);
        return date;
    }

    //  时间戳转为日期  /年/月/日/时/分
    public static String getDateToStringTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        long lcc_time = Long.valueOf(time);
        String format = sdf.format(new Date(lcc_time * 1000L));
        return format;
    }

    //手机号判断逻辑
    public static boolean isMobileNO(String mobiles) {
        //        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        //        Matcher m = p.matcher(mobiles);
        //        return m.matches();
        //上面的验证会有些问题，手机号码格式不是固定的，所以就弄简单点
        if (mobiles.length() == 11) {
            return true;
        }
        return false;
    }

    //正则6-16位数字或字母
    public static boolean isPassMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    //写这个方法主要是想当发布release版本的时候不会输出日志
    public static void syso(String str) {
        System.out.print(str);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        return mScreenWidth;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        return mScreenHeight;
    }

    //启动应用的设置，跳转到应用的设置界面
    public static void startAppSettingsResult(Activity activity) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, 0);
    }

    //得到此设备的设备信息，这里用到的是方便进行友盟的集成测试
    public static String getDeviceInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = manager.getDeviceId();
        return deviceId;
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppPackageName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    /**
     * 获取程序的版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getAppPackageName(context), 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getNavigationBarHeight() {
        Resources resources = MyApplication.getGloableContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        //        Toast.makeText(MyApplication.getGloableContext(), " ::"+height, Toast.LENGTH_SHORT).show();
        return height;
    }

    //判断设备是否存在NavigationBar,false的话是那些导航栏不在显示屏上的手机
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }


    public static String dateToString(Date date, String type) {
        String str = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (type.equals("SHORT")) {
            // 07-1-18
            format = DateFormat.getDateInstance(DateFormat.SHORT);
            str = format.format(date);
        } else if (type.equals("MEDIUM")) {
            // 2007-1-18
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            str = format.format(date);
        } else if (type.equals("FULL")) {
            // 2007年1月18日 星期四
            format = DateFormat.getDateInstance(DateFormat.FULL);
            str = format.format(date);
        }
        return str;
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static boolean isHaveFile(String type, String fname) {
        List<DownloadTask> restoreList = OkDownload.restore(DownloadManager.getInstance().getFinished());
        List<DownLoadListsBean> list = new ArrayList<>();
        List<DownLoadListsBean> freeList = new ArrayList<>();
        List<DownLoadListsBean> commentList = new ArrayList<>();
        for (int i = 0; i < restoreList.size(); i++) {
            Progress progress = restoreList.get(i).progress;
            DownLoadListsBean downLoadListBean = (DownLoadListsBean) progress.extra3;
            if (downLoadListBean.getType().equals("xiaoke") || downLoadListBean.getType().equals("zhuanlan")) {
                list.add(downLoadListBean);
            }
            if (downLoadListBean.getType().equals("free")) {
                freeList.add(downLoadListBean);
            }
            if (downLoadListBean.getType().equals("comment")) {
                commentList.add(downLoadListBean);
            }
        }
        if (list.size() > 0) {
            for (int j = 0; j < list.size(); j++) {
                DownLoadListsBean.ListBean listBean = list.get(j).getList().get(0);
                String s = listBean.getName() + listBean.getTypeId() + "_" + listBean.getChildId() + ".mp3";
                if (s.equals(fname)) {
                    //                    TUtils.showShort(MyApplication.getGloableContext(),"此音频已下载");
                    return true;
                }
            }
        }
        if (freeList.size() > 0) {
            for (int j = 0; j < freeList.size(); j++) {
                DownLoadListsBean.ListBean listBean = freeList.get(j).getList().get(0);
                String s = listBean.getName() + listBean.getTypeId() + "_" + listBean.getChildId() + ".mp3";
                if (s.equals(fname)) {
                    //                    TUtils.showShort(MyApplication.getGloableContext(),"此音频已下载");
                    return true;
                }
            }
        }
        if (commentList.size() > 0) {
            for (int j = 0; j < commentList.size(); j++) {
                DownLoadListsBean.ListBean listBean = commentList.get(j).getList().get(0);
                String s = listBean.getName() + listBean.getTypeId() + "_" + listBean.getChildId() + ".mp3";
                if (s.equals(fname)) {
                    //                    TUtils.showShort(MyApplication.getGloableContext(),"此音频已下载");
                    return true;
                }
            }
        }

        /*fname = "/" + fname;
        String result = null;
        File f = null;
        try {
            switch (type) {
                case "free":
                    f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname);
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname);
                    break;
                case "comment":
                    f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname);
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname);
                    break;
                case "xiaoke":
                    f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname);
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname);
                    break;
                case "zhuanlan":
                    f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname);
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname);
                    break;
            }
            int length = (int) f.length();
            if (length > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return false;
    }
}
