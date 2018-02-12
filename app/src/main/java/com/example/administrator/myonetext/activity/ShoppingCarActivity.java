package com.example.administrator.myonetext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myonetext.HomeActivity;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.adapter.ShoppingCarAdapter;
import com.example.administrator.myonetext.bean.ShoppingCartBean;
import com.example.administrator.myonetext.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingCarActivity extends AppCompatActivity implements ShoppingCarAdapter.CheckInterface, ShoppingCarAdapter.ModifyCountInterface {
    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.shoppingCar_home)
    Button shoppingCarHome;
    @BindView(R.id.shoppingCar_seckkill)
    Button shoppingCarSeckkill;
    @BindView(R.id.shoppingCar_businessMore)
    Button shoppingCarBusinessMore;
    @BindView(R.id.shoppingCar_more)
    Button shoppingCarMore;
    @BindView(R.id.list_shopping_cart)
    ListView shoppingCarList;
    @BindView(R.id.ck_all)
    CheckBox ckAll;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_settlement)
    Button tvSettlement;
    private ShoppingCarAdapter adapter;
    private final static int INIT_STOR_DATE = 2;//店铺
    private final static int SERVER_EXCEPTION = 4;//服务器异常
    private final static int NETWORK_ANOMALY = 5;//网络异常
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_STOR_DATE:
//                    storData.clear();
//                    storData.addAll(nearbyStoreDataRes.getMsg());
//                    adapter.notifyDataSetChanged();
                    break;
                case SERVER_EXCEPTION:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
                case NETWORK_ANOMALY:
                    ToastUtils.showToast(getApplicationContext(), (String) msg.obj);
                    break;
            }
        }
    };
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private int totalCount = 0;// 购买的商品总数量
    private double totalPrice = 0.00;// 购买的商品总价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.ck_all, R.id.tv_settlement, R.id.re_clickBack, R.id.threespot, R.id.shoppingCar_home, R.id.shoppingCar_seckkill, R.id.shoppingCar_businessMore, R.id.shoppingCar_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:

                break;
            case R.id.shoppingCar_home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                this.finish();
                break;
            case R.id.shoppingCar_seckkill:
                Intent intent1 = new Intent();
                intent1.setClass(this, NearbyMoreActivity.class);
                intent1.putExtra("NearbyFragment", "11");
                startActivity(intent1);
                break;
            case R.id.shoppingCar_businessMore:
                Intent intent2 = new Intent();
                intent2.setClass(this, NearbyMoreActivity.class);
                intent2.putExtra("NearbyFragment", "12");
                startActivity(intent2);
                break;
            case R.id.shoppingCar_more:
                Intent intent3 = new Intent();
                intent3.setClass(this, WholeActivity.class);
                startActivity(intent3);
                break;
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        Log.d("totalCount", "onViewClicked:ck_all --------------------->" + totalCount);
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_settlement://结算
                lementOnder();
                break;
        }
    }

    protected void initData() {
//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("上档次的T桖");
//            shoppingCartBean.setDressSize(20);
//            shoppingCartBean.setId(i);
//            shoppingCartBean.setPrice(30.6);
//            shoppingCartBean.setCount(1);
//            shoppingCartBean.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("瑞士正品夜光男女士手表情侣精钢带男表防水石英学生非天王星机械");
//            shoppingCartBean.setAttribute("黑白色");
//            shoppingCartBean.setPrice(89);
//            shoppingCartBean.setId(i + 2);
//            shoppingCartBean.setCount(3);
//            shoppingCartBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/2160089910/TB2M_NSbB0kpuFjSsppXXcGTXXa_!!2160089910.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
        shoppingCartBeanList.addAll(DataSupport.findAll(ShoppingCartBean.class));
        adapter = new ShoppingCarAdapter(this);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        shoppingCarList.setAdapter(adapter);
        adapter.setShoppingCartBeanList(shoppingCartBeanList);
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                // totalCount++;//买了几种产品
                int i1 = Integer.parseInt(shoppingCartBean.getCount());
                totalCount += i1;//买了几个产品
                String price = shoppingCartBean.getPrice();
                double v = Double.parseDouble(price);
                totalPrice += v * i1;
            }
        }
        tvShowPrice.setText("全选  合计:" + totalPrice);
        tvAllPrice.setText("总额:" + totalPrice);
        tvSettlement.setText("结算(" + totalCount + ")");
        ////////////////////////////////////////////////////////////////
