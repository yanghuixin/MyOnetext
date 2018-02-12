package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.CityEvent;
import com.example.administrator.myonetext.bean.OrderCancleDataRes;
import com.example.administrator.myonetext.bean.RegisterFinishDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.choseCountry)
    TextView choseCountry;
    @BindView(R.id.phoneNum_rigister)
    EditText phoneNumRigister;
    @BindView(R.id.sendsmsNum)
    TextView sendsmsNum;
    @BindView(R.id.smsCode_rigist)
    EditText smsCodeRigist;
    @BindView(R.id.name_rigist)
    EditText nameRigist;
    @BindView(R.id.pwd_rigist)
    EditText pwdRigist;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.surePwd_rigist)
    EditText surePwdRigist;
    @BindView(R.id.finish)
    Button finish;
    private String startCityStr = null;
private String loginpwd,sure_Pwd,phoneNum,smsCode,name;
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.choseCountry, R.id.sendsmsNum, R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.choseCountry:
                Intent intent = new Intent(this, ChoseStartArriveCityActivity.class);
                intent.putExtra("flag", true);
                startActivity(intent);
                break;
            case R.id.sendsmsNum:
                phoneNum=phoneNumRigister.getText().toString().trim();
                ToastUtils.showToast(getApplicationContext(),"获取手机验证码");
                Log.d("onResponse", "onViewClicked: -------------------->"+phoneNum);
                initgetSMScode(1,phoneNum);
                break;
            case R.id.finish:
                phoneNum=phoneNumRigister.getText().toString().trim();
                loginpwd=pwdRigist.getText().toString().trim();
                sure_Pwd= surePwdRigist.getText().toString().trim();
                smsCode= smsCodeRigist.getText().toString().trim();
                name=nameRigist.getText().toString().trim();
                if (!loginpwd.equals(sure_Pwd)){
                    surePwdRigist.setText("");
                    ToastUtils.showToast(this,"前后密码不一致");
                }else {
                    Log.d("onResponse", "onViewClicked: ---------------------->"+smsCode);
                    initFinish(phoneNum,smsCode,name,loginpwd);
                }
                break;
        }
    }
    @Subscribe
    public void onEventMainThread(CityEvent cityEvent) {//城市的返回
        if (country != null && cityEvent.isFlag()) {
            startCityStr = cityEvent.getCities();
            country.setText(startCityStr);
        }
    }
    private void initFinish(String AuthMobilePhone, String NoteAuthCode, String name, String Pwd) {
        String nearbyMs = "http://app.tealg.com/api/app/Login.ashx?flag=Register&please_userid=0&Name="+name+"&Pwd="+Pwd+"&AuthMobilePhone="+AuthMobilePhone+"&NoteAuthCode="+NoteAuthCode+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    RegisterFinishDataRes registerFinishDataRes = gson.fromJson(string, RegisterFinishDataRes.class);
                    int state = registerFinishDataRes.getStatus();
                    if (state==0){
                        Log.d("onResponse", "onResponse:---------------------->注册失败");
                    }else  if (state==1){
                        Log.d("onResponse", "onResponse:---------------------->注册成功");
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

    private void initgetSMScode(int Type, String mobilePhone) {
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
