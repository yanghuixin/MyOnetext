package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.AdressDataRes;
import com.example.administrator.myonetext.bean.DefaultAdressDataRes;
import com.example.administrator.myonetext.bean.GouhuiUser;
import com.example.administrator.myonetext.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.AbstractList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ReceiverAdressAdapter extends BaseAdapter {

    private Context content;
    private static List<AdressDataRes.MsgBean> adressData;
    private static ViewHolder holder;


    public ReceiverAdressAdapter(Context content, List<AdressDataRes.MsgBean> adressData) {
        this.content = content;
        this.adressData = adressData;
    }

    @Override
    public int getCount() {
        return adressData.size() == 0 ? 0 : adressData.size();
    }

    @Override
    public Object getItem(int position) {
        return adressData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(content).inflate(R.layout.receiveradressitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.name.setText(adressData.get(position).getAname());
        holder.adress.setText(adressData.get(position).getAddress());
        holder.phone.setText(adressData.get(position).getAphone());
        ///////////////////////////////////////////////////////////根据后台返回的默认地址设置CheckBox是否被选中
        if (adressData.get(position).getIsdfadd() == 1) {
            holder.adressChose.setChecked(true);
        }else {
            holder.adressChose.setChecked(false);
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        return convertView;
    }
    /////////////////////////////////////////////////////////////////根据CheckBox是否被选中,设置默认地址
    public void getAid() {
 //       for (int i = 0; i < adressData.size(); i++) {
//            if (holder.adressChose.isChecked()) {
//                int rid = adressData.get(i).getAid();
//                Log.d("defaultAdress", "onResponse:getAid- if-true-------------------------->"+rid);
//                UserInfo userInfo = GouhuiUser.getInstance().getUserInfo(content);
//                String uid = userInfo.getUid();
//                int  pid = Integer.parseInt(uid);
//                initdefaultAdress(rid,pid);
//            }else {
//                Log.d("defaultAdress", "onResponse:getAid- if---false------------------------>");
//            }
  //      }
//////////////////////////////////////////////////////////////////////尝试

        //////////////////////////////////////////////////////////////////////////////
    }

    private void initdefaultAdress(int rid, int pid) {
        String nearbyMs = "http://app.tealg.com/api/app/OrderMsg.ashx?flag=setdefaultadd&pid="+pid+"&rid="+rid+"&appkey=4b3b1f1235j654af4561gracv54c4h5q";
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
                    ////////////////////////////////////////////////////////
//                    Gson gson = new Gson();
//                    String string = response.body().string();
//                    Log.d("string", "商家onResponse:--------------------------> " + string);
//                    myWalletDataRes = gson.fromJson(string, MyWalletDataRes.class);
//                    if (myWalletDataRes.getStatus()==1){
//                        Message message = new Message();
//                        message.what = INIT_STOR_DATE;
//                        handler.sendMessage(message);
//                    }else {
//                        return;
//                    }
                    ///////////////////////////////////////////////////////////
                    Gson gson = new Gson();
                    String string = response.body().string();
                    try{
                        JsonElement je = new com.google.gson.JsonParser().parse(string);
                        if (je.getAsJsonObject().get("status").equals("0")){
                            return;
                        }else {
                            DefaultAdressDataRes defaultAdressDataRes = gson.fromJson(string, DefaultAdressDataRes.class);
                            if (defaultAdressDataRes.getStatus()==1){
                                Log.d("defaultAdress", "onResponse: ---------------------------->"+defaultAdressDataRes.getMsg());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //////////////////////////////////////////////////////////
                }
            }
        });


    }
    ///////////////////////////////////////////////////////////////////////////////

    static class ViewHolder {
        @BindView(R.id.adress_chose)
        CheckBox adressChose;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.adress)
        TextView adress;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.edit)
        Button edit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
