package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.CollectionProductDataRes;
import com.example.administrator.myonetext.bean.ProductDataRes;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 */

public class CollectionProductAdapter extends BaseAdapter {

    private Context context;
    private List<CollectionProductDataRes.MessageBean> collectionProductDataMessageBean;

    public CollectionProductAdapter(Context context, List<CollectionProductDataRes.MessageBean> collectionProductDataMessageBean) {
        this.context = context;
        this.collectionProductDataMessageBean = collectionProductDataMessageBean;
    }

    @Override
    public int getCount() {
        Log.d("productData", "getCount: -------->"+collectionProductDataMessageBean.size());
        return collectionProductDataMessageBean.size() == 0 ? 0 : collectionProductDataMessageBean.size();
    }

    @Override
    public Object getItem(int position) {
        return collectionProductDataMessageBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_information, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_price.setText(collectionProductDataMessageBean.get(position).getShopprice() + "");
        holder.tv_commodity_name.setText(collectionProductDataMessageBean.get(position).getPname());
        holder.tv_comment.setText(collectionProductDataMessageBean.get(position).getIcommentcnt() + "条评论数");
       holder.tv_address.setText(collectionProductDataMessageBean.get(position).getBaddress());
        Glide.with(context).load(collectionProductDataMessageBean.get(position).getPicurl()).into(holder.iv_product_image);
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_product_image;
        private TextView tv_commodity_name;
        private TextView tv_comment;
        private TextView tv_consumption;
        private RelativeLayout rl_price;
        private TextView tv_price;
        private TextView tv_address;
        private TextView tv_distance;
        private TextView tv_char_introduction_one;
        private TextView tv_char_introduction_two;
        private TextView tv_char_introduction_three;
        private TextView tv_char_introduction_four;

        public ViewHolder(View view) {
            iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            tv_commodity_name = (TextView) view.findViewById(R.id.tv_commodity_name);
            tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            tv_consumption = (TextView) view.findViewById(R.id.tv_consumption);
            rl_price = (RelativeLayout) view.findViewById(R.id.rl_price);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            tv_char_introduction_one = (TextView) view.findViewById(R.id.tv_char_introduction_one);
            tv_char_introduction_two = (TextView) view.findViewById(R.id.tv_char_introduction_two);
            tv_char_introduction_three = (TextView) view.findViewById(R.id.tv_char_introduction_three);
            tv_char_introduction_four = (TextView) view.findViewById(R.id.tv_char_introduction_four);
        }
    }
}
