package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.activity.HuiRedActivity;
import com.example.administrator.myonetext.bean.OrderCancleDataRes;
import com.example.administrator.myonetext.bean.PPaymoneyOrdeDataRes;
import com.example.administrator.myonetext.bean.RedPackageDataRes;
import com.example.administrator.myonetext.myview.MyListView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

/**
 * Created by Administrator on 2018/1/3.
 */

public class PPaymoneyOrdeAdapter extends BaseAdapter {
private int pid=974;
    private String orderId2;
    private Context context;
    private List<PPaymoneyOrdeDataRes.MsgBean> msg;
    private List<PPaymoneyOrdeDataRes.MsgBean.OrdersMsgBean.OrdersProductsBean> ordersProducts = new ArrayList<>();
    private OrderCancleDataRes orderCancleDataRes;

    public PPaymoneyOrdeAdapter(Context context, List<PPaymoneyOrdeDataRes.MsgBean> msg) {
        this.context = context;
        this.msg = msg;
    }

    @Override
    public int getCount() {
        return msg.size() == 0 ? 0 : msg.size();
    }

    @Override
    public Object getItem(int position) {
        return msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.paymoneyorde_largeitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.orderNum.setText("订单号 : " + msg.get(position).getOrderNumber());
        Log.d("msg", "getView: ------------->"+msg.get(position).getOrderNumber());//getView: ------------->ZXB1801090003
        String orderNumber = msg.get(position).getOrderNumber();



        // TODO: 2018/1/10 获取子订单编号，问辛辛
       // orderId2= msg.get(position).getOrdersMsg().get("子订单序号").getOrdersNumber();
        viewHolder.shopName.setText(msg.get(position).getOrdersMsg().get(0).getOrdersBusinessName());
        viewHolder.threeAll.setText("共" + msg.get(position).getOrderState() + "件商品   实付" + msg.get(position).getOrderPirce() + "     红包：" + msg.get(position).getOrderPayInt());
        if (msg.get(position).getOrderState() == 6) {
            viewHolder.cancle.setText("申请退款");
            viewHolder.payMoney.setText("去评价");
        } else if (msg.get(position).getOrderState() == 3) {
            viewHolder.cancle.setText("取消");
            viewHolder.payMoney.setText("去支付");
        } else {
            viewHolder.cancle.setText("确认收货");
            viewHolder.payMoney.setText("取消订单");
        }
        ordersProducts.clear();
        ordersProducts.addAll(msg.get(position).getOrdersMsg().get(0).getOrdersProducts());
        ItemlistAdapter itemlistadapter = new ItemlistAdapter(context, ordersProducts);
        viewHolder.productlistview.setAdapter(itemlistadapter);
        initOnClick(viewHolder,orderNumber);
        return convertView;
    }

    private void initOnClick(final ViewHolder viewHolder, final String orderNumber) {
        viewHolder.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.cancle.getText().equals("申请退款")) {

                } else if (viewHolder.cancle.getText().equals("取消")) {
                    initData("orderCancel",pid,orderNumber);

                } else if (viewHolder.cancle.getText().equals("确认收货")) {
                    initData("orderOK",pid,orderId2);
                }
            }

            private void initData(String flag, int pid, String orderNumber) {
                String path = "http://app.tealg.com/api/app/OrderMsg.ashx?flag="+flag+"&pid="+pid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q&orderId="+orderNumber+"";
                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request.Builder requestBuilder = new Request.Builder().url(path);
                //可以省略，默认是GET请求
                requestBuilder.method("GET", null);
                final Request request = requestBuilder.build();
                final Call mcall = mOkHttpClient.newCall(request);
                mcall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.d("initCancleData", "服务器onFailure: ----------------------->"+ e.getMessage());
                    }
                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (null != response) {
                            Gson gson = new Gson();
                            String string = response.body().string();
                            try {
                                JsonElement je = new com.google.gson.JsonParser().parse(string);
                                if (je.getAsJsonObject().get("state").equals("0")){
                                    Log.d("initCancleData", "服务器onFailure: ----------------------->"+ orderCancleDataRes.getMessage());

                                    return;
                                }else {
                                    orderCancleDataRes = gson.fromJson(string, OrderCancleDataRes.class);
                                    if (orderCancleDataRes.getState() == 1) {
                                        Log.d("initCancleData", "服务器onSuccess: ----------------------->"+ orderCancleDataRes.getMessage());
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("initCancleData", "服务器onFailure: ----------------------->无网络");
                        }
                    }
                });



            }
        });
        viewHolder.payMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.payMoney.getText().equals("去评价")) {

                } else if (viewHolder.payMoney.getText().equals("去支付")) {

                } else if (viewHolder.payMoney.getText().equals("取消订单")) {

                }
            }
        });

    }

    static class ViewHolder {
        @BindView(R.id.orderNum)
        TextView orderNum;
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.productlistview)
        MyListView productlistview;
        @BindView(R.id.threeAll)
        TextView threeAll;
        @BindView(R.id.cancle)
        Button cancle;
        @BindView(R.id.payMoney)
        Button payMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
