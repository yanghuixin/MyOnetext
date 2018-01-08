package com.example.administrator.myonetext.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.bunny.view.TimeCountView;
import com.example.administrator.myonetext.MainActivity;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.activity.MapActivity;
import com.example.administrator.myonetext.activity.SearchActivity;
import com.example.administrator.myonetext.adapter.HorizontallistAdapter;
import com.example.administrator.myonetext.adapter.OneAdapter;
import com.example.administrator.myonetext.adapter.TwoAdapter;
import com.example.administrator.myonetext.bean.BannerDataRes;
import com.example.administrator.myonetext.bean.OneDataRes;
import com.example.administrator.myonetext.bean.ProductDataRes;
import com.example.administrator.myonetext.bean.TodayDataRes;
import com.example.administrator.myonetext.dialog.ListDialog;
import com.example.administrator.myonetext.myview.HorizontalListView;
import com.example.administrator.myonetext.myview.MyListView;
import com.example.administrator.myonetext.myview.MyScrollView;
import com.example.administrator.myonetext.utils.JsonParser;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.example.administrator.myonetext.utils.UIHelper;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.scwang.smartrefresh.header.MaterialHeader;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class HomeFragment extends BaseFragment implements OnBannerListener, View.OnClickListener {
    private int pagei = 1;
    private Button smsnum;
    private ImageView sms;
    private ImageView map;
    private TimeCountView hour;
    private TextView more;
    private MyScrollView scrollView;
    private LinearLayout ly_head;
    private MyListView list2;
    private Banner banner;
    private Banner banner2;
    private EditText search;
    private TextView more2,whole,eatfood,tea_market,buybuybuy,hui_red;
    private HorizontalListView horizontallist;
    private RefreshLayout refreshLayout;
    //    private ImageView increase;
    private Button sure;
    private ImageView voice;
    private ListView list1;
    private ListDialog listDialog;//自定义dialog
    private List<BannerDataRes.MsgBean> msg = new ArrayList<BannerDataRes.MsgBean>();
    private BannerDataRes bannerDataRes;
    private ArrayList<String> list_path;
    private OneAdapter adapter1;//商铺
    private TwoAdapter adapter2;//商品
    private List<OneDataRes.MsgBean> storData = new ArrayList<>();////商铺
    private OneDataRes storDataRes;//商铺
    private List<TodayDataRes.MsgBean> todayData = new ArrayList<>();////今日秒杀
    private HorizontallistAdapter adapter;//今日秒杀
    private List<ProductDataRes.MsgBean> productData = new ArrayList<>();//产品
    private final static int BANNER_DATA = 1;//首页轮播图
    private final static int TODAY_DATA = 2;//今日秒杀
    private final static int STOR_DATA = 3;//商铺
    private final static int BANNER_DATA2 = 4;//广告轮播图
    private final static int PRODUCT_DATA = 5;//产品
    private final static int SERVER_EXCEPTION = 6;//服务器异常
    private final static int NETWORK_ANOMALY = 7;//网络异常
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BANNER_DATA:
                    initMyBanner(banner);
                    break;
                case TODAY_DATA:
                    horizontallist.setAdapter(adapter);
                    break;
                case STOR_DATA:
                    adapter1.notifyDataSetChanged();
                    break;
                case BANNER_DATA2:
                    initMyBanner(banner2);
                    break;
                case PRODUCT_DATA:
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
    /**
     * 讯飞
     *
     * @param savedInstanceState
     */
    private static String TAG = MainActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;//在线引擎TYPE_CLOUD
    int ret = 0; // 函数调用返回值
    private boolean mTranslateEnable = false;


    @Override
    @SuppressLint("ShowToast")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);
        initView(view);
        initSmartRefresh(view);
        initBannerData();
        initTodayData();//今日秒杀
        initStorData();
        initBanner2Data();
        initProductData(pagei);
        initHour();//秒杀时间
        initListener();
        initOnCreateXunfei();
        /*list1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //list2.getRefreshableView()
        list2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

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
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                initProductData(pagei);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initHour() {
        hour.initTime(getActivity(), "010218");//"000018" means time "02:02:18"
    }

    private void initListener() {
        sms.setOnClickListener(this);
        map.setOnClickListener(this);
        more.setOnClickListener(this);
        more2.setOnClickListener(this);
        voice.setOnClickListener(this);
        //increase.setOnClickListener(this);
        sure.setOnClickListener(this);
        hui_red.setOnClickListener(this);
        buybuybuy.setOnClickListener(this);
        hui_red.setOnClickListener(this);
        whole.setOnClickListener(this);
        tea_market.setOnClickListener(this);
        eatfood.setOnClickListener(this);
    }

    private void initView(View view) {
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        smsnum = (Button) view.findViewById(R.id.smsnum);//信息数目
        map = (ImageView) view.findViewById(R.id.iv_location);
        sms = (ImageView) view.findViewById(R.id.sms);
        hour = (TimeCountView) view.findViewById(R.id.hour);
        more = (TextView) view.findViewById(R.id.more);
        whole = (TextView) view.findViewById(R.id.whole);
        eatfood = (TextView) view.findViewById(R.id.eatfood);
        tea_market = (TextView) view.findViewById(R.id.tea_market);
        buybuybuy = (TextView) view.findViewById(R.id.buybuybuy);
        hui_red = (TextView) view.findViewById(R.id.hui_red);
        scrollView = (MyScrollView) view.findViewById(R.id.scrollView);
        ly_head = (LinearLayout) view.findViewById(R.id.ly_head);
        list2 = (MyListView) view.findViewById(R.id.list2);
        banner = (Banner) view.findViewById(R.id.banner);
        banner2 = (Banner) view.findViewById(R.id.banner2);
        search = (EditText) view.findViewById(R.id.et_search);
        more2 = (TextView) view.findViewById(R.id.more2);
        horizontallist = (HorizontalListView) view.findViewById(R.id.a);
        voice = (ImageView) view.findViewById(R.id.voice);
        //increase = (ImageView) view.findViewById(R.id.increase);
        sure = (Button) view.findViewById(R.id.sure);
        list1 = (ListView) view.findViewById(R.id.list1);
        adapter1 = new OneAdapter(getActivity(), storData);
        list1.setAdapter(adapter1);
        UIHelper.setListViewHeightBasedOnChildren(list1);
        adapter2 = new TwoAdapter(getActivity(), productData);
        list2.setAdapter(adapter2);
        UIHelper.setListViewHeightBasedOnChildren(list2);
    }

    private void initOnCreateXunfei() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(getActivity(), mInitListener);

        mSharedPreferences = getActivity().getSharedPreferences("content",
                Context.MODE_PRIVATE);
        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
    }

    private void initBanner2Data() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String pathBanner = "http://app.tealg.com/api/app/Adv.ashx?flag=onelunbo";
        Request.Builder requestBuilder = new Request.Builder().url(pathBanner);
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
                    Log.i("response", "onResponse: ------------------>" + response);
                    String s = response.body().string();

                    bannerDataRes = gson.fromJson(s, BannerDataRes.class);
                    msg.clear();
                    msg.addAll(HomeFragment.this.bannerDataRes.getMsg());
                    //放图片地址的集合
                    list_path = new ArrayList<>();
                    for (int i = 0; i < msg.size(); i++) {
                        list_path.add(msg.get(i).getApicurl());
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = BANNER_DATA2;
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

    private void initProductData(int pagei) {
        String pathToday = "http://app.tealg.com/api/app/Product.ashx?flag=indexProduct&&page=" + pagei + "&&pageSize=6&&pcid=0";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(pathToday);
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
                    ProductDataRes productDataRes = gson.fromJson(string, ProductDataRes.class);
                    productData.addAll(productDataRes.getMsg());
                    Log.d("response", "onResponse: ------------------------>" + productData.size());
                    Message msg = new Message();
                    msg.what = PRODUCT_DATA;
                    handler.sendMessage(msg);
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

    private void initTodayData() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://app.tealg.com/api/app/Product.ashx?flag=indexProSale&&page=1&&pageSize=6&&pcid=0")
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
                    String string = response.body().string();
                    TodayDataRes todayDataRes = gson.fromJson(string, TodayDataRes.class);
                    todayData.clear();
                    todayData.addAll(todayDataRes.getMsg());
                    Log.d("request", "onResponse:--------------------------------> " + todayData.size());
                    adapter = new HorizontallistAdapter(getActivity(), todayData);
                    Message msg = handler.obtainMessage();
                    msg.what = TODAY_DATA;
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

    private void initStorData() {
        String path1 = "http://app.tealg.com/api/app/Business.ashx?flag=indexbusiness&&page=1&&pageSize=4&&pageno=1&&pcid=0";
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
                    Log.d("string", "商铺string------------------------>" + string);
                    storDataRes = gson.fromJson(string, OneDataRes.class);
                    if (storDataRes.getStatus()==0){
                        return;
                    }else {
                        storData.clear();
                        storData.addAll(storDataRes.getMsg());
                        Message msg = handler.obtainMessage();
                        msg.what = STOR_DATA;
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

    }

    private void initBannerData() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://app.tealg.com/api/app/Adv.ashx?flag=onelunbo")
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
                    Log.d("response", "onResponse: ------------------------->" + response);
                    String string = response.body().string();
                    bannerDataRes = gson.fromJson(string, BannerDataRes.class);
                    msg.clear();
                    msg.addAll(bannerDataRes.getMsg());
                    Log.d("response", "onResponse: ------------------------->" + msg.size());
                    //放图片地址的集合
                    list_path = new ArrayList<>();
                    for (int i = 0; i < msg.size(); i++) {
                        String apicurl = msg.get(i).getApicurl();
                        Log.d("response", "msg: ------------------------->" + apicurl);
                        list_path.add(msg.get(i).getApicurl());
                    }
                    Log.d("response", "list_path: ------------------------->" + list_path.size());
                    //// TODO: 2017/12/20
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

    private void initMyBanner(Banner banner) {
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
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

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast(getActivity(), "点击了条目" + position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch (v.getId()) {
            case R.id.iv_location:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                break;
            case R.id.sms:


                break;
            case R.id.more:


                break;
            case R.id.more2:


                break;
            case R.id.voice:
                initMyXunfei(voice);
                break;
//            case R.id.increase:
//                   showListDialog();
//
//                break;
            case R.id.sure://
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                String s = search.getText().toString().trim();
                intent1.putExtra("keyword", s);
                startActivity(intent1);
                break;
            case R.id.eatfood:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                OneNearbyFragment oneNearbyFragment = new OneNearbyFragment();
                fragmentTransaction.replace(R.id.fragment, oneNearbyFragment);
                fragmentTransaction.commit();
                break;
            case R.id.tea_market:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                TwoNearbyFragment twoNearbyFragment = new TwoNearbyFragment();
                fragmentTransaction.replace(R.id.fragment, twoNearbyFragment);
                fragmentTransaction.commit();

                break;
            case R.id.buybuybuy:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ThreeNearbyFragment threeNearbyFragment = new ThreeNearbyFragment();
                fragmentTransaction.replace(R.id.fragment, threeNearbyFragment);
                fragmentTransaction.commit();

                break;
            case R.id.hui_red:

                break;
            case R.id.whole:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FourNearbyFragment fourNearbyFragment = new FourNearbyFragment();
                fragmentTransaction.replace(R.id.fragment, fourNearbyFragment);
                fragmentTransaction.commit();

                break;
        }
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

    private void initMyXunfei(View view) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        switch (view.getId()) {
            // 开始听写
            // 如何判断一次听写结束：OnResult isLast=true 或者 onError
            case R.id.voice://语音
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(getActivity(), "iat_recognize");
                search.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                    showTip(getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：" + ret);
                    } else {
                        showTip(getString(R.string.text_begin));
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                printResult(results);
            }

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        search.setText(resultBuffer.toString());
        search.setSelection(search.length());
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                printResult(results);
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = mSharedPreferences.getBoolean(this.getString(R.string.pref_key_translate), false);
        if (mTranslateEnable) {
            Log.i(TAG, "translate enable");
            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
        }

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    private void printTransResult(RecognizerResult results) {
        String trans = JsonParser.parseTransResult(results.getResultString(), "dst");
        String oris = JsonParser.parseTransResult(results.getResultString(), "src");

        if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
            showTip("解析结果失败，请确认是否已开通翻译功能。");
        } else {
            search.setText("原始语言:\n" + oris + "\n目标语言:\n" + trans);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    @Override
    public void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(getActivity());
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    public void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(getActivity());
        super.onPause();
    }

    /**
     * 设置ListDialog显示位置及点击事件
     */
    private void showListDialog() {
        listDialog = new ListDialog(getContext(), new String[]{"开发票", "付款码", "看电影", "骑单车"}, new ListDialog.OnListDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtils.showToast(getContext(), "用户点击了开发票");
                        listDialog.dismiss();
                        break;
                    case 1:
                        ToastUtils.showToast(getContext(), "用户点击了付款码");
                        listDialog.dismiss();
                        break;
                    case 2:
                        ToastUtils.showToast(getContext(), "用户点击了看电影");
                        listDialog.dismiss();
                        break;
                    case 3:
                        ToastUtils.showToast(getContext(), "用户点击了骑单车");
                        listDialog.dismiss();
                        break;
                }
            }
        });
        Window dialogWindow = listDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
        lp.x = 40; // 新位置X坐标
        lp.y = 40; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        dialogWindow.setAttributes(lp);
        listDialog.show();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        //销毁fragment前判断自定义dialog是否消失，若未消失，手动设置dialog消失
        if (listDialog.isShowing()) {
            listDialog.dismiss();
        }
        Glide.with(this).pauseRequests();
    }
}