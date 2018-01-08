package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myonetext.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/24.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.tubiao1)
    ImageView tubiao1;
    @BindView(R.id.myshop)
    RelativeLayout myshop;
    @BindView(R.id.tubiao4)
    ImageView tubiao4;
    @BindView(R.id.myQRCode)
    RelativeLayout myQRCode;
    @BindView(R.id.tubiao5)
    ImageView tubiao5;
    @BindView(R.id.myWallent)
    RelativeLayout myWallent;
    @BindView(R.id.tubiao6)
    ImageView tubiao6;
    @BindView(R.id.myCollection)
    RelativeLayout myCollection;
    @BindView(R.id.tubiao7)
    ImageView tubiao7;
    @BindView(R.id.myShoppingCar)
    RelativeLayout myShoppingCar;
    @BindView(R.id.tubiao8)
    ImageView tubiao8;
    @BindView(R.id.myOrder)
    RelativeLayout myOrder;
    @BindView(R.id.tubiao9)
    ImageView tubiao9;
    @BindView(R.id.myReceiverAdress)
    RelativeLayout myReceiverAdress;
    @BindView(R.id.tubiao10)
    ImageView tubiao10;
    @BindView(R.id.myHuiRed)
    RelativeLayout myHuiRed;
    @BindView(R.id.tubiao3)
    ImageView tubiao3;
    @BindView(R.id.myWallet)
    RelativeLayout myWallet;
    @BindView(R.id.tubiao12)
    ImageView tubiao12;
    @BindView(R.id.mySetting)
    RelativeLayout mySetting;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myshop, R.id.myQRCode, R.id.myWallent, R.id.myCollection, R.id.myShoppingCar, R.id.myOrder, R.id.myReceiverAdress, R.id.myHuiRed, R.id.myWallet, R.id.mySetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myshop:
                break;
            case R.id.myQRCode:
                break;
            case R.id.myWallent:
                break;
            case R.id.myCollection:
                break;
            case R.id.myShoppingCar:
                break;
            case R.id.myOrder:
                break;
            case R.id.myReceiverAdress:
                break;
            case R.id.myHuiRed:
                break;
            case R.id.myWallet:
                break;
            case R.id.mySetting:
                break;
        }
    }
}
