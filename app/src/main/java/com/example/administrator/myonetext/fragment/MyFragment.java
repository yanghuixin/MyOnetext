package com.example.administrator.myonetext.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.activity.LoginActivity;
import com.example.administrator.myonetext.activity.MyCollectionActivity;
import com.example.administrator.myonetext.activity.MyHuiRedActivity;
import com.example.administrator.myonetext.activity.MyQRCodeActivity;
import com.example.administrator.myonetext.activity.MyReceiverAdressActivity;
import com.example.administrator.myonetext.activity.MySettingActivity;
import com.example.administrator.myonetext.activity.MyWallentActivity;
import com.example.administrator.myonetext.activity.MyWalletActivity;
import com.example.administrator.myonetext.activity.MyshopActivity;
import com.example.administrator.myonetext.activity.ShoppingCarActivity;
import com.example.administrator.myonetext.adapter.MyWalletAdapter;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.MyDataRes;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/24.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.tubiao1)
    ImageView tubiao1;
    @BindView(R.id.myshop)
    RelativeLayout myshop;
    @BindView(R.id.tubiao4)
    ImageView tubiao4;
    @BindView(R.id.myQRCode)
    RelativeLayout myQRCode;
    @BindView(R.id.tubiao5)
    ImageView tubiao5;
    @BindView(R.id.myWallent)
    RelativeLayout myWallent;
    @BindView(R.id.tubiao6)
    ImageView tubiao6;
    @BindView(R.id.myCollection)
    RelativeLayout myCollection;
    @BindView(R.id.tubiao7)
    ImageView tubiao7;
    @BindView(R.id.myShoppingCar)
    RelativeLayout myShoppingCar;
    @BindView(R.id.tubiao8)
    ImageView tubiao8;
    @BindView(R.id.myOrder)
    RelativeLayout myOrder;
    @BindView(R.id.tubiao9)
    ImageView tubiao9;
    @BindView(R.id.myReceiverAdress)
    RelativeLayout myReceiverAdress;
    @BindView(R.id.tubiao10)
    ImageView tubiao10;
    @BindView(R.id.myHuiRed)
    RelativeLayout myHuiRed;
    @BindView(R.id.tubiao3)
    ImageView tubiao3;
    @BindView(R.id.myWallet)
    RelativeLayout myWallet;
    @BindView(R.id.tubiao12)
    ImageView tubiao12;
    @BindView(R.id.mySetting)
    RelativeLayout mySetting;
    Unbinder unbinder;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    private MyDataRes myDataRes;
    private String uname;
    private String upicurl;
    private final static int INIT_STOR_DATE = 3;//产品
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                   Glide.with(getActivity()).load(upicurl).into(head);
                    name.setText(uname);
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
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (GouhuiUser.getInstance().hasUserInfo(getActivity())){
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(getActivity());
            String uid = userInfo.getUid();
            int pid = Integer.parseInt(uid);
            initData(pid);
        }else {
            ToastUtils.showToast(getActivity(),"您还未登录");
        }
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                OrderFragment orderFragment = new OrderFragment();
                fragmentTransaction.replace(R.id.fragment, orderFragment);//R.id.fragment是Activity1中的FrameLayout
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void initData(int uid) {
        String nearbyMs = "http://app.tealg.com/api/app/showmember.aspx?uid=" + uid + "&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    myDataRes = gson.fromJson(string, MyDataRes.class);
                    uname = myDataRes.getUname();
                    upicurl = myDataRes.getUpicurl();
                    String dlno = myDataRes.getDlno();
                    GouhuiUser.getInstance().saveMyInfo(getActivity(),dlno,upicurl);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myshop, R.id.myQRCode, R.id.myWallent, R.id.myCollection, R.id.myShoppingCar, R.id.myReceiverAdress, R.id.myHuiRed, R.id.myWallet, R.id.mySetting})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        if (GouhuiUser.getInstance().hasUserInfo(getActivity())) {
            switch (view.getId()) {
                case R.id.myshop:
                    intent.setClass(getActivity(),MyshopActivity.class);
                    break;
                case R.id.myQRCode:
                    intent.setClass(getActivity(),MyQRCodeActivity.class);
                    break;
                case R.id.myWallent:
                    intent.setClass(getActivity(),MyWallentActivity.class);
                    break;
                case R.id.myCollection:
                    intent.setClass(getActivity(),MyCollectionActivity.class);
                    break;
                case R.id.myShoppingCar://1.	商家更多链接到秒杀栏下面商家列表更多。
                    // 2.	更多链接到首页上面大分类栏更多。
                    intent.setClass(getActivity(),ShoppingCarActivity.class);
                    break;
                case R.id.myReceiverAdress:
                    intent.setClass(getActivity(),MyReceiverAdressActivity.class);
                    break;
                case R.id.myHuiRed:
                    intent.setClass(getActivity(),MyHuiRedActivity.class);
                    break;
                case R.id.myWallet://我的银行卡
                    intent.setClass(getActivity(),MyWalletActivity.class);
                    break;
                case R.id.mySetting:
                    intent.setClass(getActivity(),MySettingActivity.class);
                    break;
            }
        } else {
            intent.setClass(getActivity(),LoginActivity.class);
        }
        startActivity(intent);
    }
}
