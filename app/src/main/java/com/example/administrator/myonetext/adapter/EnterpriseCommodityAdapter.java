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
import com.example.administrator.myonetext.bean.EnterpriseCommodityDataRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/26.
 */

public class EnterpriseCommodityAdapter extends BaseAdapter {
    private Context context;
    private List<EnterpriseCommodityDataRes.MsgBean> msgBean;

    public EnterpriseCommodityAdapter(Context context, List<EnterpriseCommodityDataRes.MsgBean> msgBean) {
        this.context = context;
        this.msgBean = msgBean;
    }

    @Override
    public int getCount() {
        return msgBean.size() == 0 ? 0 : msgBean.size();
    }

    @Override
    public Object getItem(int position) {
        return msgBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.enterprisecommodit_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(msgBean.get(position).getPname());
        float shopprice = msgBean.get(position).getShopprice();
        holder.price.setText(shopprice + "");
        Glide.with(context).load(msgBean.get(position).getPicurl()).into(holder.picture);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.picture)
        ImageView picture;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
