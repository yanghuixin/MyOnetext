package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.fragment.HomeFragment;
import com.example.administrator.myonetext.fragment.NearbyFragment;
import com.example.administrator.myonetext.fragment.SmsMessageFragment;
import com.example.administrator.myonetext.fragment.SmsMoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.smsMessage)
    RadioButton smsMessage;
    @BindView(R.id.smsMore)
    RadioButton smsMore;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.smsFrameLayout)
    FrameLayout smsFrameLayout;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SmsMessageFragment smsMessageFragment;
    private SmsMoreFragment smsMoreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        /**
         * 拿到事务管理器并开启事务
         */

        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        /**
         * 启动默认选中第一个
         */
        radioGroup.check(R.id.smsMessage);
        SmsMessageFragment smsMessageFragment = new SmsMessageFragment();
        fragmentTransaction.add(R.id.smsFrameLayout, smsMessageFragment);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.re_clickBack, R.id.smsMessage, R.id.smsMore, R.id.threespot})
    public void onViewClicked(View view) {
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.smsMessage:
                hideFragment(fragmentTransaction);
                smsMessageFragment = new SmsMessageFragment();
                fragmentTransaction.replace(R.id.smsFrameLayout, smsMessageFragment);
                fragmentTransaction.commit();
                break;
            case R.id.smsMore:
                hideFragment(fragmentTransaction);
                smsMoreFragment = new SmsMoreFragment();
                fragmentTransaction.replace(R.id.smsFrameLayout, smsMoreFragment);
                fragmentTransaction.commit();
                break;
            case R.id.threespot:
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (smsMessageFragment != null) {
            //transaction.hide(f3);
            fragmentTransaction.remove(smsMessageFragment);
        }
        if (smsMoreFragment != null) {
            //transaction.hide(f3);
            fragmentTransaction.remove(smsMoreFragment);
        }

    }
}
