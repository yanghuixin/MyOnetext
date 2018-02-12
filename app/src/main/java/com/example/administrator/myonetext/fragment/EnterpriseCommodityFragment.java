package com.example.administrator.myonetext.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.EnterpriseCommodityAdapter;
import com.example.administrator.myonetext.bean.EnterpriseCommodityDataRes;
import com.example.administrator.myonetext.myview.MyGridView;
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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/26.
 */

public class EnterpriseCommodityFragment extends Fragment implements OnBannerListener {
    @BindView(R.id.storeBanner)
    Banner banner;
    @BindView(R.id.storehomefragment_gradeview)
    MyGridView storehomefragmentGradeview;
    Unbinder unbinder;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout refreshLayout;
    private ArrayList<String> list_path;
    private int bid, category;
    private int page = 1;
    private EnterpriseCommodityAdapter adapter;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    private List<EnterpriseCommodityDataRes.MsgBean> msgBean = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    msgBean.clear();
                    msgBean.addAll(enterpriseCommodityDataRes.getMsg());
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
    private EnterpriseCommodityDataRes enterpriseCommodityDataRes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.enterprisecommodityfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
//        if (isAdded()) {//判断Fragment已经依附Activity
//            Bundle bundle =getArguments();
//            String blbpic = bundle.getString("blbpic");
//             bid = bundle.getInt("bid");
//            Log.d("blbpic1", "handleMessage:----------> "+blbpic.toString());
//            String[] split = blbpic.split(";");
//            initMyBanner(split);
//        }
        category = 0;
//initEnterpriseCommodityData(bid,page,category);
        adapter = new EnterpriseCommodityAdapter(getActivity(), msgBean);
        storehomefragmentGradeview.setAdapter(adapter);
        initSmartRefresh();
        return view;

    }
    private void initSmartRefresh() {
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//            }
//        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //initEnterpriseCommodityData(bid,page,category);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
    private void initEnterpriseCommodityData(int bid, int page, int category) {
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=getprobybid&bid=" + bid + "&page=" + page + "&pagesize=4&category=" + category + "&txtss=0&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    enterpriseCommodityDataRes = gson.fromJson(string, EnterpriseCommodityDataRes.class);
                    if (enterpriseCommodityDataRes.getStatus() == 1) {
                        Message message = new Message();
                        message.what = INIT_STOR_DATE;
                        handler.sendMessage(message);
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
        page++;
    }

    private void initMyBanner(String[] split) {
//放图片地址的集合
        list_path = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list_path.add(split[i]);
        }
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(5000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void OnBannerClick(int position) {

    }

    //自定义的图片加载器
    public static class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (Util.isOnMainThread()) {
                Glide.with(context).load((String) path).into(imageView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
