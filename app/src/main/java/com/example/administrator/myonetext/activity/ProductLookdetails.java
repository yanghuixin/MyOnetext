package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.ProductLookdetailsDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductLookdetails extends AppCompatActivity {
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.score)
    LinearLayout score;
    @BindView(R.id.location)
    ImageView location;
    @BindView(R.id.adress)
    TextView adress;
    @BindView(R.id.phone)
    ImageView phone;
    @BindView(R.id.Details_introduction)
    RadioButton DetailsIntroduction;
    @BindView(R.id.Specification_parameter)
    RadioButton SpecificationParameter;
    @BindView(R.id.tabs)
    RadioGroup tabs;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.webView)
    WebView webView;
    private int proid;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    webView.loadData(productLookdetailsDataRes.getStrXQJS(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
                    Glide.with(getApplicationContext()).load(productLookdetailsDataRes.getPicUrl()).into(picture);
                    name.setText(productLookdetailsDataRes.getPname());
                    adress.setText(productLookdetailsDataRes.getAddress());
                    num.setText(productLookdetailsDataRes.getProfs() + "分  已售" + productLookdetailsDataRes.getIbuys() + "份");
                    for (int i = 0; i < productLookdetailsDataRes.getProfs(); i++) {
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(45, 45));
                        imageView.setPadding(5, 0, 5, 0);
                        imageView.setImageResource(R.mipmap.xq_ax2x);
                        score.addView(imageView);
                    }
                    break;
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
            }
        }
    };
    private ProductLookdetailsDataRes productLookdetailsDataRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_lookdetails);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int proid = intent.getIntExtra("proid1", 0);
        tabs.check(R.id.Details_introduction);
        init();
        initproductData(proid);
    }

    private void init() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//给权限
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //优先使用缓存:
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void initproductData(int proid) {
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=getproxqmsg&proid=" + proid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(nearbyMs);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        final Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = handler.obtainMessage();
                msg.what = SERVER_EXCEPTION;
                msg.obj = "服务器" + e.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    productLookdetailsDataRes = gson.fromJson(string, ProductLookdetailsDataRes.class);
                    int state = productLookdetailsDataRes.getState();
                    if (state == 1) {
                        Message message = new Message();
                        message.what = INIT_STOR_DATE;
                        handler.sendMessage(message);
                    }


                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });


    }

    @OnClick({R.id.re_clickBack, R.id.location, R.id.phone, R.id.Details_introduction, R.id.Specification_parameter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.location://地图
                break;
            case R.id.phone:
                break;
            case R.id.Details_introduction:
                webView.loadData(productLookdetailsDataRes.getStrXQJS(), "text/html; charset=UTF-8", null);
                break;
            case R.id.Specification_parameter:
                webView.loadData(productLookdetailsDataRes.getStrGGCS(), "text/html; charset=UTF-8", null);
                break;
        }
    }
}
