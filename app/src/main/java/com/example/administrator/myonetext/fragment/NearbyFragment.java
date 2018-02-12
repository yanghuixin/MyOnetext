package com.example.administrator.myonetext.fragment;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.activity.ChoseStartArriveCityActivity;
import com.example.administrator.myonetext.adapter.NearbyFragmentAdapter;
import com.example.administrator.myonetext.bean.CityEvent;
import com.example.administrator.myonetext.bean.LALMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/24.
 */

public class NearbyFragment extends BaseFragment implements AMapLocationListener {

    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.voice)
    ImageView voice;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    private String title[] = {"吃美食", "茶市场", "买买买", "全部"};
    private List<Fragment> fragment = new ArrayList<>();
    private NearbyFragmentAdapter adapter;
    //地图定位：
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public String wdpt;
    public String jdpt;
    private String startCityStr = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearbyframent, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initfragmentData();
        //将TabLayout与ViewPager绑定在一起
        //必须要用getChildFragmentManager()，不能用getActivity().getSupportFragmentManager()或者getFragmentManager()，不然会出现ViewPager 加载 Fragment 空白页的情况
        adapter = new NearbyFragmentAdapter(getChildFragmentManager(), fragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        initMapLocation();//初始化地图定位配置
        return view;
    }

    private void initfragmentData() {
        fragment.add(new OneNearbyFragment());
        fragment.add(new TwoNearbyFragment());
        fragment.add(new ThreeNearbyFragment());
        fragment.add(new FourNearbyFragment());
    }
    //初始化地图定位配置
    private void initMapLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 设置定位同时是否需要返回地址描述
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        boolean needAddress = mLocationOption.isNeedAddress();
        Log.d("address", "initMapLocation: -------------------->"+needAddress);
        // 设置是否强制刷新WIFI，默认为强制刷新。每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        // 设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟软件Mock位置结果，多为模拟GPS定位结果，默认为false，不允许模拟位置。
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位请求超时时间，默认为30秒
        // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(50000);
        // 设置是否只定位一次，默认为false
        mLocationOption.setOnceLocation(false);
        //启动定位
        mlocationClient.startLocation();
    }
    //获得定位结果
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {//获得定位结果
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();// 获取纬度
                wdpt = latitude + "";
                double longitude = amapLocation.getLongitude();// 获取经度
                jdpt = longitude + "";
                amapLocation.getAccuracy();//获取精度信息
                String address = amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                Log.d("address", "onLocationChanged:----------------> "+address+"纬度"+wdpt+"经度"+jdpt);
                EventBus.getDefault().post(new LALMessageEvent(address,wdpt,jdpt));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                mlocationClient.stopLocation();//停止定位
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.place, R.id.voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.place:
                Intent intent = new Intent(getActivity(), ChoseStartArriveCityActivity.class);
                intent.putExtra("flag", true);
                startActivity(intent);
                break;
            case R.id.voice:
                break;
        }
    }
    @Subscribe
    public void onEventMainThread(CityEvent cityEvent) {//城市的返回
        if (place != null && cityEvent.isFlag()) {
            startCityStr = cityEvent.getCities();
            place.setText(startCityStr);
        }
    }
}
