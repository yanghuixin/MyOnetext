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
import com.example.administrator.myonetext.adapter.CollectionStoreAdapter;
import com.example.administrator.myonetext.adapter.OneAdapter;
import com.example.administrator.myonetext.adapter.PuductAdapter;
import com.example.administrator.myonetext.adapter.TwoAdapter;
import com.example.administrator.myonetext.bean.CollectionStoreDataRes;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.OneDataRes;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/11.
 */

public class StoreFragment extends BaseFragment {
    @BindView(R.id.productList)
    ListView productList;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private CollectionStoreAdapter adapter;
    private int page = 1;
    private CollectionStoreDataRes collectionStoreDataRes;//商铺
    private   List<CollectionStoreDataRes.MessageBean> CollectionStoreMessageBean = new ArrayList<>();////商铺
    private final static int STOR_DATA = 3;//商铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOR_DATA:
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
    private int pid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (GouhuiUser.getInstance().hasUserInfo(getActivity())) {
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(getActivity());
            String uid = userInfo.getUid();
            pid = Integer.parseInt(uid);
            initData(page, pid, 2);
            adapter = new CollectionStoreAdapter(getActivity(), CollectionStoreMessageBean);
            productList.setAdapter(adapter);
            initSmartRefresh(view);
        } else {
            ToastUtils.showToast(getActivity(), "您还未登录");
        }
        return view;
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
                initData(page, pid, 2);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initData(int page, int pid, int type) {
        String path1 = "http://app.tealg.com/api/app/Member.ashx?flag=myconllection&pid=" + pid + "&page=" + page + "&pageSize=4&type=" + type + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(path1);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
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
                    ///////////////////////////////////////////////////////////////////////////
//                    try{
//                        JsonElement je = new JsonParser().parse(string);
//                        if (je.getAsJsonObject().get("Status").equals("0")){//{ "status":0,"message":"未登录或登录已超时"}
//                            Log.d("uid", "onResponse: -------------------------------status:0");
//                            return;
//                        }else {
//                            collectionStoreDataRes = gson.fromJson(string, CollectionStoreDataRes.class);
//                            if (collectionStoreDataRes.getStatus()==0) {
//                                return;
//                            } else {
//                                Log.d("uid", "storDataRes.getStatus(): --------->"+collectionStoreDataRes.getStatus());
//                                CollectionStoreMessageBean.addAll(collectionStoreDataRes.getMessage());
//                                Message msg = handler.obtainMessage();
//                                msg.what = STOR_DATA;
//                                handler.sendMessage(msg);
//                            }
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

                    /////////////////////////////////////////////////////////
                    Log.d("uid", "onResponse: collectionStoreDataRes-------------- 1>");
                    collectionStoreDataRes = gson.fromJson(string, CollectionStoreDataRes.class);
                    CollectionStoreMessageBean.addAll(collectionStoreDataRes.getMessage());
                    Message msg = handler.obtainMessage();
                    msg.what = STOR_DATA;
                    handler.sendMessage(msg);
                    ////////////////////////////////////////////////////////////////
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
    }
}
