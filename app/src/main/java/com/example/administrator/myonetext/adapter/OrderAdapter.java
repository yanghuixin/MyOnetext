package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.ShoppingCartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12.
 */

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartBean> orderBean;

    public OrderAdapter(Context context, List<ShoppingCartBean> orderBean) {
        this.context = context;
        this.orderBean = orderBean;
    }

    @Override
    public int getCount() {
        return orderBean.size() == 0 ? 0 : orderBean.size();
    }

    @Override
    public Object getItem(int position) {
        return orderBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orderitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(orderBean.get(position).getShoppingName());
        holder.storename.setText(orderBean.get(position).getStorName());
        Glide.with(context).load(orderBean.get(position).getImageUrl()).into(holder.image);
        holder.price.setText(orderBean.get(position).getPrice() + "  x  " + orderBean.get(position).getCount());
        holder.DistributionFee.setText("配送费         0.00");
        return convertView;
    }

   static class ViewHolder {
        @BindView(R.id.storename)
        TextView storename;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.Distribution_fee)
        TextView DistributionFee;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
