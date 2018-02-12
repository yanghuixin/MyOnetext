package com.example.administrator.myonetext.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.MainActivity;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.NearbyProductAdapter;
import com.example.administrator.myonetext.adapter.NearbyStroeAdapter;
import com.example.administrator.myonetext.bean.BannerDataRes;
import com.example.administrator.myonetext.bean.LALMessageEvent;
import com.example.administrator.myonetext.bean.NearbyProductDataRes;
import com.example.administrator.myonetext.bean.NearbyStoreDataRes;
import com.example.administrator.myonetext.dialog.HuiRedDialog;
import com.example.administrator.myonetext.dialog.ListDialog;
import com.example.administrator.myonetext.fragment.HomeFragment;
import com.example.administrator.myonetext.myview.MyListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
//首页吃美食，茶市场，买买买的二级页面
public class ETBWActivity extends AppCompatActivity implements OnBannerListener {
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
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_search)
    EditText search;
    @BindView(R.id.voice)
    ImageView voice;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    private int pagei = 1;
    private List<NearbyStoreDataRes.MsgBean> storData = new ArrayList<>();//店铺
    private NearbyStroeAdapter adapter;//店铺
    private NearbyProductAdapter adapter2;
    private ArrayList<String> list_path;
    private List<BannerDataRes.MsgBean> msg = new ArrayList<BannerDataRes.MsgBean>();
    private BannerDataRes bannerDataRes;
    private NearbyStoreDataRes nearbyStoreDataRes;
    private NearbyProductDataRes nearbyProductDataRes;
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
                    storData.clear();
                    storData.addAll(nearbyStoreDataRes.getMsg());
                    adapter.notifyDataSetChanged();
                    break;
                case BANNER_DATA:
                    initMyBanner(banner);
                    break;
                case INIT_PRODUCT_DATE:
                    productData.addAll(nearbyProductDataRes.getMsg());
                    adapter2.notifyDataSetChanged();
                    break;
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
            }
        }
    };
    private String jdpt;
    private String wdpt;
    private List<NearbyProductDataRes.MsgBean> productData = new ArrayList<>();
    private String type, type2;
    private int page = 1;
    private static String TAG = MainActivity.class.getSimpleName();
    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;//在线引擎TYPE_CLOUD
    int ret = 0; // 函数调用返回值
    private boolean mTranslateEnable = false;
    private HuiRedDialog huiRedDialog;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.open:
                    Intent intent = new Intent(getApplicationContext(), HuiRedActivity.class);
                    startActivity(intent);
                    huiRedDialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etbw);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initOnCreateXunfei();
        Intent intent = getIntent();
        type = intent.getStringExtra("ETBWActivity");
        initBannerData();
        initstorData(page, type);
        inittype2();
        initSmartRefresh();
        adapter = new NearbyStroeAdapter(this, storData);
        listView1.setAdapter(adapter);
        UIHelper.setListViewHeightBasedOnChildren(listView1);
        adapter2 = new NearbyProductAdapter(this, productData);
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
    }

    private void inittype2() {
        switch (type) {
            case "ms":
                type2 = "1758";
                initProductData(pagei, type2);
                break;
            case "cjy":
                type2 = "cjy";
                initProductData(pagei, type2);
                break;
            case "mmm":
                type2 = "mmm";
                initProductData(pagei, type2);
                break;
        }
    }

    private void initSmartRefresh() {
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initstorData(page, type);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                inittype2();
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

    private void initstorData(int page, String type) {
        String nearbyMs = "http://app.tealg.com/api/app/Business.ashx?flag=typebusiness&page=" + page + "&pageSize=4&pcid=0&type=" + type;
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
                    nearbyStoreDataRes = gson.fromJson(string, NearbyStoreDataRes.class);
                    Message message = new Message();
                    message.what = INIT_STOR_DATE;
                    handler.sendMessage(message);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void initProductData(int pagei, String type2) {
        //  String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=fjProduct&page=1&pageSize=4&rsKey=8489557DE1881279D8C45A79E57F3FADC153AFF72CA2EE1FF292AFD3E4F4E7B1&pcid=0&type=1758;
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=typeproduct&page=" + pagei + "&pageSize=6&pcid=0&type=" + type2;
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
                    nearbyProductDataRes = gson.fromJson(string, NearbyProductDataRes.class);
                    Message message = new Message();
                    message.what = INIT_PRODUCT_DATE;
                    handler.sendMessage(message);
                } else {
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

    private void initBannerData() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://app.tealg.com/api/app/Adv.ashx?flag=onehuodong")
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);//取消注册
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }


    @OnClick(R.id.more2)
    public void onViewClicked() {
        Intent intent = new Intent(this, NearbyMoreActivity.class);
        intent.putExtra("NearbyFragment", "2");
        startActivity(intent);
    }

    //轮播图的监听方法

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast(this, "点击了条目" + position);
    }

    @OnClick({R.id.re_clickBack, R.id.voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.voice:
                initMyXunfei(voice);
                break;
        }
    }
    private void initOnCreateXunfei() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(getApplicationContext(), mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);

        mSharedPreferences = getApplicationContext().getSharedPreferences("content",
                Context.MODE_PRIVATE);
        mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
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
                FlowerCollector.onEvent(getApplicationContext(), "iat_recognize");
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
    public void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(getApplicationContext());
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    public void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(getApplicationContext());
        super.onPause();
    }


}
