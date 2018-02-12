package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.SmsMessageAdapter;
import com.example.administrator.myonetext.bean.NearbyStoreDataRes;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/9.
 */

public class SmsMessageFragment extends BaseFragment {
    @BindView(R.id.smsMessagelist)
    ListView smsMessagelist;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
private SmsMessageAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
//                    storData.clear();
//                    storData.addAll(nearbyStoreDataRes.getMsg());
                    adapter.notifyDataSetChanged();
                    break;
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getActivity(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getActivity(), (String) msg.obj);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.smsmessage, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSmartRefresh(view);
      //  initData();
        adapter=new SmsMessageAdapter();
        smsMessagelist.setAdapter(adapter);


        return view;
    }

//    private void initData() {
//        String nearbyMs = "";
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        Request.Builder requestBuilder = new Request.Builder().url(nearbyMs);
//        //可以省略，默认是GET请求
//        requestBuilder.method("GET", null);
//        final Request request = requestBuilder.build();
//        final Call mcall = mOkHttpClient.newCall(request);
//        mcall.enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Message msg = handler.obtainMessage();
//                msg.what = SERVER_EXCEPTION;
//                msg.obj = "服务器" + e.getMessage();
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                if (null != response) {
//                    Gson gson = new Gson();
//                    String string = response.body().string();
//                    Log.d("string", "商家onResponse:--------------------------> " + string);
//                    nearbyStoreDataRes = gson.fromJson(string, .class);
//
//
//
//                    Message message = new Message();
//                    message.what = INIT_STOR_DATE;
//                    handler.sendMessage(message);
//                }else {
//                    Message msg = handler.obtainMessage();
//                    msg.what = NETWORK_ANOMALY;
//                    msg.obj = "无网络";
//                    handler.sendMessage(msg);
//                }
//            }
//        });
//
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void initSmartRefresh(View view) {
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
