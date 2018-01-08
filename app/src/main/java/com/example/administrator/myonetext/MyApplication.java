package com.example.administrator.myonetext;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Administrator on 2017/12/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //讯飞语音初始化工作
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=5a3b59db");
        //扫描二维码初始化工作
        ZXingLibrary.initDisplayOpinion(this);
    }
}
