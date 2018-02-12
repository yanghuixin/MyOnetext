package com.example.administrator.myonetext;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myonetext.fragment.HomeFragment;
import com.example.administrator.myonetext.fragment.MyFragment;
import com.example.administrator.myonetext.fragment.NearbyFragment;
import com.example.administrator.myonetext.fragment.OrderFragment;
import com.example.administrator.myonetext.fragment.ScanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.fragment)
    FrameLayout fragmentLayout;
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.nearby)
    RadioButton nearby;
    @BindView(R.id.scan)
    RadioButton scan;
    @BindView(R.id.order)
    RadioButton order;
    @BindView(R.id.my)
    RadioButton my;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private HomeFragment honeFragment;
    private NearbyFragment nearbyFragment;
    private ScanFragment scanFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        /**
         * 拿到事务管理器并开启事务
         */
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        /**
         * 启动默认选中第一个
         */
        radioGroup.check(R.id.home);
        honeFragment = new HomeFragment();
        transaction.replace(R.id.fragment, honeFragment);
        transaction.commit();

    }

    @OnClick({R.id.home, R.id.nearby, R.id.scan, R.id.order, R.id.my})
    public void onViewClicked(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.home:
                /**
                 * 为了防止重叠，需要点击之前先移除其他Fragment
                 */
                hideFragment(transaction);
                honeFragment = new HomeFragment();
                transaction.replace(R.id.fragment, honeFragment);
                transaction.commit();
                break;
            case R.id.nearby:
                hideFragment(transaction);
                nearbyFragment = new NearbyFragment();
                transaction.replace(R.id.fragment, nearbyFragment);
                transaction.commit();
                break;
            case R.id.scan:
                hideFragment(transaction);
                scanFragment = new ScanFragment();
                transaction.replace(R.id.fragment, scanFragment);
                transaction.commit();
                break;
            case R.id.order:
                hideFragment(transaction);
                orderFragment = new OrderFragment();
                transaction.replace(R.id.fragment, orderFragment);
                transaction.commit();
                break;
            case R.id.my:
                hideFragment(transaction);
                myFragment = new MyFragment();
                transaction.replace(R.id.fragment, myFragment);
                transaction.commit();
                break;
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (honeFragment != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(honeFragment);
        }
        if (nearbyFragment != null) {
            //transaction.hide(f2);
            transaction.remove(nearbyFragment);
        }
        if (scanFragment != null) {
            //transaction.hide(f3);
            transaction.remove(scanFragment);
        }
        if (orderFragment != null) {
            //transaction.hide(f3);
            transaction.remove(orderFragment);
        }
        if (myFragment != null) {
            //transaction.hide(f3);
            transaction.remove(myFragment);
        }

    }

    //////////////////////////////////////////
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        int id = intent.getIntExtra("id", 0);
        if (id == 1) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment honeFragment = new HomeFragment();
            ft.replace(R.id.fragment, honeFragment);
            ft.commit();
        }
    }
}
