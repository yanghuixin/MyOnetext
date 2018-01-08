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
import com.example.administrator.myonetext.adapter.RAndCSOrderAdapter;
import com.example.administrator.myonetext.bean.PPaymoneyOrdeDataRes;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/2.
 * 待发货
 */

public class PDGoodsOrderFragment extends BaseFragment {
    @BindView(R.id.orderlist)
    ListView orderlist;
    @BindView(R.id.orderRefreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    //private  PPaymoneyOrderFragmentAdapter adapter;
    private int pagei = 1;
    private int pid = 974;
    // private int type = 4;//type:查询订单状态；3：待付款；4：待发货；5：待收货；6：已完成；7：退款/售后
    private int type = 7;
    private final static int ORDER_DATA = 5;//产品
    private final static int SERVER_EXCEPTION = 6;//服务器异常
    private final static int NETWORK_ANOMALY = 7;//网络异常
    private List<PPaymoneyOrdeDataRes.MsgBean> msg=new ArrayList<PPaymoneyOrdeDataRes.MsgBean>();
    private RAndCSOrderAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getActivity(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getActivity(), (String) msg.obj);
                    break;
                case ORDER_DATA:
                    adapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ppaymoney_orderfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListData(pagei, pid, type);//pid int 会员编号;type:查询订单状态；3：待付款；4：待发货；5：待收货；6：已完成；7：退款/售后
        initSmartRefresh(view);
        adapter=new RAndCSOrderAdapter(getActivity(),msg);
        orderlist.setAdapter(adapter);
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
                //  initListData(1, pid, type);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initListData(pagei, pid, type);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initListData(int pagei, int pid, int type) {
        String path = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=myorder&pid="+pid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q&type="+type+"&page="+pagei+"&pageSize=1";
        // String path = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=myorder&pid=974&appkey=4b3b1f1235j654af4561gracv54c4h5q&type=7&page=1&pageSize=1";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(path);
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
                    PPaymoneyOrdeDataRes pPaymoneyOrdeDataRes = gson.fromJson(string, PPaymoneyOrdeDataRes.class);
                    int status = pPaymoneyOrdeDataRes.getStatus();
                    if (status==0){
                        Log.d("onResponse", "onResponse:http://app.tealg.com/api/app/OrderMsg.ashx?flag=myorder&pid=974&appkey=4b3b1f1235j654af4561gracv54c4h5q&type=3&page=1&pageSize=1----------------->status="+status);
                    }else if (status==1){
                        msg.addAll(pPaymoneyOrdeDataRes.getMsg());
                        Message msg = new Message();
                        msg.what = ORDER_DATA;
                        handler.sendMessage(msg);
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
        pagei++;//可能需要换位置
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
