package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.OrderAdapter;
import com.example.administrator.myonetext.bean.DefaultAdressDataRes;
import com.example.administrator.myonetext.bean.GetDefaultAdressDataRes;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.ShoppingCartBean;
import com.example.administrator.myonetext.bean.UserInfo;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitOrderActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.adress)
    TextView adress;
    @BindView(R.id.Distribution)
    RadioButton Distribution;
    @BindView(R.id.toStore)
    RadioButton toStore;
    @BindView(R.id.tabs)
    RadioGroup tabs;
    @BindView(R.id.Now_arrived)
    RadioButton NowArrived;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.my_order)
    ListView myOrder;
    @BindView(R.id.payM)
    TextView payM;
    @BindView(R.id.submit_order)
    Button submitOrder;
    @BindView(R.id.Table_number)
    EditText TableNumber;
private OrderAdapter adapter;
    private GetDefaultAdressDataRes getDefaultAdressDataRes;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
                    Log.d("qqq", "handleMessage: -------->"+getDefaultAdressDataRes.getMsg()+"  >");
                    adress.setText( getDefaultAdressDataRes.getMsg());
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        ButterKnife.bind(this);
        if (GouhuiUser.getInstance().hasUserInfo(this)){
            UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(this);
            String uid = userInfo.getUid();
            int pid = Integer.parseInt(uid);
            initDefaultAdress(pid);
        }else {
            ToastUtils.showToast(this,"您还未登录");
        }
        Intent intent = getIntent();
        String totalPrice = intent.getStringExtra("totalPrice");
        List<ShoppingCartBean> orderBean = (List<ShoppingCartBean>) intent.getSerializableExtra("orderBean");
       initpayM(totalPrice);
        adapter=new OrderAdapter(this,orderBean);
        myOrder.setAdapter(adapter);
    }

    private void initpayM(String totalPrice) {
        payM.setText(totalPrice);
    }

    private void initDefaultAdress(int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=showdefaultadd&pid="+pid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    try{
                        JsonElement je = new JsonParser().parse(string);
                        if (je.getAsJsonObject().get("status").getAsInt() == 0){
                            return;
                        }else {
                            getDefaultAdressDataRes = gson.fromJson(string, GetDefaultAdressDataRes.class);
                            Log.d("qqq", "handleMessage:getDefaultAdressDataRes -------->"+getDefaultAdressDataRes.getStatus());
                            Message message = new Message();
                            message.what = INIT_STOR_DATE;
                            handler.sendMessage(message);
                        }
                    }catch (Exception e){
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

    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.adress, R.id.Distribution, R.id.toStore, R.id.Now_arrived, R.id.submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.adress:
                Intent intent = new Intent();
                intent.setClass(this, MyReceiverAdressActivity.class);
                startActivity(intent);
                break;
            case R.id.Distribution:
                TableNumber.setVisibility(View.GONE);
                break;
            case R.id.toStore:
                TableNumber.setVisibility(View.VISIBLE);
                break;
            case R.id.Now_arrived://抵现

                break;
            case R.id.submit_order://提交订单
//                Intent intent1 = new Intent();
//                intent1.setClass(this, .class);
//                startActivity(intent1);
                break;
        }
    }
}
