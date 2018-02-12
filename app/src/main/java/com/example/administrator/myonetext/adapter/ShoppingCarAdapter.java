package com.example.administrator.myonetext.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.ShoppingCartBean;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ShoppingCarAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingCartBean> shoppingCartBeanList;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    public ShoppingCarAdapter(Context context) {
        this.context = context;
    }
    public void setShoppingCartBeanList(List<ShoppingCartBean> shoppingCartBeanList) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        notifyDataSetChanged();
    }
    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }
    @Override
    public int getCount() {
        return shoppingCartBeanList == null ? 0 : shoppingCartBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shoppingcar_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        boolean choosed = shoppingCartBean.isChoosed();
        if (choosed){
            holder.ckChose.setChecked(true);
        }else{
            holder.ckChose.setChecked(false);
        }
        String attribute = shoppingCartBean.getAttribute();
//        if (!StringUtil.isEmpty(attribute)){
//            //tvCommodityAttr产品属性对应控件
//            holder.tvCommodityAttr.setText(attribute);
//        }else{
//            holder.tvCommodityAttr.setText(shoppingCartBean.getDressSize()+"");
//        }
        holder.storname.setText(shoppingCartBean.getStorName());
        holder.productname.setText(shoppingCartBean.getShoppingName());
        holder.productprice.setText("￥"+shoppingCartBean.getPrice());
        holder.Subtotal.setText("小计：￥"+shoppingCartBean.getPrice());
        holder.number.setText(shoppingCartBean.getCount()+"");
        Glide.with(context).load(shoppingCartBean.getImageUrl()).into(holder.picture);
        //单选框按钮
        holder.ckChose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shoppingCartBean.setChoosed(((CheckBox) v).isChecked());
                        checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                    }
                }
        );
        //增加按钮
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position, holder.number, holder.ckChose.isChecked());//暴露增加接口
            }
        });
        //删减按钮
        holder.ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position, holder.number, holder.ckChose.isChecked());//暴露删减接口
            }
        });
        //删除弹窗
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
                                DataSupport.delete(ShoppingCartBean.class, shoppingCartBean.getId());
                                modifyCountInterface.childDelete(position);//删除 目前只是从item中移除
                            }
                        });
                alert.show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ck_chose)
        CheckBox ckChose;
        @BindView(R.id.picyure)
        ImageView picture;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.collection)
        RadioButton collection;
        @BindView(R.id.lin)
        RelativeLayout lin;
        @BindView(R.id.storname)
        TextView storname;
        @BindView(R.id.productname)
        TextView productname;
        @BindView(R.id.productprice)
        TextView productprice;
        @BindView(R.id.iv_sub)
        TextView ivSub;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.iv_add)
        TextView ivAdd;
        @BindView(R.id.rl_edit)
        LinearLayout rlEdit;
        @BindView(R.id.Subtotal)
        TextView Subtotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }
    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }
}