//        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
//            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
//            if (shoppingCartBean.isChoosed()) {
//                // totalCount++;//买了几种产品
//                int i1 = Integer.parseInt(shoppingCartBean.getCount());
//                totalCount += i1;//买了几个产品
//                String price = shoppingCartBean.getPrice();
//                double v = Double.parseDouble(price);
//                totalPrice += v * i1;
//            }
//        }

        //////////////////////////////////////////////////////////////////
    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        adapter.notifyDataSetChanged();
        //totalCount是所选条目的数量，选中未选中都会走
        Log.d("totalCount", "onViewClicked:单选 --------------------->" + totalCount);

        statistics();
    }

    /**
     * 增加
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        totalCount = 0;
        totalPrice = 0.00;
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        //  int currentCount = shoppingCartBean.getCount();
        int currentCount = Integer.parseInt(shoppingCartBean.getCount());
        currentCount++;
        shoppingCartBean.setCount(currentCount + "");
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        Log.d("totalCount", "onViewClicked:增加 --------------------->" + totalCount);

        if (shoppingCartBean.isSaved()) {
            shoppingCartBean.setCount(currentCount + "");
            shoppingCartBean.save();
        }

        statistics();
    }

    /**
     * 减少
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        // int currentCount = shoppingCartBean.getCount();
        int currentCount = Integer.parseInt(shoppingCartBean.getCount());
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount + "");
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        Log.d("totalCount", "onViewClicked: 删减 --------------------->" + totalCount);

        if (shoppingCartBean.isSaved()) {
            shoppingCartBean.setCount(currentCount + "");
            shoppingCartBean.save();
        }
        statistics();
    }

    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        shoppingCartBeanList.remove(position);
        adapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (ShoppingCartBean group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        //选中的需要提交的商品清单
        for (ShoppingCartBean bean : shoppingCartBeanList) {
            boolean choosed = bean.isChoosed();
            if (choosed) {
                String shoppingName = bean.getShoppingName();
                int count = Integer.parseInt(bean.getCount());
                double price = Double.parseDouble(bean.getPrice());
                int size = bean.getDressSize();
                String attribute = bean.getAttribute();
                int id = bean.getId();
                Log.d("结算", id + "----id---" + shoppingName + "---" + count + "---" + price + "--size----" + size + "--attr---" + attribute);
            }
        }
        ToastUtils.showToast(this, "总价：" + totalPrice);
        Intent intent = new Intent();
        intent.setClass(this, SubmitOrderActivity.class);
        //////////////////////////////////////////////////////////////////////
        List<ShoppingCartBean> orderBean = new ArrayList<>();
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                // totalCount++;//买了几种产品
                int i1 = Integer.parseInt(shoppingCartBean.getCount());
                totalCount += i1;//买了几个产品
                String price = shoppingCartBean.getPrice();
                double v = Double.parseDouble(price);
                totalPrice += v * i1;
                int id = shoppingCartBean.getId();
                String imageUrl = shoppingCartBean.getImageUrl();
                String storName = shoppingCartBean.getStorName();
                String count = shoppingCartBean.getCount();
                String price1 = shoppingCartBean.getPrice();
                String shoppingName = shoppingCartBean.getShoppingName();
                orderBean.add(new ShoppingCartBean(id, imageUrl, storName, shoppingName, price1, count));
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderBean", (Serializable) orderBean);
        intent.putExtras(bundle);
        intent.putExtra("totalPrice", totalPrice);
        /////////////////////////////////////////////////////////////////////////
        startActivity(intent);
        //跳转到支付界面
    }


}
