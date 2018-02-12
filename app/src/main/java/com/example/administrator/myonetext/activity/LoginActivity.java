package com.example.administrator.myonetext.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myonetext.MyApplication;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.OrderCancleDataRes;
import com.example.administrator.myonetext.bean.UserDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.login1)
    RadioButton login1;
    @BindView(R.id.login2)
    RadioButton login2;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.Quick_login_of_phone)
    LinearLayout QuickLoginOfPhone;
    @BindView(R.id.login_of_phone_name)
    LinearLayout loginOfPhoneName;
    @BindView(R.id.Forget_password)
    TextView ForgetPassword;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.Alipay_login)
    TextView AlipayLogin;
    @BindView(R.id.WeChat_login)
    TextView WeChatLogin;
    @BindView(R.id.qq_login)
    TextView qqLogin;
    @BindView(R.id.phoneNum)
    EditText phoneNum;
    @BindView(R.id.SMS_code)
    EditText SMSCode;
    @BindView(R.id.name_phoneNum)
    EditText namePhoneNum;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.getSMS_code)
    TextView getSMSCode;
    private String mobilePhone,authcode,loginname,loginpwd;
    private final static int LOGIN_FAIL=1;
    private final static int PASSWORD_FALSE=2;
    private final static int NAME_PASSWORD_MISMATCH=3;
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    private final static int LOGIN_SUCESS=6;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_FAIL:
                    ToastUtils.showToast(getApplicationContext(), "登录失败");
                    break;
                case PASSWORD_FALSE:
                    ToastUtils.showToast(getApplicationContext(), "密码输入不正确");
                    break;
                case NAME_PASSWORD_MISMATCH:
                    ToastUtils.showToast(getApplicationContext(), "您输入的帐户名和密码不匹配，请重新输入");
                    break;
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case LOGIN_SUCESS:
                  finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        /**
         * 启动默认选中第一个
         */
        radioGroup.check(R.id.login2);
        loginOfPhoneName.setVisibility(View.GONE);
        QuickLoginOfPhone.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.re_clickBack, R.id.login1, R.id.login2,R.id.getSMS_code, R.id.Forget_password, R.id.register, R.id.login, R.id.Alipay_login, R.id.WeChat_login, R.id.qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.login1:
                QuickLoginOfPhone.setVisibility(View.GONE);
                loginOfPhoneName.setVisibility(View.VISIBLE);
                break;
            case R.id.login2:
                loginOfPhoneName.setVisibility(View.GONE);
                QuickLoginOfPhone.setVisibility(View.VISIBLE);
                break;
            case R.id.getSMS_code:
                //13521319845
                ToastUtils.showToast(getApplicationContext(),"获取验证码");
                mobilePhone = phoneNum.getText().toString().trim();
                initgetSMScode(3,mobilePhone);
                break;
            case R.id.Forget_password:
                Intent intent=new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                Intent intent1=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login:
                mobilePhone = phoneNum.getText().toString().trim();
                authcode=SMSCode.getText().toString().trim();
                loginname=namePhoneNum.getText().toString().trim();
                loginpwd=pwd.getText().toString().trim();
                if (login1.isChecked()){
                    initlogin1(loginname,loginpwd);
                }else if (login2.isChecked()){
                    initlogin2(mobilePhone,authcode);
                }
                break;
            case R.id.Alipay_login:

                break;
            case R.id.WeChat_login:
                wxLogin();
                break;
            case R.id.qq_login:
                break;
        }
    }

    private void wxLogin() {
        if (!MyApplication.api.isWXAppInstalled()) {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        MyApplication.api.sendReq(req);
    }

    private void initlogin1(String loginname, String loginpwd) {
        String nearbyMs = "http://app.tealg.com/api/app/Login.ashx?flag=Login&type=0&loginname="+loginname+"&loginpwd="+loginpwd+"&chkRememberMe=0";
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
                    UserDataRes userDataRes = gson.fromJson(string, UserDataRes.class);
                    int status = userDataRes.getStatus();
                    if (status==0){
                        Message msg=handler.obtainMessage();
                        msg.what=LOGIN_FAIL;
                        handler.sendMessage(msg);
                        Log.d("onResponse", "onResponse:---------------------->登录失败");
                    }else if (status==1){
                        Log.d("onResponse", "onResponse:---------------------->登录成功");
                       String loginname=namePhoneNum.getText().toString().trim();
                        String loginpwd=pwd.getText().toString().trim();
                        GouhuiUser.getInstance().saveUserInfo(getApplicationContext(), loginname, loginpwd,userDataRes.getUid(),userDataRes.getUsafe(),userDataRes.getUflag());
                        Message msg=handler.obtainMessage();
                        msg.what=LOGIN_SUCESS;
                        handler.sendMessage(msg);
                    }else if (status==2){
                        Message msg=handler.obtainMessage();
                        msg.what=PASSWORD_FALSE;
                        handler.sendMessage(msg);
                        Log.d("onResponse", "onResponse:---------------------->密码输入不正确");
                    }else if (status==3){
                        Message msg=handler.obtainMessage();
                        msg.what=NAME_PASSWORD_MISMATCH;
                        handler.sendMessage(msg);
                        Log.d("onResponse", "onResponse:---------------------->您输入的帐户名和密码不匹配，请重新输入");
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
        Log.d("onResponse", "initgetSMScode: ----------------------------->"+nearbyMs.trim());
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
                    OrderCancleDataRes orderCancleDataRes = gson.fromJson(string, OrderCancleDataRes.class);
                    int state = orderCancleDataRes.getState();
                    if (state==0){
                        Log.d("onResponse", "onResponse:---------------------->验证码发送失败");

                    }else  if (state==1){
                        Log.d("onResponse", "onResponse:---------------------->验证码发送成功");

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
    private void initlogin2(String loginname,String authcode) {
        String nearbyMs = "http://app.tealg.com/api/app/Login.ashx?flag=LoginPhone&type=0&loginname="+loginname+"&authcode="+authcode+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    UserDataRes userDataRes = gson.fromJson(string, UserDataRes.class);
                    int status = userDataRes.getStatus();
                    if (status==0){
                        Log.d("onResponse", "onResponse:---------------------->错误信息");
                    }else if (status==1){
                        Log.d("onResponse", "onResponse:---------------------->登录成功");
                    }else if (status==2){
                        Log.d("onResponse", "onResponse:---------------------->注册失败");
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
