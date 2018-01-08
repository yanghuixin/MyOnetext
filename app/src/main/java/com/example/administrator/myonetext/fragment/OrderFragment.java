package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myonetext.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/24.
 */

public class OrderFragment extends BaseFragment {

    @BindView(R.id.redPackage)
    RadioButton redPackage;
    @BindView(R.id.PPaymoney)
    RadioButton PPaymoney;
    @BindView(R.id.PDGoods)
    RadioButton PDGoods;
    @BindView(R.id.receiverGoods)
    RadioButton receiverGoods;
    @BindView(R.id.completed)
    RadioButton completed;
    @BindView(R.id.RAndCS)
    RadioButton RAndCS;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.orderFragmentlayout)
    FrameLayout orderFragmentlayout;
    Unbinder unbinder;
    private FragmentTransaction transaction;
    private FragmentManager childFragmentManager;
    private PPaymoneyOrderFragment pPaymoneyOrderFragment;
    private PDGoodsOrderFragment pdGoodsOrderFragment;
    private ReceiverGoodsOrderFragment receiverGoodsOrderFragment;
    private RAndCSOrderFragment rAndCSOrderFragment;
    private CompletedOrderFragment completedOrderFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        /**
         * 拿到事务管理器并开启事务
         */
        childFragmentManager = getChildFragmentManager();
        transaction = childFragmentManager.beginTransaction();
        /**
         * 启动默认选中待付款
         */

        radioGroup.check(R.id.PPaymoney);//待付款
        pPaymoneyOrderFragment = new PPaymoneyOrderFragment();
        transaction.replace(R.id.orderFragmentlayout, pPaymoneyOrderFragment);
        transaction.commit();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.redPackage, R.id.PPaymoney, R.id.PDGoods, R.id.receiverGoods, R.id.completed, R.id.RAndCS})
    public void onViewClicked(View view) {
        childFragmentManager = getChildFragmentManager();
        transaction = childFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.redPackage://// TODO: 2018/1/2  红包付fragment还不知道什么页面



                break;
            case R.id.PPaymoney:
                hideFragment(transaction);
                pPaymoneyOrderFragment = new PPaymoneyOrderFragment();
                transaction.replace(R.id.orderFragmentlayout, pPaymoneyOrderFragment);
                transaction.commit();
                break;
            case R.id.PDGoods:
                hideFragment(transaction);
                pdGoodsOrderFragment = new PDGoodsOrderFragment();
                transaction.replace(R.id.orderFragmentlayout, pdGoodsOrderFragment);
                transaction.commit();
                break;
            case R.id.receiverGoods:
                hideFragment(transaction);
                receiverGoodsOrderFragment = new ReceiverGoodsOrderFragment();
                transaction.replace(R.id.orderFragmentlayout, receiverGoodsOrderFragment);
                transaction.commit();
                break;
            case R.id.completed:
                hideFragment(transaction);
                completedOrderFragment = new CompletedOrderFragment();
                transaction.replace(R.id.orderFragmentlayout, completedOrderFragment);
                transaction.commit();
                break;
            case R.id.RAndCS:
                hideFragment(transaction);
                rAndCSOrderFragment = new RAndCSOrderFragment();
                transaction.replace(R.id.orderFragmentlayout, rAndCSOrderFragment);
                transaction.commit();
                break;
        }
    }
    private void hideFragment(FragmentTransaction transaction) {
//        if (myFragment != null) {//红包付fragment还不知道什么页面
//            //transaction.hide(f3);
//            transaction.remove(myFragment);
//        }
        if (pPaymoneyOrderFragment != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(pPaymoneyOrderFragment);
        }
        if (pdGoodsOrderFragment != null) {
            //transaction.hide(f2);
            transaction.remove(pdGoodsOrderFragment);
        }
        if (receiverGoodsOrderFragment != null) {
            //transaction.hide(f3);
            transaction.remove(receiverGoodsOrderFragment);
        }
        if (completedOrderFragment != null) {
            //transaction.hide(f3);
            transaction.remove(completedOrderFragment);
        }
        if (rAndCSOrderFragment != null) {
            //transaction.hide(f3);
            transaction.remove(rAndCSOrderFragment);
        }

    }
}
