package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.Initcomplete1DataRes;
import com.example.administrator.myonetext.bean.OrderCancleDataRes;
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

public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.sendSMS)
    TextView sendSMS;
    @BindView(R.id.SMSCode)
    EditText SMSCode;
    @BindView(R.id.newPwd)
    EditText newPwd;
    @BindView(R.id.surePwd)
    EditText surePwd;
    @BindView(R.id.complete)
    TextView complete;
    private String mobilePhon,smsCode,loginpwd,sure_Pwd;
    private static final int SERVER_EXCEPTION=4;
    private static final int NETWORK_ANOMALY=5;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.sendSMS, R.id.complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.sendSMS:
                mobilePhon=phoneNumber.getText().toString().trim();
                initgetSMScode(2,mobilePhon);
                break;
            case R.id.complete:
                mobilePhon=phoneNumber.getText().toString().trim();
                loginpwd=newPwd.getText().toString().trim();
                sure_Pwd= surePwd.getText().toString().trim();
                smsCode= SMSCode.getText().toString().trim();
                if (!loginpwd.equals(sure_Pwd)){
                    surePwd.setText("");
                    ToastUtils.showToast(this,"前后密码不一致");
                }
                initcomplete1(mobilePhon,smsCode);

                break;
        }
    }

    private void initcomplete1(String loginname, String authcode) {
        String nearbyMs = "http://app.tealg.com/api/app/Login.ashx?flag=ValidCode&loginname="+loginname+"&authcode="+authcode+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    Log.d("ForgetPassword1", "商家onResponse:--------------------------> " + string);
                    Initcomplete1DataRes initcomplete1DataRes = gson.fromJson(string, Initcomplete1DataRes.class);
                    int state = initcomplete1DataRes.getState();
                    if (state==0){
                        Log.d("ForgetPassword1", "onResponse:---------------------->失败");
                    }else  if (state==1){
                        int uid = initcomplete1DataRes.getUid();
                         initcomplete2(uid,loginpwd);
                        Log.d("ForgetPassword1", "onResponse:---------------------->成功");

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

    private void initcomplete2(int uid, String loginpwd) {
        String nearbyMs = "http://app.tealg.com/api/app/Login.ashx?flag=UpdatePw&uid="+uid+"&loginpwd="+loginpwd+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    Log.d("ForgetPassword2", "商家onResponse:--------------------------> " + string);
                    OrderCancleDataRes orderCancleDataRes = gson.fromJson(string, OrderCancleDataRes.class);
                    int state = orderCancleDataRes.getState();
                    if (state==0){
                        Log.d("ForgetPassword2", "onResponse:---------------------->失败");
                    }else  if (state==1){
                        Log.d("ForgetPassword2", "onResponse:---------------------->成功");
                        finish();
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

    private void initgetSMScode(int Type,String mobilePhone) {
        String nearbyMs = "http://app.tealg.com/api/app/SendAuthCode.ashx?flag=MobilePhone&Type="+Type+"&MobilePhone="+mobilePhone+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    Log.d("ForgetPassword", "商家onResponse:--------------------------> " + string);
                    OrderCancleDataRes orderCancleDataRes = gson.fromJson(string, OrderCancleDataRes.class);
                    int state = orderCancleDataRes.getState();
                    if (state==0){
                        Log.d("ForgetPassword", "onResponse:---------------------->验证码发送失败");

                    }else  if (state==1){
                        Log.d("ForgetPassword", "onResponse:---------------------->验证码发送成功");

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
}
