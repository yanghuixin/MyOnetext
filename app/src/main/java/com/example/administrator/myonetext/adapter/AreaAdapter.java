package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.AreaBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AreaBean> datas;
    private int greenColor;
    private int grayColor;

    public AreaAdapter() {
    }
    public void setData(ArrayList<AreaBean> datas) {
        this.datas=datas;
    }

    @Override
    public AreaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        greenColor = context.getResources().getColor(R.color.colorPrimary);
        grayColor = context.getResources().getColor(R.color.color_area);
        AreaAdapter.MyViewHolder holder = new AreaAdapter.MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_area, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(AreaAdapter.MyViewHolder holder, final int position) {
        AreaBean bean = datas.get(position);
        if (bean.isSelected()) {//已选中
            holder.area.setTextColor(greenColor);
        } else {
            holder.area.setTextColor(grayColor);
        }
        holder.area.setText(bean.getAreaName());

        holder.area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_area)
        TextView area;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private AreaAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AreaAdapter.OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(int position);

    }
}

