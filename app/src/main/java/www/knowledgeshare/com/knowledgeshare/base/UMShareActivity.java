package www.knowledgeshare.com.knowledgeshare.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import www.knowledgeshare.com.knowledgeshare.MyApplication;

/**
 * Created by lxk on 2017/7/3.
 */

public class UMShareActivity extends BaseActivity {
    public static ShareBoardConfig setShareBoardConfig() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        config.setIndicatorVisibility(false);
        //因为在这个项目中只有3个分享平台，但还是显示指示器原点，所以我这里设置指示器隐藏
        return config;
    }

    public static void shareText(Activity context) {
        ShareAction shareAction = new ShareAction(context).withText("hello")
                //setDisplayList中设置的枚举参数就是最终分享面板中显示的平台，所传入参数的顺序即为最终面板分享平台的排列顺序
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener);
        //        shareAction.open();//这种是默认的面板样式
        shareAction.open(setShareBoardConfig());//设置分享面板样式
    }

    public static void shareImage(Activity context, String imgUrl) {
        UMImage image = new UMImage(context, imgUrl);//网络图片
        //        UMImage image = new UMImage(UMShareActivity.this, file);//本地文件
        //        UMImage image = new UMImage(UMShareActivity.this, R.drawable.xxx);//资源文件
        //        UMImage image = new UMImage(UMShareActivity.this, bitmap);//bitmap文件
        //        UMImage image = new UMImage(UMShareActivity.this, byte[]);//字节流
        // 对于微信QQ的那个平台，分享的图片需要设置缩略图，缩略图的设置规则为：
        UMImage thumb = new UMImage(context, imgUrl);
        image.setThumb(thumb);
        // 用户可以设置压缩的方式：
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        //        压缩格式设置：
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        ShareAction shareAction = new ShareAction(context).withText("hello")
                .withMedia(image);
        shareAction.open(setShareBoardConfig());//设置分享面板样式
        shareAction.share();
    }

    public static void shareWebUrl(String url, String title, String imgUrl, String des, Activity context) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(context, imgUrl);
        web.setThumb(thumb);  //缩略图
        web.setDescription(des);//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(context).withMedia(web);
        shareAction.open(setShareBoardConfig());//设置分享面板样式
        shareAction.share();
        shareAction.setCallback(umShareListener);
    }

    public static void shareWebUrl(String url, String title, String imgUrl, String des, Activity context, SHARE_MEDIA pingtai) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(context, imgUrl);
        web.setThumb(thumb);  //缩略图
        web.setDescription(des);//描述
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        ShareAction shareAction = new ShareAction(context).withMedia(web);
        shareAction.setPlatform(pingtai);//传入平台
        shareAction.share();
        shareAction.setCallback(umShareListener);
    }

    public static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            //            Log.d("plat", "platform" + platform);

            Toast.makeText(MyApplication.getGloableContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyApplication.getGloableContext(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                //                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MyApplication.getGloableContext(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /*
    * 在分享所在的Activity里复写onActivityResult方法,注意不可在fragment中实现，如果在fragment中调用分享，
    * 在fragment依赖的Activity中实现，如果不实现onActivityResult方法，会导致分享或回调无法正常进行
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
