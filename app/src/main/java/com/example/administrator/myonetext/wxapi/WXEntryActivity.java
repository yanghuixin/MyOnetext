package com.example.administrator.myonetext.wxapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.myonetext.MyApplication;
import com.example.administrator.myonetext.bean.CodeDataRes;
import com.example.administrator.myonetext.bean.UserDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/16.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        MyApplication.api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        Log.i("weixin", ":---->" + resp.errStr);
        Log.i("weixin", "错误码:---->" + resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtils.showToast(getApplicationContext(), "拒绝授权微信登录");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (resp.getType()== RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (resp.getType() == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                ToastUtils.showToast(getApplicationContext(), message);
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                        if (sendResp != null) {
                            String code = sendResp.code;
                           Log.d("weixin", "onResp:--------------------> "+code);
                            getAccess_token(code);
                        }
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtils.showToast(getApplicationContext(), "微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    private void getAccess_token(String code) {
        String url = "http://app.tealg.com/api/app/Login.ashx?flag=WeiXinLogin&code=" +
                code+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";

        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        final Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {

            private String openid;
            private String access_token;

            @Override
            public void onFailure(Request request, IOException e) {
               Log.i("weixin", "onFailure:----------------------->网络请求失败 ");
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    CodeDataRes codeDataRes = gson.fromJson(string, CodeDataRes.class);
                    access_token = codeDataRes.getAccess_token();
                    openid = codeDataRes.getOpenid();
                    //得到用户信息：
                       getWeiXinUserInfo(access_token,openid);
                }else {
                    Log.i("weixin", "onFailure:----------------------->无网络 ");
                }
            }
        });
    }

    private void getWeiXinUserInfo(String access_token, String openid) {
        String url = "http://app.tealg.com/api/app/Login.ashx?flag=GetUserMsg_WXlogin&access_token="+access_token+"&openid="+openid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        final Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("weixin", "onFailure:----------------------->网络请求失败 ");
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    UserDataRes userDataRes = gson.fromJson(string, UserDataRes.class);
                    int  status = userDataRes.getStatus();

                    if (status==0){
                        Log.i("weixin", "onFailure:----------------------->登录失败 ");
                    }else  if (status==1){
                        Log.i("weixin", "onFailure:----------------------->登录成功 "+userDataRes.toString());
                    }else  if (status==2){
                        Log.i("weixin", "onFailure:----------------------->密码输入不正确 ");
                    }else  if (status==3){
                        Log.i("weixin", "onFailure:----------------------->您输入的帐户名和密码不匹配，请重新输入");
                    }

                }else {
                    Log.i("weixin", "onFailure:----------------------->无网络 ");
                }
            }
        });
    }

}
