package com.example.administrator.myonetext;

import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.speech.tts.Voice;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.example.administrator.myonetext.utils.JsonParser;
//import com.google.gson.Gson;
//import com.iflytek.cloud.ErrorCode;
//import com.iflytek.cloud.InitListener;
//import com.iflytek.cloud.RecognizerListener;
//import com.iflytek.cloud.RecognizerResult;
//import com.iflytek.cloud.SpeechConstant;
//import com.iflytek.cloud.SpeechError;
//
//import com.iflytek.cloud.SpeechRecognizer;
//import com.iflytek.cloud.ui.RecognizerDialog;
//import com.iflytek.cloud.ui.RecognizerDialogListener;
//import com.iflytek.sunflower.FlowerCollector;
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import com.youth.banner.Banner;
//import com.youth.banner.BannerConfig;
//import com.youth.banner.Transformer;
//import com.youth.banner.listener.OnBannerListener;
//import com.youth.banner.loader.ImageLoader;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.youth.banner.listener.OnBannerListener;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }


}
