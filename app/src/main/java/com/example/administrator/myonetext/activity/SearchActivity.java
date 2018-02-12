package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.SearchAdapter;
import com.example.administrator.myonetext.adapter.SearchSPAdapter;
import com.example.administrator.myonetext.adapter.SearchSandPAdapter;
import com.example.administrator.myonetext.adapter.SearchStoreAdapter;
import com.example.administrator.myonetext.bean.SearchBean;
import com.example.administrator.myonetext.bean.SearchSPBean;
import com.example.administrator.myonetext.bean.SearchStoreBean;
import com.example.administrator.myonetext.myview.MyListView;
import com.example.administrator.myonetext.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
import butterknife.OnClick;

//搜索的页面,目前只有产品，后期自己添加店铺的
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.morelist)
    MyListView morelist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.storelist)
    MyListView storelist;
    private List<SearchBean.CpmsgBean> cpmsg = new ArrayList<SearchBean.CpmsgBean>();//只有商品的数据源
    private SearchAdapter adapter;
    private int page = 1;
    private final static int SEARCHSP_DATA=3;//搜到店铺,产品
    private final static int SEARSTORE_DATA=4;//只搜到店铺
    private final static int PRODUCT_DATA = 5;//只搜到产品
    private final static int SERVER_EXCEPTION = 6;//服务器异常
    private final static int NETWORK_ANOMALY = 7;//网络异常
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case PRODUCT_DATA://只搜到产品
                    adapter.notifyDataSetChanged();
                    morelist.setAdapter(adapter);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case SEARSTORE_DATA://只搜到店铺
                    searchstoreadapter.notifyDataSetChanged();
                    storelist.setAdapter(searchstoreadapter);
                    break;
                case SEARCHSP_DATA:////搜到店铺,产品
                    spAdaptercpmsg1.notifyDataSetChanged();
                    spAdaptersjmsg1.notifyDataSetChanged();
                    morelist.setAdapter(spAdaptercpmsg1);
                    storelist.setAdapter(spAdaptersjmsg1);
                    break;
            }
        }
    };
    private List<SearchStoreBean.SjmsgBean> sjmsg = new ArrayList<>();
    private List<SearchSPBean.SjmsgBean> sjmsg1 = new ArrayList<>();
    private List<SearchSPBean.CpmsgBean> cpmsg1 = new ArrayList<>();
    private SearchStoreAdapter searchstoreadapter;
private SearchSPAdapter spAdaptercpmsg1;
    private SearchSandPAdapter spAdaptersjmsg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        Log.d("cpmsg", "onCreate: ------->"+keyword);
        initData(keyword, page);
        adapter = new SearchAdapter(this, cpmsg);
//        morelist.setAdapter(adapter);
        searchstoreadapter = new SearchStoreAdapter(this, sjmsg);
//        storelist.setAdapter(searchstoreadapter);
        spAdaptercpmsg1=new SearchSPAdapter(this,cpmsg1);
        spAdaptersjmsg1=new SearchSandPAdapter(this,sjmsg1);
    }
    private void initData(String keyword, int page) {
        String path = "http://app.tealg.com/api/app/Product.ashx?flag=Search&&keyword=" + keyword + "&&page=" + page + "&&pageSize=4";
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
                    try {
                        JsonElement je = new JsonParser().parse(string);
                        if (je.getAsJsonObject().get("Status").getAsString().equals("0")) {
                            Log.d("cpmsg", "Status:" + je.getAsJsonObject().get("Status").getAsString());
                            return;
                        } else {
                            if (!je.getAsJsonObject().get("sjmsg").isJsonArray()){
                                Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"sjmsg\").equals(\"\"))"+je.getAsJsonObject().get("sjmsg").equals(""));
                                Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"sjmsg\").equals(\"\"))");
                                SearchBean searchProductBean = gson.fromJson(string, SearchBean.class);
                                cpmsg.addAll(searchProductBean.getCpmsg());
                                Message msg = new Message();
                                msg.what = PRODUCT_DATA;
                                handler.sendMessage(msg);
                            }else {
                                if (!je.getAsJsonObject().get("cpmsg").isJsonArray()) {
                                    Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"cpmsg\").equals(\"\"))" + je.getAsJsonObject().get("cpmsg").equals(""));
                                    Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"cpmsg\").equals(\"\"))");
                                    SearchStoreBean searchStoreBean = gson.fromJson(string, SearchStoreBean.class);
                                    sjmsg.addAll(searchStoreBean.getSjmsg());
                                    Message msg = new Message();
                                    msg.what = SEARSTORE_DATA;
                                    handler.sendMessage(msg);
                                } else {
                                    Log.d("cpmsg", "onResponse: ------>都不为空 SearchSPBean.class");
                                    Log.d("cpmsg", "onResponse: ------> SearchSPBean.class");
                                    SearchSPBean searchSPBean = gson.fromJson(string, SearchSPBean.class);
                                    sjmsg1.addAll(searchSPBean.getSjmsg());
                                    cpmsg1.addAll(searchSPBean.getCpmsg());
                                    Message msg = new Message();
                                    msg.what = SEARCHSP_DATA;
                                    handler.sendMessage(msg);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //////////////////////////////////////////////////
//                    try {
//                        JsonElement je = new JsonParser().parse(string);
//                        if (je.getAsJsonObject().get("Status").equals("0")) {
//                            return;
//                        } else {
//                            Log.d("cpmsg", "onResponse: ------> else;");
////                                Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"sjmsg\").equals(\"\"))"+je.getAsJsonObject().get("sjmsg").equals(""));
////                                Log.d("cpmsg", "onResponse: ------> if (je.getAsJsonObject().get(\"sjmsg\").equals(\"\"))");
//                            AllSearchBean allSearchBean = gson.fromJson(string, AllSearchBean.class);
//                            Objects getCpmsg = allSearchBean.getCpmsg();
//                            Objects getSjmsg = allSearchBean.getSjmsg();
//                            Log.d("cpmsg", "onResponse: ------>  Objects getCpmsg = allSearchBean.getCpmsg();"+getCpmsg);
//                            Log.d("cpmsg", "onResponse: ------>  Objects getSjmsg = allSearchBean.getSjmsg();"+getSjmsg);
//                            Message msg = new Message();
//                                msg.what = PRODUCT_DATA;
//                                handler.sendMessage(msg);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = NETWORK_ANOMALY;
                    msg.obj = "无网络";
                    handler.sendMessage(msg);
                }
            }
        });
        page++;//可能需要换位置


    }

    @OnClick({R.id.re_clickBack, R.id.threespot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
        }
    }
}
