package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class WenGaoFileActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_teacher_head)
    CircleImageView ivTeacherHead;
    @BindView(R.id.tv_ke_name)
    TextView tvKeName;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.nestView)
    NestedScrollView nestView;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_gao_file);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        String img = getIntent().getStringExtra("img");
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        String childId = getIntent().getStringExtra("childId");
        String tname = getIntent().getStringExtra("tname");
        type = getIntent().getStringExtra("type");
        tvKeName.setText(title);
        tvTeacherName.setText(tname);
        if (NetWorkUtils.isNetworkConnected(this)) {
            Glide.with(this).load(img).into(ivTeacherHead);
        }else {
            ivTeacherHead.setImageResource(R.drawable.home_default_xk);
        }
        String mohuchaxun = getIntent().getStringExtra("mohuchaxun");
        if (!TextUtils.isEmpty(mohuchaxun)) {
            String fromSDFile = loadFromSDFile2(mohuchaxun);
            tvContent.setText(fromSDFile);
            //            getMohuPath(mohuchaxun);
        } else {
            String fromSDFile = loadFromSDFile(id + "-" + childId + title + ".txt");
            tvContent.setText(fromSDFile);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private String loadFromSDFile(String fname) {
        fname = "/" + fname;
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
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(WenGaoFileActivity.this, "没有找到指定文件", Toast.LENGTH_SHORT).show();
        }
        return result;
    }


    private File sdDir = null;

    private File getMohuPath(String fname) {
        File file = null;
        String[] list = sdDir.list();
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains(fname) && list[i].contains(".txt")) {
                //                Toast.makeText(this,"包含AFile",Toast.LENGTH_SHORT).show();
                file = new File(sdDir, list[i]);
            }
        }
        return file;
    }

    private String loadFromSDFile2(String fname) {
        String result = null;
        File f = null;
        try {
            switch (type) {
                case "free":
                    sdDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download");
                    f = getMohuPath(fname);
                    break;
                case "everydaycomment":
                    sdDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download");
                    f = getMohuPath(fname);
                    ;
                    break;
                case "softmusicdetail":
                    sdDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download");
                    f = getMohuPath(fname);
                    break;
                case "zhuanlan":
                    sdDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download");
                    f = getMohuPath(fname);
                    break;
            }
            int length = (int) f.length();
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(WenGaoFileActivity.this, "没有找到指定文件", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
