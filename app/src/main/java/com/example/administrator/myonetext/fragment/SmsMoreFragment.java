package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myonetext.R;

/**
 * Created by Administrator on 2018/1/9.
 */

public class SmsMoreFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.smsmore,container,false);

        return view;
    }
}
