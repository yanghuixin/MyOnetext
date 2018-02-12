package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.MyWallentRes;
import com.example.administrator.myonetext.bean.UserInfo;
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

public class MyWallentActivity extends AppCompatActivity {
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.MyAllMoney)
    TextView MyAllMoney;
    @BindView(R.id.PromotingProfit)
    TextView PromotingProfit;
    @BindView(R.id.MoneyinCash)
    TextView MoneyinCash;
    @BindView(R.id.CashinCash)
    TextView CashinCash;
    @BindView(R.id.Refunds)
    TextView Refunds;
    @BindView(R.id.AccountDetails)
    TextView AccountDetails;
    @BindView(R.id.Withdrawals1)
    TextView Withdrawals1;
    private MyWallentRes myWallentRes;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    if (myWallentRes.getI_status()==1){
                        Withdrawals1.setText("提现");
                    }else if (myWallentRes.getI_status()==0){
                        Withdrawals1.setText("上传资料");
                    }
                    PromotingProfit.setText(myWallentRes.getGcqAmt());
                    MoneyinCash.setText(myWallentRes.getKtxAmt());
                    CashinCash.setText(myWallentRes.getYtxAmt());
                    Refunds.setText(myWallentRes.getYtkAmt());
                    MyAllMoney.setText(myWallentRes.getAllAmt());
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallent);
        ButterKnife.bind(this);
        if (GouhuiUser.getInstance().hasUserInfo(this)) {
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(this);
            String uid = userInfo.getUid();
            int pid = Integer.parseInt(uid);
            initMyWallentData(pid);
        }else {
            Withdrawals1.setText("上传资料");
        }
    }

    private void initMyWallentData(int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/Member.ashx?flag=mymoney&pid="+pid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    myWallentRes = gson.fromJson(string, MyWallentRes.class);
                    if (myWallentRes.getState() == 1) {
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
    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.AccountDetails,R.id.Withdrawals1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.AccountDetails://账单明细
                break;
            case R.id.Withdrawals1://提现
                Intent intent=new Intent();
                if (GouhuiUser.getInstance().hasUserInfo(this)) {
                    intent.setClass(this,WithdrawalsActivity.class);
                }else {
                    intent.setClass(this,UploadDataActivity.class);
                }
                startActivity(intent);
                break;
        }
    }
}
