package com.example.administrator.myonetext.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.activity.NearbyMoreActivity;
import com.example.administrator.myonetext.adapter.NearbyProductAdapter;
import com.example.administrator.myonetext.adapter.NearbyStroeAdapter;
import com.example.administrator.myonetext.bean.BannerDataRes;
import com.example.administrator.myonetext.bean.LALMessageEvent;
import com.example.administrator.myonetext.bean.NearbyProductDataRes;
import com.example.administrator.myonetext.bean.NearbyStoreDataRes;
import com.example.administrator.myonetext.myview.MyListView;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.example.administrator.myonetext.utils.UIHelper;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/26.
 */

public class TwoNearbyFragment extends BaseFragment implements OnBannerListener {
    @BindView(R.id.more2)
    TextView more2;
    @BindView(R.id.nearby_fragment_banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.list1)
    MyListView listView1;
    @BindView(R.id.list2)
    MyListView listView2;
    @BindView(R.id.ly_head)
    LinearLayout ly_head;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int pagei = 1;
    private List<NearbyStoreDataRes.MsgBean> storData = new ArrayList<>();//店铺
    private String rsKey;
    private NearbyStroeAdapter adapter;//店铺
    private NearbyProductAdapter adapter2;
    private ArrayList<String> list_path;
    private List<BannerDataRes.MsgBean> msg = new ArrayList<BannerDataRes.MsgBean>();
    private BannerDataRes bannerDataRes;
    private final static int BANNER_DATA = 1;//轮播图
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int INIT_PRODUCT_DATE = 3;//产品
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    adapter.notifyDataSetChanged();
                    break;
                case BANNER_DATA:
                    initMyBanner(banner);
                    break;
                case INIT_PRODUCT_DATE:
                    adapter2.notifyDataSetChanged();
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
    private String jdpt;
    private String wdpt;
    private List<NearbyProductDataRes.MsgBean> productData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.onnearbyfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initBannerData(284);//{ "Status":"0","Msg":}
        initstorData("cjy", wdpt, jdpt);
        initSmartRefresh(view);
        adapter = new NearbyStroeAdapter(getActivity(), storData);
        listView1.setAdapter(adapter);
        UIHelper.setListViewHeightBasedOnChildren(listView1);
        adapter2 = new NearbyProductAdapter(getActivity(), productData);
        listView2.setAdapter(adapter2);
        UIHelper.setListViewHeightBasedOnChildren(listView2);

        ly_head.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ly_head.setFocusable(true);
                ly_head.setFocusableInTouchMode(true);
                ly_head.requestFocus();
                return false;
            }
        });
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
                initstorData("ms",wdpt,jdpt);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initProductData(pagei, rsKey);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LALMessageEvent event) {
        jdpt = event.getJdpt();
        wdpt = event.getWdpt();
        Log.d("onEvent", "----------------------------->" + jdpt + "--------------->" + wdpt);
    }

    private void initstorData(String type, String wdpt, String jdpt) {
        //  String nearbyMs = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page="+pagei+"&pageSize=4&pcid=0&type="+type+"&wdpt="+wdpt+"&jdpt="+jdpt;

        String nearbyMs = "http://app.tealg.com/api/app/Business.ashx?flag=mapbusiness&page=1&pageSize=4&pcid=0&type=cjy&wdpt=39.892138&jdpt=116.323148";
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
                    NearbyStoreDataRes nearbyStoreDataRes = gson.fromJson(string, NearbyStoreDataRes.class);
                    rsKey = nearbyStoreDataRes.getRsKey();
                    //WttWRG9BiJ0lsydfBt3YY1gmV/u+8/CrMHuR0ioV0qg=
                    Log.d("rsKey", "twoonResponse:---------------------------> "+rsKey);
                    initProductData(pagei, rsKey);
                    storData.clear();
                    storData.addAll(nearbyStoreDataRes.getMsg());
                    Message message = new Message();
                    message.what = INIT_STOR_DATE;
                    handler.sendMessage(message);
                }else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void initProductData(int pagei, String rsKey) {
        //  String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=fjProduct&page=1&pageSize=4&rsKey=8489557DE1881279D8C45A79E57F3FADC153AFF72CA2EE1FF292AFD3E4F4E7B1&pcid=0&type=1758;
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=fjProduct&page=" + pagei + "&pageSize=6&rsKey=" + rsKey + "&pcid=0&type=cjy";
        //"rsKey":"8489557DE1881279D8C45A79E57F3FADC153AFF72CA2EE1FF292AFD3E4F4E7B1"
        //   Log.d("pathToday", "initstorData: -------------------------->"+pathToday);
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
                    Log.d("string", "产品onResponse:--------------------------> " + string);
                    NearbyProductDataRes nearbyProductDataRes = gson.fromJson(string, NearbyProductDataRes.class);
                    productData.addAll(nearbyProductDataRes.getMsg());

                    Message message = new Message();
                    message.what = INIT_PRODUCT_DATE;
                    handler.sendMessage(message);
                }else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
        pagei++;//可能需要换位置
        //pullToRefreshListView1.onRefreshComplete();//取消刷新的动画
    }

    private void initMyBanner(Banner banner) {
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new HomeFragment.MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    private void initBannerData(int type) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://app.tealg.com/api/app/Adv.ashx?flag=fjhuodong&type=" + type)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = handler.obtainMessage();
                msg.what = SERVER_EXCEPTION;
                msg.obj = "服务器" + e.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    Log.d("response", "轮播图onResponse: ------------------------->" + response);
                    String string = response.body().string();
                    bannerDataRes = gson.fromJson(string, BannerDataRes.class);
                    msg.addAll(bannerDataRes.getMsg());
                    Log.d("response", "轮播图onResponse: ------------------------->" + msg.size());
                    //放图片地址的集合
                    list_path = new ArrayList<>();
                    for (int i = 0; i < msg.size(); i++) {
                        String apicurl = msg.get(i).getApicurl();
                        Log.d("response", "轮播图msg: ------------------------->" + apicurl);
                        list_path.add(msg.get(i).getApicurl());
                    }
                    Log.d("response", "轮播图list_path: ------------------------->" + list_path.size());
                    Message msg = handler.obtainMessage();
                    msg.what = BANNER_DATA;
                    handler.sendMessage(msg);
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
        EventBus.getDefault().unregister(this);//取消注册
    }

    @OnClick(R.id.more2)
    public void onViewClicked() {
        Intent intent=new Intent(getActivity(),NearbyMoreActivity.class);
        intent.putExtra("NearbyFragment","2");
        startActivity(intent);
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast(getActivity(), "点击了条目" + position);
    }
}
