package com.example.administrator.myonetext.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyQRCodeActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.myQRCodeHome)
    TextView myQRCodeHome;
    @BindView(R.id.myQRCodeSearch)
    TextView myQRCodeSearch;
    @BindView(R.id.myQRCodeCar)
    TextView myQRCodeCar;
    @BindView(R.id.myQRCodePay)
    TextView myQRCodePay;
    @BindView(R.id.myQRCode)
    ImageView myQRCode;
private String url="http://img1.tealg.com/Member/Logo/201801/2ae0835e-3c5d-46c9-b304-94c8f693d554.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_qrcode);
        ButterKnife.bind(this);
        if (GouhuiUser.getInstance().hasUserInfo(this)){
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(this);
            String dlno = userInfo.getDlno();
            String upicurl = userInfo.getUpicurl();// TODO: 2018/2/8  还不知道怎么用
            initMyQRCode(dlno);
        }else {
            ToastUtils.showToast(this,"您还未登录");
        }
    }

    private void initMyQRCode(String dlno) {
        //generateBitmap("http://www.csdn.net",400, 400);第一个参数：生成二维码的内容，第二个，三个：生成二维码的宽高
        Bitmap qrBitmap = generateBitmap(dlno, 400, 400);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaaaaaaaaaaaaa);//生成logo图片，R.mipmap.ic_launcher为给定的图片
        // addLogo(Bitmap qrBitmap, Bitmap logoBitmap)第一个参数：生成的不带图标的二维码，第二个：logo图片
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        myQRCode.setImageBitmap(bitmap);
    }

    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.myQRCodeHome, R.id.myQRCodeSearch, R.id.myQRCodeCar, R.id.myQRCodePay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.myQRCodeHome:
                break;
            case R.id.myQRCodeSearch:
                break;
            case R.id.myQRCodeCar:
                break;
            case R.id.myQRCodePay:
                break;
        }
    }
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }
    //////////////////////////////////////////////
    /*
    * 网络上的图片变成Bitmap形式
    * */
//    public static Bitmap getbitmap(String imageUri) {
//        // 显示网络上的图片
//        Bitmap bitmap = null;
//        try {
//            URL myFileUrl = new URL(imageUri);
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl
//                    .openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//            bitmap = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            bitmap = null;
//        }
//        return bitmap;
//    }
}
