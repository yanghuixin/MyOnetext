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
import com.example.administrator.myonetext.bean.PPaymoneyOrdeDataRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/3.
 */

public class ItemlistAdapter extends BaseAdapter {
    private Context context;
    private List<PPaymoneyOrdeDataRes.MsgBean.OrdersMsgBean.OrdersProductsBean> ordersProducts;

    public ItemlistAdapter(Context context, List<PPaymoneyOrdeDataRes.MsgBean.OrdersMsgBean.OrdersProductsBean> ordersProducts) {
        this.context = context;
        this.ordersProducts = ordersProducts;
    }

    @Override
    public int getCount() {
        return ordersProducts.size() == 0 ? 0 : ordersProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.paymoneyorde_smallitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }
        holder = (ViewHolder) convertView.getTag();
        holder.smallitemName.setText(ordersProducts.get(position).getProductName());
        holder.smallitemTextMoney.setText("￥" + ordersProducts.get(position).getProductPrice() + "      x" + ordersProducts.get(position).getProductNumber());
        Glide.with(context).load(ordersProducts.get(position).getProductPic()).into(holder.smallitemImage);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.smallitemImage)
        ImageView smallitemImage;
        @BindView(R.id.smallitemName)
        TextView smallitemName;
        @BindView(R.id.smallitemTextMoney)
        TextView smallitemTextMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
