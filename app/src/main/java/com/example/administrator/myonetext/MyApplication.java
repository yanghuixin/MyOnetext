package com.example.administrator.myonetext;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2017/12/21.
 */

public class MyApplication extends Application {
    private static final String APP_ID="wx6612d18cf6469daf";
   public static IWXAPI api;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(this);
        //讯飞语音初始化工作
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=5a3b59db");
        //扫描二维码初始化工作
        ZXingLibrary.initDisplayOpinion(this);
        registToWX();
    }

    private void registToWX() {
        //APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        // 将该app注册到微信
        api.registerApp(APP_ID);

    }
}
