package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.AreaBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5.
 */

public class SelectedCityAdapter extends RecyclerView.Adapter<SelectedCityAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AreaBean> datas;


    public SelectedCityAdapter(){}
    public void setData(ArrayList<AreaBean> datas){
        this.datas=datas;
    }

    @Override
    public SelectedCityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        SelectedCityAdapter.MyViewHolder holder = new SelectedCityAdapter.MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_selected_city, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SelectedCityAdapter.MyViewHolder holder, final int position) {
        AreaBean bean = datas.get(position);

        holder.cityname.setText(bean.getAreaName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
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
        if(datas!=null)
            return datas.size();
        else return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_city_name)
        TextView cityname;
        @BindView(R.id.image_delete_city)
        ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void itemClick(int position);

    }
}
