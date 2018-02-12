package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.myview.MyGridView;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/25.
 */

public class StoreHomeFragment extends Fragment {
    @BindView(R.id.storeBanner)
    Banner storeBanner;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.storehomefragment_gradeview)
    MyGridView storehomefragmentGradeview;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.storehomefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initStorehomeData();
        return view;

    }

    private void initStorehomeData() {





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {

    }
}
