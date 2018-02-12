package com.example.administrator.myonetext.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.fragment.HomeFragment;
import com.example.administrator.myonetext.fragment.PuductFragment;
import com.example.administrator.myonetext.fragment.StoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCollectionActivity extends AppCompatActivity {

    @BindView(R.id.re_clickBack)
    TextView reClickBack;
    @BindView(R.id.threespot)
    ImageView threespot;
    @BindView(R.id.product)
    RadioButton product;
    @BindView(R.id.store)
    RadioButton store;
    @BindView(R.id.tabs)
    RadioGroup radioGroup;
    @BindView(R.id.myCollectionFragment)
    FrameLayout myCollectionFragment;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PuductFragment puductFragment;
    private StoreFragment storeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        /**
         * 拿到事务管理器并开启事务
         */

        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        /**
         * 启动默认选中第一个
         */
        radioGroup.check(R.id.product);
        puductFragment = new PuductFragment();
        fragmentTransaction.replace(R.id.myCollectionFragment, puductFragment);
        fragmentTransaction.commit();

    }

    @OnClick({R.id.re_clickBack, R.id.threespot, R.id.product, R.id.store})
    public void onViewClicked(View view) {
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.re_clickBack:
                finish();
                break;
            case R.id.threespot:
                break;
            case R.id.product:
                hideFragment(fragmentTransaction);
                puductFragment = new PuductFragment();
                fragmentTransaction.replace(R.id.myCollectionFragment, puductFragment);
                fragmentTransaction.commit();
                break;
            case R.id.store:
                storeFragment = new StoreFragment();
                fragmentTransaction.replace(R.id.myCollectionFragment, storeFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (puductFragment != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            fragmentTransaction.remove(puductFragment);
        }
        if (storeFragment != null) {
            //transaction.hide(f2);
            fragmentTransaction.remove(storeFragment);
        }
    }
}
