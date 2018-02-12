package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.MessageEvent;
import com.example.administrator.myonetext.bean.StoredetailsDataRes;
import com.example.administrator.myonetext.fragment.EnterpriseCommodityFragment;
import com.example.administrator.myonetext.fragment.EnterpriseDynamicsFragment;
import com.example.administrator.myonetext.fragment.EnterpriseIntroductionFragment;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewStoredetailsActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.storePicture)
    ImageView storePicture;
    @BindView(R.id.storeName)
    TextView storeName;
    @BindView(R.id.GHSelf_support)
    TextView GHSelfSupport;
    @BindView(R.id.storeCollection)
    TextView storeCollection;
    @BindView(R.id.store_background)
    LinearLayout storeBackground;
    @BindView(R.id.Enterprise_commodity)
    RadioButton EnterpriseCommodity;
    @BindView(R.id.Enterprise_introduction)
    RadioButton EnterpriseIntroduction;
    @BindView(R.id.Enterprise_dynamics)
    RadioButton EnterpriseDynamics;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.storedetailsFragmentlayout)
    FrameLayout storedetailsFragmentlayout;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private StoredetailsDataRes storedetailsDataRes;
    private int bid;

    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:

                    Glide.with(getApplicationContext()).load(storedetailsDataRes.getBlogo()).into(storePicture);
                    storeName.setText(storedetailsDataRes.getBname());
                    Glide.with(getApplicationContext())
                            .load(storedetailsDataRes.getBlbpic())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(180, 180) { //括号里的是图片宽高
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        storeBackground.setBackground(drawable);//设置背景
                                    }
                                }
                            });
                    storeCollection.setText(storedetailsDataRes.getBscnt() + "");
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
    private EnterpriseCommodityFragment enterpriseCommodityFragment;
    private EnterpriseDynamicsFragment enterpriseDynamicsFragment;
    private EnterpriseIntroductionFragment enterpriseIntroductionFragment;
    private String blbpic;
    private String strGydp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_storedetails);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String ibid = intent.getStringExtra("bid");
        Log.d("productDetailsDataRes1", "onViewClicked: -------------------->" + ibid);
       // bid = Integer.parseInt(ibid);
        bid=416;
        initStoredetailsData(this.bid);
        /**
         * 拿到事务管理器并开启事务
         */
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        /**
         * 启动默认选中第一个
         */
        radioGroup.check(R.id.home);
        enterpriseCommodityFragment = new EnterpriseCommodityFragment();
        transaction.replace(R.id.storedetailsFragmentlayout, enterpriseCommodityFragment);
        transaction.commit();


    }

    private void initStoredetailsData(final int bid) {
        Log.d("ssss", "onCreateView:bid-------------------------->"+bid);
        String nearbyMs = "http://app.tealg.com/api/app/Business.ashx?flag=showshopmsg&bid=" + bid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    Log.d("string", "商家onResponse:--------------------------> " + string);
                    storedetailsDataRes = gson.fromJson(string, StoredetailsDataRes.class);
                    strGydp = storedetailsDataRes.getStrGydp();
                    ////////////////////////////////////////////////////////////////////////////////
                    EventBus.getDefault().post(new MessageEvent(strGydp));
                    Log.d("ssss", "onCreateView:-------------------------->"+strGydp);//有值
                 //   EventBus.getDefault().post(strGydp);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////不对
//                    EnterpriseIntroductionFragment fragment1 = new EnterpriseIntroductionFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("strGydp", strGydp);
//                    Log.d("ssss", "onCreateView:-------------------------->"+strGydp);//有值
//                    fragment1.setArguments(bundle);
///////////////////////////////////////////////////////////////////////////////////////////
                    Message message = new Message();
                    message.what = INIT_STOR_DATE;
                    handler.sendMessage(message);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @OnClick({R.id.re_clickBack, R.id.iv_search, R.id.threespot, R.id.Enterprise_commodity, R.id.Enterprise_introduction, R.id.Enterprise_dynamics})
    public void onViewClicked(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.iv_search:
                break;
            case R.id.threespot:
                break;
            case R.id.Enterprise_commodity://企业商品
                hideFragment(transaction);
                enterpriseCommodityFragment = new EnterpriseCommodityFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, enterpriseCommodityFragment);
                transaction.commit();
                break;
            case R.id.Enterprise_introduction://企业简介
//                initWebSetting();
//                webview.loadUrl("www.baidu.com");
//                webview.loadData(strGydp, "text/html; charset=UTF-8", null);
//                Log.d("webview", "onViewClicked:----------------------> "+strGydp);
                hideFragment(transaction);
                enterpriseIntroductionFragment = new EnterpriseIntroductionFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, enterpriseIntroductionFragment);
                transaction.commit();
                break;
            case R.id.Enterprise_dynamics://企业动态
                hideFragment(transaction);
                enterpriseDynamicsFragment = new EnterpriseDynamicsFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, enterpriseDynamicsFragment);
                transaction.commit();
                break;
        }
    }

//    private void initWebSetting() {
////声明WebSettings子类
//        WebSettings webSettings = webview.getSettings();
////如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);//给权限
//// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
//// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
//
////设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
////缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
////其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        //优先使用缓存:
//        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//    }


    private void hideFragment(FragmentTransaction transaction) {
        if (enterpriseCommodityFragment != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(enterpriseCommodityFragment);
        }
        if (enterpriseIntroductionFragment != null) {
            //transaction.hide(f2);
            transaction.remove(enterpriseIntroductionFragment);
        }
        if (enterpriseDynamicsFragment != null) {
            //transaction.hide(f3);
            transaction.remove(enterpriseDynamicsFragment);
        }
    }
}
