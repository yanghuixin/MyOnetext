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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.StoreCollectionDataRes;
import com.example.administrator.myonetext.bean.StoredetailsDataRes;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.fragment.AboutStoreFragment;
import com.example.administrator.myonetext.fragment.AllProductFragment;
import com.example.administrator.myonetext.fragment.StoreHomeFragment;
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

public class StoredetailsActivity extends AppCompatActivity {
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.storePicture)
    ImageView storePicture;
    @BindView(R.id.storeName)
    TextView storeName;
    @BindView(R.id.storeCollection)
    RadioButton storeCollection;
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.allProduct)
    RadioButton allProduct;
    @BindView(R.id.store)
    RadioButton store;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.storedetailsFragmentlayout)
    FrameLayout storedetailsFragmentlayout;
    @BindView(R.id.backgroundpicture)
    RelativeLayout backgroundpicture;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private StoreHomeFragment storehoneFragment;
    private int bid;
    private final static int COLLECTION_STOR=1;//收藏店铺
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COLLECTION_STOR:
                    storeCollection.setChecked(true);
                    break;
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
                                        backgroundpicture.setBackground(drawable);//设置背景
                                    }
                                }
                            });
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
    private AllProductFragment allProductFragment;
    private AboutStoreFragment aboutStoreFragment;
    private StoredetailsDataRes storedetailsDataRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedetails);
        ButterKnife.bind(this);
        // TODO: 2018/1/23 提供页面之后放出
        Intent intent = getIntent();
        String ibid = intent.getStringExtra("bid");
        Log.d("productDetailsDataRes1", "onViewClicked: -------------------->"+ibid);
        bid = Integer.parseInt(ibid);
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
        storehoneFragment = new StoreHomeFragment();
        transaction.replace(R.id.storedetailsFragmentlayout, storehoneFragment);
        transaction.commit();

    }

    private void initStoredetailsData(int bid) {
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

                    //// TODO: 2018/1/25 目前轮播图应该在另一个接口内 ，在fragment中加载 展示以下：
//                    String blbpic = storedetailsDataRes.getBlbpic();
//                    String[] split = blbpic.split(";");
//                    initMyBanner(split);


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


    @OnClick({R.id.re_clickBack, R.id.storeCollection, R.id.home, R.id.allProduct, R.id.store})
    public void onViewClicked(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.storeCollection:
                UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(getApplicationContext());
                String uid = userInfo.getUid();
                int pid = Integer.parseInt(uid);
                initStoreCollection(pid,bid);
                break;
            case R.id.home:
                hideFragment(transaction);
                storehoneFragment = new StoreHomeFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, storehoneFragment);
                transaction.commit();
                break;
            case R.id.allProduct:
                hideFragment(transaction);
                allProductFragment = new AllProductFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, allProductFragment);
                transaction.commit();
                break;
            case R.id.store:
                hideFragment(transaction);
                aboutStoreFragment = new AboutStoreFragment();
                transaction.replace(R.id.storedetailsFragmentlayout, aboutStoreFragment);
                transaction.commit();
                break;
        }
    }

    private void initStoreCollection(int pid, int storeid) {
        String nearbyMs = "http://app.tealg.com/api/app/Business.ashx?flag=CollectionShop&pid="+pid+"&storeid="+storeid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    StoreCollectionDataRes storeCollectionDataRes = gson.fromJson(string, StoreCollectionDataRes.class);
                    if ( storeCollectionDataRes.getStatus()==1){
                        Message message = new Message();
                        message.what = COLLECTION_STOR;
                        handler.sendMessage(message);
                    }
                }else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });



    }

    private void hideFragment(FragmentTransaction transaction) {
        if (storehoneFragment != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(storehoneFragment);
        }
        if (allProductFragment != null) {
            //transaction.hide(f2);
            transaction.remove(allProductFragment);
        }
        if (aboutStoreFragment != null) {
            //transaction.hide(f3);
            transaction.remove(aboutStoreFragment);
        }
    }
}
