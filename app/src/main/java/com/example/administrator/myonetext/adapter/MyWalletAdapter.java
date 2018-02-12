package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.MyWalletDataRes;
import com.example.administrator.myonetext.bean.StoreCollectionDataRes;
import com.example.administrator.myonetext.bean.UserInfo;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/12.
 */

public class MyWalletAdapter extends BaseAdapter {
    private Context context;
    private List<MyWalletDataRes.MessageBean> message;
private  int pid;
    public MyWalletAdapter(Context context, List<MyWalletDataRes.MessageBean> message,int pid) {
        this.context = context;
        this.message = message;
        this.pid=pid;
    }

    @Override
    public int getCount() {
        return message.size() == 0 ? 0 : message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mywalletitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.LineNumber.setText(message.get(position).getBankname());
        holder.type.setText(message.get(position).getCardtype());
        holder.number.setText("卡号后四位：" + message.get(position).getCardno());
        holder.Unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUnbind(message.get(position).getCardid(),pid);
            }
        });
        return convertView;
    }

    private void initUnbind(String cardid, int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/Member.ashx?flag=del_bind_card&pid="+pid+"&cardid="+cardid+"appkey=4b3b1f1235j654af4561gracv54c4h5q";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(nearbyMs);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        final Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                if (null != response) {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    StoreCollectionDataRes storeCollectionDataRes = gson.fromJson(string, StoreCollectionDataRes.class);
                    if (storeCollectionDataRes.getStatus()==1){
                        Log.d("解绑", "onResponse: ------->"+storeCollectionDataRes.getMessage());
                        // TODO: 2018/2/7 去做删除条目
                    }else {
                        Log.d("解绑", "onResponse: ------->"+storeCollectionDataRes.getMessage());
                    }
                }
            }
        });
    }

    static class ViewHolder {
        @BindView(R.id.Line_number)
        TextView LineNumber;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.Unbind)
        TextView Unbind;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
