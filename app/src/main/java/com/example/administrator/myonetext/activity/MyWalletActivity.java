package com.example.administrator.myonetext.activity;

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
import com.example.administrator.myonetext.adapter.MyWalletAdapter;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.MyWalletDataRes;
import com.example.administrator.myonetext.bean.NearbyStoreDataRes;
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

public class MyWalletActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.walletList)
    ListView walletList;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private MyWalletAdapter adapter;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    message.clear();
                    message.addAll(myWalletDataRes.getMessage());
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
    private MyWalletDataRes myWalletDataRes;
    private List<MyWalletDataRes.MessageBean> message=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        if (GouhuiUser.getInstance().hasUserInfo(this)) {
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(this);
            String uid = userInfo.getUid();
            int pid = Integer.parseInt(uid);
            initstorData(page, pid);
            initSmartRefresh(pid);
            adapter = new MyWalletAdapter(this,message,pid);
            walletList.setAdapter(adapter);
        } else {
            ToastUtils.showToast(this, "您还未登录");
        }
    }

    private void initSmartRefresh(final int pid) {
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
                initstorData(page, pid);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initstorData(int page, int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/Member.ashx?flag=mybankcards&pid=" + pid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    try{
                        JsonElement je = new JsonParser().parse(string);
                        if (je.getAsJsonObject().get("status").getAsInt() == 0){
                            return;
                        }else {
                            myWalletDataRes = gson.fromJson(string, MyWalletDataRes.class);
//                            message.clear();
//                            message.addAll(myWalletDataRes.getMessage());
                            Log.d("request", "onResponse:--------------------------------> " + myWalletDataRes.getMessage().size());
                            Message message = new Message();
                            message.what = INIT_STOR_DATE;
                            handler.sendMessage(message);
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
        page++;
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
}
