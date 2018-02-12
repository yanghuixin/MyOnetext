package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.ReceiverAdressAdapter;
import com.example.administrator.myonetext.bean.AdressDataRes;
import com.example.administrator.myonetext.bean.DefaultAdressDataRes;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.UserInfo;
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

public class MyReceiverAdressActivity extends AppCompatActivity {
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.myrecdresslist)
    ListView myrecdresslist;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.Set_default)
    Button SetDefault;
    @BindView(R.id.add_adress)
    Button addAdress;
    private ReceiverAdressAdapter adapter;
    private int page = 1;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    adressData.clear();
                    adressData.addAll(adressDataRes.getMsg());
                    Log.d("adressData", "handleMessage: -------------------------->"+adressData.size());
                    adapter.notifyDataSetChanged();
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
    private List<AdressDataRes.MsgBean> adressData = new ArrayList<>();
    private AdressDataRes adressDataRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_receiver_adress);
        ButterKnife.bind(this);
        if (GouhuiUser.getInstance().hasUserInfo(this)) {
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(this);
            String uid = userInfo.getUid();
            int  pid = Integer.parseInt(uid);
            initAdressData(pid);
        } else {
            ToastUtils.showToast(this, "您还未登录");
        }
        adapter = new ReceiverAdressAdapter(this, adressData);
        myrecdresslist.setAdapter(adapter);
        //  initSmartRefresh();
//////////////////////////////////////////////////////////////
    }
    private void initAdressData(int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=singleuseradd&pid=" + pid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    try {
                        JsonElement je = new JsonParser().parse(string);
                        if (je.getAsJsonObject().get("status").equals("0")) {
                            return;
                        } else {
                            adressDataRes = gson.fromJson(string, AdressDataRes.class);
                            if (adressDataRes.getStatus() == 1) {
                                Message message = new Message();
                                message.what = INIT_STOR_DATE;
                                handler.sendMessage(message);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    private void initSmartRefresh() {
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initAdressData(page);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @OnClick({R.id.re_clickBack, R.id.threespot,R.id.Set_default, R.id.add_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.Set_default:

                adapter.getAid();

                break;
            case R.id.add_adress:
                break;
        }
    }
//设为默认收货地址的请求
    private void initdefaultAdress(int rid, int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=setdefaultadd&pid="+pid+"&rid="+rid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    ////////////////////////////////////////////////////////
//                    Gson gson = new Gson();
//                    String string = response.body().string();
//                    Log.d("string", "商家onResponse:--------------------------> " + string);
//                    myWalletDataRes = gson.fromJson(string, MyWalletDataRes.class);
//                    if (myWalletDataRes.getStatus()==1){
//                        Message message = new Message();
//                        message.what = INIT_STOR_DATE;
//                        handler.sendMessage(message);
//                    }else {
//                        return;
//                    }
                    ///////////////////////////////////////////////////////////
                    Gson gson = new Gson();
                    String string = response.body().string();
                    try{
                        JsonElement je = new com.google.gson.JsonParser().parse(string);
                        if (je.getAsJsonObject().get("status").equals("0")){
                            return;
                        }else {
                            DefaultAdressDataRes defaultAdressDataRes = gson.fromJson(string, DefaultAdressDataRes.class);
                            if (defaultAdressDataRes.getStatus()==1){
                                Log.d("defaultAdress", "onResponse: ---------------------------->"+defaultAdressDataRes.getMsg());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //////////////////////////////////////////////////////////
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });


    }
}
