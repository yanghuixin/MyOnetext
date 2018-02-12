package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.MoreproductDataRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/22.
 */

public class MoreproductAdapter extends BaseAdapter {
    private Context context;
    private List<MoreproductDataRes.MsgBean> moreproduct;

    public MoreproductAdapter(Context context, List<MoreproductDataRes.MsgBean> moreproduct) {
        this.context = context;
        this.moreproduct = moreproduct;
    }

    @Override
    public int getCount() {
        Log.d("getCount", "getCount:------------------> "+moreproduct.size());
        return moreproduct.size() == 0 ? 0 : moreproduct.size();
    }

    @Override
    public Object getItem(int position) {
        return moreproduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moreproduct, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(moreproduct.get(position).getPname());
        holder.price.setText(moreproduct.get(position).getShopprice());
        Glide.with(context).load(moreproduct.get(position).getPicurl()).into(holder.picture);
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
