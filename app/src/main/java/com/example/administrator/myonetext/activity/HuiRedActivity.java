package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.PPaymoneyOrdeDataRes;
import com.example.administrator.myonetext.bean.RedPackageDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuiRedActivity extends AppCompatActivity {

    @BindView(R.id.redMoney)
    TextView redMoney;
    @BindView(R.id.lookRedPackage)
    TextView lookRedPackage;
    private int pid=974;
    private final static int SERVER_EXCEPTION = 6;//服务器异常
    private final static int NETWORK_ANOMALY = 7;//网络异常
    private final static int ORDER_DATA = 5;//产品
    private RedPackageDataRes redPackageDataRes;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case ORDER_DATA:
                    redMoney.setText(redPackageDataRes.getNum()+"");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hui_red);
        ButterKnife.bind(this);
        initData(pid);
    }

    private void initData(int pid) {
        String path = "http://app.tealg.com/api/app/Member.ashx?flag=qiandao&&pid="+pid+"&&appkey=4b3b1f1235j654af4561gracv54c4h5q";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(path);
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
//                        if (je.getAsJsonObject().get("Status").equals("0") || je.getAsJsonObject().get("Msg").equals("")){
//                            return;
//                        }else {
//                            redPackageDataRes = gson.fromJson(string, RedPackageDataRes.class);
//                            Message msg = new Message();
//                            msg.what = ORDER_DATA;
//                            handler.sendMessage(msg);
//                        }
                        redPackageDataRes = gson.fromJson(string, RedPackageDataRes.class);
                        Message msg = new Message();
                        msg.what = ORDER_DATA;
                        handler.sendMessage(msg);
                    } catch (Exception e){
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
    }

    @OnClick(R.id.lookRedPackage)
    public void onViewClicked() {
    }
}
