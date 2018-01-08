package com.example.administrator.myonetext.utils;

import android.os.Message;
import android.util.Log;

import com.example.administrator.myonetext.adapter.OneAdapter;
import com.example.administrator.myonetext.bean.OneDataRes;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/25.
 */

public class OkhttpUtils {

    public static String requestData(String path) {
        final String[] string = new String[1];
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(path);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("request", "onFailure:--------------> " + e.getMessage().toString());
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    string[0] = response.body().string();
                } else {
                    Log.d("request", "onFailure:--------------> 无网络");
                }
            }
        });
        return string[0];
    }
}
