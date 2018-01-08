package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.OneAdapter;
import com.example.administrator.myonetext.bean.OneDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NearbyMoreActivity extends AppCompatActivity {
private String type;
private  String path1;
    @BindView(R.id.morelist)
    ListView morelist;
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Unbinder bind;
    private final static int STOR_DATA = 3;
    private final static int SERVER_EXCEPTION = 6;//服务器异常
    private final static int NETWORK_ANOMALY = 7;//网络异常
    private OneAdapter adapter1;//商铺
    private List<OneDataRes.MsgBean> storData = new ArrayList<>();////商铺
    private OneDataRes storDataRes;//商铺
    private int page = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case STOR_DATA:
                    adapter1.notifyDataSetChanged();
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);

                    break;
            }
        }
    };
    private String nearbyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_more);
        bind = ButterKnife.bind(this);
        Intent intent1 = getIntent();
//        Intent intent2 = getIntent();
//        Intent intent3 = getIntent();
//        Intent intent4 = getIntent();
        nearbyFragment = intent1.getStringExtra("NearbyFragment");
        Log.d("getIntent", "onCreate:--------------> "+nearbyFragment);
        initType();

        initData(page,type);
        adapter1 = new OneAdapter(this, storData);
        morelist.setAdapter(adapter1);
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getApplicationContext()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getApplicationContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData(1,type);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initData(page,type);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initType() {
        switch (nearbyFragment){
            case "1":
                type="ms";
                break;
            case "2":
                type="cjy";
                break;
            case "3":
                type="mmm";
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.re_clickBack, R.id.threespot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
        }
    }

    private void initData(int page,String type) {
        path1 = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+page+"&pageSize=4&pcid=0&type="+type+"&wdpt=39.892138&jdpt=116.323148";

//        if (oneNearbyFragment.equals("1")){
//            path1 = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+page+"&pageSize=4&pcid=0&type=ms&wdpt=39.892138&jdpt=116.323148";
//        }else if(twoNearbyFragment.equals("2")){
//            path1 = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+page+"&pageSize=4&pcid=0&type=cjy&wdpt=39.892138&jdpt=116.323148";
//
//        }else if (threeNearbyFragment.equals("3")){
//            path1 = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+page+"&pageSize=4&pcid=0&type=mmm&wdpt=39.892138&jdpt=116.323148";
//
//        }else if (fourNearbyFragment.equals("4")){
//            path1 = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+page+"&pageSize=4&pcid=0&type=&wdpt=39.892138&jdpt=116.323148";
//
//        }
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(path1);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
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
                    Log.d("string", "商铺string------------------------>" + string);
                    //JsonElement解析从后台获取的string对象，string对象包含属性Status和Msg
                    JsonElement je = new JsonParser().parse(string);
                    Log.d("string", "商铺Status------------------------>" + je.getAsJsonObject().get("Status"));
                    //判断Status是否为0或Msg是否为空，若tatus为0或Msg为空，则说明得到的string没有数据，页面不加载
                    if (je.getAsJsonObject().get("Status").equals("0") || je.getAsJsonObject().get("Msg").equals("")){
                        return;
                    }else{
                        storDataRes = gson.fromJson(string, OneDataRes.class);
                        storData.clear();
                        storData.addAll(storDataRes.getMsg());
                        Message msg = handler.obtainMessage();
                        msg.what = STOR_DATA;
                        handler.sendMessage(msg);
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
        page++;
    }


}
