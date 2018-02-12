package com.example.administrator.myonetext.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.AreaAdapter;
import com.example.administrator.myonetext.adapter.MoreproductAdapter;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.MoreproductDataRes;
import com.example.administrator.myonetext.bean.ProductCollectionDataRes;
import com.example.administrator.myonetext.bean.ProductDetailsDataRes;
import com.example.administrator.myonetext.bean.ShoppingCartBean;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.dialog.ListDialog;
import com.example.administrator.myonetext.fragment.HomeFragment;
import com.example.administrator.myonetext.myview.HorizontalListView;
import com.example.administrator.myonetext.myview.MyScrollView;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
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

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//ProductDetailsReq
public class ProductDetailsActivity extends AppCompatActivity implements OnBannerListener {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.product_details_banner)
    Banner banner;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.surplus_number)
    TextView surplusNumber;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.reduce)
    ImageView reduce;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.increase)
    ImageView increase;
    @BindView(R.id.look_details)
    Button lookDetails;
    //    @BindView(R.id.moreProduce)
//    ProductHorizontalListView moreProduce;
    @BindView(R.id.shop)
    ImageView shop;
    @BindView(R.id.Collection)
    ImageView Collection;
    @BindView(R.id.add_to_car)
    Button addToCar;
    @BindView(R.id.Immediately_Order)
    Button ImmediatelyOrder;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.Customer_Service)
    ImageView CustomerService;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    private int proid;
    private int productNumber = 1;
    private ListDialog listDialog;//自定义dialog
    private MoreproductAdapter adapter;
    private MoreproductDataRes moreproductDataRes;
    private final static int INIT_MOREPRODUCT_DATE = 1;//逛逛
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int PRODUCT_COLLECTION_DATE = 3;//收藏产品
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_MOREPRODUCT_DATE:
                    moreproduct.clear();
                    moreproduct.addAll(moreproductDataRes.getMsg());
                    Log.d("moreproduct", "onResponse: ------------------------->" + moreproduct.size());
                    adapter = new MoreproductAdapter(getApplicationContext(), moreproduct);
                    adapter.notifyDataSetChanged();
                    moreProduce.setAdapter(adapter);
                    break;
                case INIT_STOR_DATE:
                    initMyBanner(picurl);
                    price.setText(productDetailsDataRes.getShopprice());
                    surplusNumber.setText("约剩余" + productDetailsDataRes.getIstock() + "    (" + productDetailsDataRes.getPpaoz() + ")");
                    name.setText(productDetailsDataRes.getPname());
                    integral.setText("可用积分：           " + productDetailsDataRes.getUsintegral());
                    String ptype1 = productDetailsDataRes.getPtype();
                    Log.d("ptype1", "onCreate:---------------------> " + ptype1);
                    if (productDetailsDataRes.getPtype().equals("")) {
                        radioGroup.setVisibility(View.GONE);
                    } else {
                        String ptype = productDetailsDataRes.getPtype();
                        String[] split = ptype.split(",");
                        radioGroup.setVisibility(View.INVISIBLE);
                        initsetVisibility(split);
                    }
                    break;
                case PRODUCT_COLLECTION_DATE:
                    ToastUtils.showToast(getApplicationContext(), "收藏成功");
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
    private ProductDetailsDataRes productDetailsDataRes;
    private String[] picurl;
    private ArrayList<String> list_path;
    private List<MoreproductDataRes.MsgBean> moreproduct = new ArrayList<>();
    private HorizontalListView moreProduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        proid = Integer.parseInt(pid);
        initproductData(proid);
        moreProduce = (HorizontalListView) findViewById(R.id.moreProduce);
        initMoreproductData(proid);
        moreProduce.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                proid = moreproduct.get(position).getPid();
                Log.d("pid1", "onItemClick: ------------------>" + proid);
                initproductData(proid);
                initMoreproductData(proid);
            }
        });
    }

    private void initsetVisibility(String[] split) {
        for (int i = 0; i < split.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            //设置RadioButton边距 (int left, int top, int right, int bottom)
            lp.setMargins(15, 0, 0, 0);
            //设置RadioButton背景
            //radioButton.setBackgroundResource(R.drawable.xx);
            //设置RadioButton的样式
//            radioButton.setButtonDrawable(R.drawable.radio_bg);
            //设置文字距离四周的距离
            radioButton.setPadding(80, 0, 0, 0);
            //设置文字
            radioButton.setText(split[i]);
            radioButton.setId(i);
            final int finalI = i;
            //设置radioButton的点击事件
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(getApplicationContext(), "this is radioButton  " + finalI);
                }
            });
            //将radioButton添加到radioGroup中
            radioGroup.addView(radioButton);
        }
        radioGroup.check(0);
    }

    private void initMoreproductData(int proid) {
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=youlove&proid=" + proid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    moreproductDataRes = gson.fromJson(string, MoreproductDataRes.class);
                    Message message = new Message();
                    message.what = INIT_MOREPRODUCT_DATE;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initproductData(int proid) {
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=getpromsg&proid=" + proid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    productDetailsDataRes = gson.fromJson(string, ProductDetailsDataRes.class);
                    String mypicurl = productDetailsDataRes.getPicurl();
                    picurl = mypicurl.split(";");

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

    private void initMyBanner(String[] picurl) {
        //放图片地址的集合
        list_path = new ArrayList<>();
        for (int i = 0; i < picurl.length; i++) {
            list_path.add(picurl[i]);
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

    //自定义的图片加载器
    public static class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (Util.isOnMainThread()) {
                Glide.with(context).load((String) path).into(imageView);
            }
        }
    }
    @OnClick({R.id.re_clickBack, R.id.reduce, R.id.increase, R.id.look_details, R.id.Customer_Service, R.id.shop, R.id.Collection, R.id.add_to_car, R.id.Immediately_Order})
    public void onViewClicked(View view) {
        int pid = productDetailsDataRes.getPid();
        String picurl = list_path.get(0);
        String pstorename = productDetailsDataRes.getPstorename();
        String pname = productDetailsDataRes.getPname();
        String shopprice = productDetailsDataRes.getShopprice();
        String s = number.getText().toString();
        List<ShoppingCartBean> shoppingCartBeen = DataSupport.where("productPid == ?", pid + "").find(ShoppingCartBean.class);
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.reduce://减少
                if (productNumber > 1) {
                    productNumber--;
                    number.setText(productNumber + "");
                }
                break;
            case R.id.increase://增加
                productNumber++;
                if (productDetailsDataRes.getBuymax() != 0) {
                    if (productNumber <= productDetailsDataRes.getBuymax()) {
                        number.setText(productNumber + "");
                    } else {
                        number.setText(productDetailsDataRes.getBuymax() + "");
                    }
                } else {
                    number.setText(productNumber + "");
                }
                break;
            case R.id.look_details:
                Intent intent = new Intent(getApplicationContext(), ProductLookdetails.class);
                intent.putExtra("proid1", productDetailsDataRes.getPid());
                startActivity(intent);
                break;
            case R.id.Customer_Service:
                Intent intent1 = new Intent();
                //设置意图要做的事，这里是打电话
                intent1.setAction(Intent.ACTION_CALL);
                //设置参数 Uri请求资源表示符
                intent1.setData(Uri.parse("tel:" + productDetailsDataRes.getPkfphone()));
                startActivity(intent1);
                break;
            case R.id.shop:
                Intent intent2 = new Intent();
//                Intent intent2 = new Intent(getApplicationContext(), StoredetailsActivity.class);
                intent2.setClass(getApplicationContext(), NewStoredetailsActivity.class);
                Log.d("productDetailsDataRes1", "onViewClicked: intent2-------------------->" + productDetailsDataRes.getIbid());
                intent2.putExtra("bid", productDetailsDataRes.getIbid() + "");
                startActivity(intent2);
                break;
            case R.id.Collection:
                int productid = productDetailsDataRes.getPid();
                if (GouhuiUser.getInstance().hasUserInfo(getApplicationContext())) {
                    UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(getApplicationContext());
                    String uid = userInfo.getUid();
                    int pid1 = Integer.parseInt(uid);
                    inintProductCollection(pid1, productid);
                } else {
                    ToastUtils.showToast(getApplicationContext(), "您还没有登录，请先登录");
                }
                break;
            case R.id.add_to_car:
                if (shoppingCartBeen != null&&shoppingCartBeen.size()>0) {
                    String count = shoppingCartBeen.get(0).getCount();
                    int i = Integer.parseInt(count)+1;
                    shoppingCartBeen.get(0).setCount(i+"");
                    shoppingCartBeen.get(0).update(shoppingCartBeen.get(0).getId());
                } else {
                    ShoppingCartBean myshoppingCartBeen= new ShoppingCartBean(pid, picurl, pstorename, pname, shopprice, s);
                    myshoppingCartBeen.save();
                }
                ToastUtils.showToast(this, "加入购物车成功");
                break;
            case R.id.Immediately_Order:
//                int pid = productDetailsDataRes.getPid();
//                String picurl = list_path.get(0);
//                String pstorename = productDetailsDataRes.getPstorename();
//                String pname = productDetailsDataRes.getPname();
//                String shopprice = productDetailsDataRes.getShopprice();
//                String s = number.getText().toString();
//                List<ShoppingCartBean> shoppingCartBeen = DataSupport.where("productPid == ?", pid + "").find(ShoppingCartBean.class);
                if (shoppingCartBeen != null&&shoppingCartBeen.size()>0) {
                    String count = shoppingCartBeen.get(0).getCount();
                    int i = Integer.parseInt(count)+1;
                    shoppingCartBeen.get(0).setCount(i+"");
                    shoppingCartBeen.get(0).update(shoppingCartBeen.get(0).getId());
                } else {
                    ShoppingCartBean myshoppingCartBeen= new ShoppingCartBean(pid, picurl, pstorename, pname, shopprice, s);
                    myshoppingCartBeen.save();
                }
                Intent intent3 = new Intent();
                intent3.setClass(this, ShoppingCarActivity.class);
                startActivity(intent3);
                //  showListDialog();
                break;
        }
    }

    private void showListDialog() {
        listDialog = new ListDialog(this, new String[]{"红包抵现", "分享好友"}, new ListDialog.OnListDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtils.showToast(getApplicationContext(), "用户点击了红包抵现");
                        listDialog.dismiss();
                        break;
                    case 1:
                        ToastUtils.showToast(getApplicationContext(), "用户点击了分享好友");
                        listDialog.dismiss();
                        break;
                }
            }
        });
        Window dialogWindow = listDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        lp.x = 40; // 新位置X坐标
        lp.y = 160; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        dialogWindow.setAttributes(lp);
        listDialog.show();
    }

    private void inintProductCollection(int pid1, int productid) {
        String nearbyMs = "http://app.tealg.com/api/app/Product.ashx?flag=Collection&pid=" + pid1 + "&productid=" + productid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    ProductCollectionDataRes productCollectionDataRes = gson.fromJson(string, ProductCollectionDataRes.class);
                    int state = productCollectionDataRes.getState();
                    if (state == 1) {
                        String coll = productCollectionDataRes.getColl();//该产品已被收藏的数量
                        Message message = new Message();
                        message.what = PRODUCT_COLLECTION_DATE;
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
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast(getApplicationContext(), "你点了第" + position + "张轮播图");
    }
}
