package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//xml布局控件可能要改
public class MySettingActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.nameAndbianhao)
    TextView nameAndbianhao;
    @BindView(R.id.phonePicture)
    ImageView phonePicture;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.Gender)
    EditText Gender;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.province)
    TextView province;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.phone2)
    EditText phone2;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.sureSubmission)
    Button sureSubmission;
    @BindView(R.id.ExitLogon)
    Button ExitLogon;
//////////////////////////////////////////////////////////////////////
private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.re_clickBack,R.id.Gender, R.id.threespot, R.id.phonePicture, R.id.camera, R.id.province, R.id.city, R.id.area, R.id.sureSubmission, R.id.ExitLogon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.phonePicture:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                intent1.setType("image/*");
                startActivityForResult(intent1, IMAGE_REQUEST_CODE);
                break;
            case R.id.camera:
                if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    ToastUtils.showToast(this,"请插入sd卡");
                }
                break;
            case R.id.province:
                break;
            case R.id.city:
                break;
            case R.id.area:
                break;
            case R.id.sureSubmission:
                break;
            case R.id.ExitLogon://退出登录
                GouhuiUser.getInstance().clearUserInfo();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.Gender://性别
                break;

        }
    }

    //////////////////////////////////////////////////////////////////
    //获取图片的URI
    private Uri  getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }
    //该方法是用于放回相应结果的
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if(isSdcardExisting()){
                        resizeImage(getImageUri());
                    }else{
                        Toast.makeText(this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 展示头像
     * @param data
     */
    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            head.setImageDrawable(drawable);
        }
    }
    /**
     * 对图片进行处理
     * @param uri
     */
    private void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}
