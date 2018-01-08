package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.PPaymoneyOrdeDataRes;
import com.example.administrator.myonetext.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/8.
 */

public class RAndCSOrderAdapter extends BaseAdapter {
    private Context context;
    private List<PPaymoneyOrdeDataRes.MsgBean> msg;
    private List<PPaymoneyOrdeDataRes.MsgBean.OrdersMsgBean.OrdersProductsBean> ordersProducts = new ArrayList<>();

    public RAndCSOrderAdapter(Context context, List<PPaymoneyOrdeDataRes.MsgBean> msg) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.randcsorder_largeitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.orderNum.setText("订单号 : " + msg.get(position).getOrderNumber());
        viewHolder.shopName.setText(msg.get(position).getOrdersMsg().get(0).getOrdersBusinessName());
        viewHolder.threeAll.setText("共3件商品   实付" + msg.get(position).getOrderPirce() + "     红包：" + msg.get(position).getOrderPayInt());
        if (msg.get(position).getOrderState()==6){
            viewHolder.Completed.setText("已完成");
        }else if (msg.get(position).getOrderState()==4){
            viewHolder.Completed.setText("取消订单");
        }else {
            viewHolder.Completed.setText("未完成");
        }
        ordersProducts.addAll(msg.get(position).getOrdersMsg().get(0).getOrdersProducts());
        ItemlistAdapter itemlistadapter = new ItemlistAdapter(context, ordersProducts);
        viewHolder.productlistview.setAdapter(itemlistadapter);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.Completed)
        Button Completed;
        @BindView(R.id.orderNum)
        TextView orderNum;
        @BindView(R.id.shopName)
        TextView shopName;
        @BindView(R.id.productlistview)
        MyListView productlistview;
        @BindView(R.id.threeAll)
        TextView threeAll;
        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
