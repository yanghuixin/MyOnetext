package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.myonetext.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WholeActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.re_clickBack)
    public void onViewClicked() {
        finish();
    }
}
