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
import com.example.administrator.myonetext.adapter.CollectionProductAdapter;
import com.example.administrator.myonetext.adapter.PuductAdapter;
import com.example.administrator.myonetext.adapter.TwoAdapter;
import com.example.administrator.myonetext.bean.CollectionProductDataRes;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.ProductDataRes;
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

public class PuductFragment extends BaseFragment {
    @BindView(R.id.productList)
    ListView productList;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private int page = 1;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    ////////////////////////////////////////////////////////////
                    adapter.notifyDataSetChanged();
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
    private int pid;

    ///////////////////////////////////////////////////////////
    private CollectionProductAdapter adapter;//商品
    private   List<CollectionProductDataRes.MessageBean> collectionProductDataMessageBean = new ArrayList<>();//产品

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.productfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (GouhuiUser.getInstance().hasUserInfo(getActivity())) {
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(getActivity());
            String uid = userInfo.getUid();
            pid = Integer.parseInt(uid);
            initData(page, pid, 1);
//            adapter = new PuductAdapter();
//            productList.setAdapter(adapter);
///////////////////////////////////////////////////////////////////////
            adapter = new CollectionProductAdapter(getActivity(), collectionProductDataMessageBean);
            productList.setAdapter(adapter);
            initSmartRefresh(view);
        } else {
            ToastUtils.showToast(getActivity(), "您还未登录");
        }
        return view;
    }

    private void initData(int page, int pid, int type) {
        String nearbyMs = "http://app.tealg.com/api/app/Member.ashx?flag=myconllection&pid=" + pid + "&page=" + page + "&pageSize=4&type=" + type + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    try {
                        JsonElement je = new JsonParser().parse(string);
                        if (je.getAsJsonObject().get("Status").getAsInt()==0) {
                            Log.d("uid", "onResponse: -------------------------------status:0");
                            return;
                        } else {
                            Log.d("uid", "onResponse: productData-------------- 1>");
                            CollectionProductDataRes collectionProductDataRes = gson.fromJson(string, CollectionProductDataRes.class);
                           collectionProductDataMessageBean.addAll(collectionProductDataRes.getMessage());
                            Message message = new Message();
                            message.what = INIT_STOR_DATE;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
    }

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
                initData(page, pid, 1);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
