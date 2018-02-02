package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.UserInfoBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.BaseSelectPopupWindow;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TimeUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class PersonInfomationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;@BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.face_iv) CircleImageView faceIv;@BindView(R.id.person_face_rl) RelativeLayout personFaceRl;
    @BindView(R.id.zhye_tv) TextView nameTv;@BindView(R.id.person_name_rl) RelativeLayout personNameRl;
    @BindView(R.id.sex_tv) TextView sexTv;@BindView(R.id.person_sex_rl) RelativeLayout personSexRl;
    @BindView(R.id.date_tv) TextView dateTv;@BindView(R.id.person_date_rl) RelativeLayout personDateRl;
    @BindView(R.id.xueli_tv) TextView xueliTv;@BindView(R.id.person_xueli_rl) RelativeLayout personXueliRl;
    @BindView(R.id.hangye_tv) TextView hangyeTv;@BindView(R.id.person_hangye_rl) RelativeLayout personHangyeRl;
    private List<String> cameraList;private List<LocalMedia> selectList = new ArrayList<>();
    private String cutPath;private BaseSelectPopupWindow popWiw;// 昵称 编辑框
    private List<String> sexLiset;private TimePickerView pvCustomLunar;
    private OptionsPickerView pvCustomOptions;private List<String> xueliItem, hangyeItem;
    private int NAME = 0, SEX = 1, BIRTHDAY = 2, EDUCATION = 3, INDUSTRY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_infomation);
        ButterKnife.bind(this);

        sexLiset = new ArrayList<>();
        sexLiset.add("男");
        sexLiset.add("女");

        xueliItem = new ArrayList<>();
        xueliItem.add("小学");
        xueliItem.add("初中");
        xueliItem.add("高中（中专）");
        xueliItem.add("大专（高职）");
        xueliItem.add("本科");
        xueliItem.add("硕士研究生");
        xueliItem.add("博士研究生");

        hangyeItem = new ArrayList<>();
        hangyeItem.add("农、林、牧、渔、水利业");
        hangyeItem.add("工业");
        hangyeItem.add("地质普查和勘探业");
        hangyeItem.add("建筑业");
        hangyeItem.add("交通运输业、邮电通信业");
        hangyeItem.add("商业、公共饮食业、物资供应和仓储业");
        hangyeItem.add("房地产管理、公用事业、居民服务和咨询服务业");
        hangyeItem.add("卫生、体育和社会福利事业");
        hangyeItem.add("教育、文化艺术和广播电视业");
        hangyeItem.add("科学研究和综合技术服务业");
        hangyeItem.add("金融、保险业");
        hangyeItem.add("国家机关、党政机关和社会团体");
        hangyeItem.add("其他行业");
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("个人信息");
        personFaceRl.setOnClickListener(this);
        personNameRl.setOnClickListener(this);
        personSexRl.setOnClickListener(this);
        personDateRl.setOnClickListener(this);
        personXueliRl.setOnClickListener(this);
        personHangyeRl.setOnClickListener(this);

        initLunarPicker();//初始化时间选择器

        String url = getIntent().getStringExtra("url");
        if (url != null && !url.equals("")){
            Glide.with(this).load(url).into(faceIv);
        }
        String name = getIntent().getStringExtra("name");
        nameTv.setText(name);
        String sex = getIntent().getStringExtra("sex");
        if (TextUtils.equals("1",sex)){
            sexTv.setText("男");
        }else {
            sexTv.setText("女");
        }
        String brithday = getIntent().getStringExtra("brithday");
        dateTv.setText(brithday);
        String education = getIntent().getStringExtra("education");
        int educationInt = Integer.parseInt(education);
        getEducation(educationInt);
        String industry = getIntent().getStringExtra("industry");
        int industryInt = Integer.parseInt(industry);
        getIndustry(industryInt);
    }

    private void getIndustry(int industry) {
        switch (industry){
            case 1:
                hangyeTv.setText(hangyeItem.get(0));
                break;
            case 2:
                hangyeTv.setText(hangyeItem.get(1));
                break;
            case 3:
                hangyeTv.setText(hangyeItem.get(2));
                break;
            case 4:
                hangyeTv.setText(hangyeItem.get(3));
                break;
            case 5:
                hangyeTv.setText(hangyeItem.get(4));
                break;
            case 6:
                hangyeTv.setText(hangyeItem.get(5));
                break;
            case 7:
                hangyeTv.setText(hangyeItem.get(6));
                break;
            case 8:
                hangyeTv.setText(hangyeItem.get(7));
                break;
            case 9:
                hangyeTv.setText(hangyeItem.get(8));
                break;
            case 10:
                hangyeTv.setText(hangyeItem.get(9));
                break;
            case 11:
                hangyeTv.setText(hangyeItem.get(10));
                break;
            case 12:
                hangyeTv.setText(hangyeItem.get(11));
                break;
            case 13:
                hangyeTv.setText(hangyeItem.get(12));
                break;
        }
    }

    private void getEducation(int education) {
        switch (education){
            case 1:
                xueliTv.setText(xueliItem.get(0));
                break;
            case 2:
                xueliTv.setText(xueliItem.get(1));
                break;
            case 3:
                xueliTv.setText(xueliItem.get(2));
                break;
            case 4:
                xueliTv.setText(xueliItem.get(3));
                break;
            case 5:
                xueliTv.setText(xueliItem.get(4));
                break;
            case 6:
                xueliTv.setText(xueliItem.get(5));
                break;
            case 7:
                xueliTv.setText(xueliItem.get(6));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.person_face_rl://头像
                cameraList = new ArrayList<>();
                cameraList.add("从相册中选择");
                cameraList.add("拍照");
                showCamera();
                break;
            case R.id.person_name_rl://昵称
                showNickName();
                break;
            case R.id.person_sex_rl://性别
                showSex();
                break;
            case R.id.person_date_rl://出生年月
                pvCustomLunar.show();
                break;
            case R.id.person_xueli_rl://学历
                initCustomOptionPicker(xueliItem,0);
                pvCustomOptions.show();
                break;
            case R.id.person_hangye_rl://行业
                initCustomOptionPicker(hangyeItem,1);
                pvCustomOptions.show();
                break;
        }
    }

    private void showCamera() {
        new NormalSelectionDialog.Builder(this).setlTitleVisible(false)   //设置是否显示标题
                .setItemHeight(55)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.text_black)  //设置item字体颜色
                .setItemTextSize(16)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {

                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int
                            position) {
                        switch (position){
                            case 0://从相册选择
                                requestPhoto();
                                break;
                            case 1://拍照
                                requestCamera();
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build()
                .setDatas(cameraList)
                .show();
    }

    private void requestPhoto() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(200, 200)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(list)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void requestCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片
//                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .selectionMedia(list)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    cutPath = selectList.get(0).getCutPath();
//                    cutPath = selectList.get(0).getPath();
//                    Glide.with(this).load(cutPath).into(faceIv);
                    File file = new File(cutPath);
                    requestUploadAvatar(file);
                    break;
            }
        }
    }

    private void requestUploadAvatar(File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("upload_file",file);
        OkGo.<BaseBean>post(MyContants.uploadAvatar)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            Glide.with(PersonInfomationActivity.this).load(response.body().getUrl()).into(faceIv);
                            EventBean eventBean = new EventBean("userinfo");
                            EventBus.getDefault().postSticky(eventBean);
                        }
                    }
                });
    }

    private void showNickName() {
        if (popWiw == null) {
            popWiw = new BaseSelectPopupWindow(this, R.layout.edit_data);
            // popWiw.setOpenKeyboard(true);
            popWiw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

            popWiw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWiw.setShowTitle(false);
        }
        popWiw.setFocusable(true);
        InputMethodManager im = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        final ImageView send = (ImageView) popWiw.getContentView().findViewById(R.id.query_iv);
        final EditText edt = (EditText) popWiw.getContentView().findViewById(R.id.edt_content);
        final ImageView close = popWiw.getContentView().findViewById(R.id.cancle_iv);

        edt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
//        edt.setImeOptions(EditorInfo.IME_ACTION_SEND);
        edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (TextUtils.isEmpty(edt.getText())) {
                    send.setEnabled(false);
                } else {
                    send.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
                    // 昵称
                    String content = edt.getText().toString().trim();
                    nameTv.setText(content);
                    requestEditInfo(NAME,content);
                    popWiw.dismiss();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWiw.dismiss();
            }
        });

        popWiw.showAtLocation(personNameRl, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void requestEditInfo(int flag, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        Logger.e(param);
        switch (flag){
            case 0:
                params.put("user_name",param);
                break;
            case 1:
                params.put("user_sex",param);
                break;
            case 2:
                params.put("user_birthday",param);
                break;
            case 3:
                params.put("user_education",param);
                break;
            case 4:
                params.put("user_industry",param);
                break;
        }

        OkGo.<BaseBean>post(MyContants.editInfo)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(this,BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            TUtils.showShort(PersonInfomationActivity.this,response.body().getMessage());
                            EventBean eventBean = new EventBean("userinfo");
                            EventBus.getDefault().postSticky(eventBean);
                        }else {
                            TUtils.showShort(PersonInfomationActivity.this,response.body().getMessage());
                        }
                    }
                });
    }

    private void showSex() {
        new NormalSelectionDialog.Builder(this).setlTitleVisible(false)   //设置是否显示标题
                .setItemHeight(55)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.text_black)  //设置item字体颜色
                .setItemTextSize(16)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {

                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int
                            position) {
                        switch (position){
                            case 0://男
                                sexTv.setText("男");
                                requestEditInfo(SEX,"1");
                                break;
                            case 1://女
                                sexTv.setText("女");
                                requestEditInfo(SEX,"2");
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build()
                .setDatas(sexLiset)
                .show();
    }

    /**
     * 时间选择器
     */
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, 11, 31);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                /*Date nowDate = TimeUtils.getNowDate();
                if (date.getTime() > nowDate.getTime()) {
                    TUtils.showShort(getApplicationContext(), "只能选择当前日期之前的日期");
                    return;
                }*/
                dateTv.setText(getTime(date));
                requestEditInfo(BIRTHDAY,getTime(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_date_layout, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        tvCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .setContentSize(16)
                .setLineSpacingMultiplier(1.6f)
                .isCyclic(true)//是否循环滚动
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.textcolor))
                .setTextColorOut(getResources().getColor(R.color.textcolor))
                .setBgColor(getResources().getColor(R.color.white))
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(true)
                .setTextColorCenter(getResources().getColor(R.color.text_black)) //设置选中项文字颜色
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initCustomOptionPicker(final List<String> data, final int flag){
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                switch (flag){
                    case 0:
                        xueliTv.setText(tx);
                        requestEditInfo(EDUCATION,options1+1+"");
                        break;
                    case 1:
                        hangyeTv.setText(tx);
                        requestEditInfo(INDUSTRY,options1+1+"");
                        break;
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        tvCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .setSelectOptions(2)//默认选中项
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.huise2))
                .setTextColorOut(getResources().getColor(R.color.textcolor))
                .setDividerColor(getResources().getColor(R.color.textcolor))
                .setTextColorCenter(getResources().getColor(R.color.text_black)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(data);//添加数据

    }
}
