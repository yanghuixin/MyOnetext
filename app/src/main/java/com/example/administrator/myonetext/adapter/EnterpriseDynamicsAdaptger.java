package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myonetext.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/26.
 */

public class EnterpriseDynamicsAdaptger extends BaseAdapter {
    private Context context;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.enterprisedynamics_item, parent, false);
       holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();




        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.author)
        TextView author;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
