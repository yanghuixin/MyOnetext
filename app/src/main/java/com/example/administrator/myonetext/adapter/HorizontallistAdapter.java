package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.TodayDataRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/20.
 */
//今日秒杀
public class HorizontallistAdapter extends BaseAdapter {
    private Context context;
    private List<TodayDataRes.MsgBean> todayData;

    public HorizontallistAdapter(Context context, List<TodayDataRes.MsgBean> todayData) {
        this.context = context;
        this.todayData = todayData;
    }

    @Override
    public int getCount() {
        Log.d("request", "getCount:---------------------> " + todayData.size());
        return todayData.size() == 0 ? 0 : todayData.size();
    }

    @Override
    public Object getItem(int position) {
        return todayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Glide.with(context).load(todayData.get(position).getPicurl()).into(holder.todyImage1);
        return convertView;
    }

    static  class ViewHolder {
        @BindView(R.id.tody_image1)
        ImageView todyImage1;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
