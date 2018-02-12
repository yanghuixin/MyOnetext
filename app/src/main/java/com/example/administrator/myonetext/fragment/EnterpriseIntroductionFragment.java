package com.example.administrator.myonetext.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/26.
 */

public class EnterpriseIntroductionFragment extends BaseFragment {

//    @BindView(R.id.picture)
//    ImageView picture;
//    @BindView(R.id.text)
//    TextView text;
    Unbinder unbinder;
    @BindView(R.id.webview)
    WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.enterpriseintroductionfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);//订阅
        //  strGydp= "<p><span style='color: rgb(56, 56, 56); line-height: 28px; font-family: \"Microsoft Yahei\", 宋体; font-size: 14px;'>     大德盛普洱茶旗舰店位于<span style='color: rgb(51, 51, 51); font-family: \"Microsoft Yahei\", Arial; font-size: 14px;'>北京市西城区马连道路14号33-1(马连道大德盛茶城1-2号）</span>，经营的项目有茶叶和茶具，主营的普洱茶与紫砂茶具等,线上产品价格低于实体店价格，</span></p><p><span style='color: rgb(56, 56, 56); line-height: 28px; font-family: \"Microsoft Yahei\", 宋体; font-size: 14px;'>我店现在打造线上交易，注册会员活动，你只要注册我们大德盛会员，即可获得800年树龄古树普洱茶100克（市场价值160元）也可以来店体验试饮。</span></p><p><span style='color: rgb(56, 56, 56); line-height: 28px; font-family: \"Microsoft Yahei\", 宋体; font-size: 14px;'> 主营高端普洱茶，数量小，以客户提前定制为主，古树纯料茶，当年头拨茶，单株古树头拨春茶，山头头拨春茶。自营自制。04年至今，每年都收有古树茶，存放。</span></p><p><span style='color: rgb(56, 56, 56); font-family: Microsoft Yahei, 宋体;'><span style='line-height: 28px; font-size: 14px;'>大德盛普洱茶特点明显，山头特征有代表性，年份差别等，适合收藏品饮，性价比高</span></span></p><p><span style='font-family: 微软雅黑,Microsoft YaHei; font-size: 16px;'>本店线上拥有网店，线下有实体店、欢迎广大顾客前来线上或线下选购产品，我们将竭诚为您服务！<br/>对消费者承诺：<br/>1.本店线上的所有商品均和线下实体店铺的商品质量一致，并且线上的产品价格会略低于线下价格；<br/>2.本店励志做到线上产品及时与线下产品互动，及时更新线上产品，保证线上的产品质量优、品质好、价格实惠。<br/>3.消费者如果线上购买的商品价格高于线下或者遇到质量问题，消费者可以联系本店或者直接到“茶天下”平台投诉。<br/>4.消费者下单成功后，本店及时配送发货，保证产品快速的运送到您手中。</span><br/><br/><br/></p><p style='display:none;' data-background='background-repeat:no-repeat; background-position:center center; background-color:#FBD5B5;'><br/></p>";
        /////////////////////////////////////////////////////////////////////////
//        Bundle bundle = getArguments();
//        if (bundle!=null){
//            String strGydp = bundle.getString("strGydp");
//            Log.d("ssss", "onCreateView:ssss -------------------------->"+strGydp);
//            initWebSetting();
//            webview.loadData(strGydp, "text/html; charset=UTF-8", null);
//        }else {
//            Log.d("ssss", "-------------------------->bundle为空");
//        }
        /////////////////////////////////////////////////////////////////////
        return view;

    }
    ///////////////////////////////////////////////////////////////
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        initWebSetting();
        String s = event.getS();
        Log.d("ssss", "onMessageEvent -------------------------->"+s);
        webview.loadData(s, "text/html; charset=UTF-8", null);//这种写法可以
    };
//////////////////////////////////////////////////////////////////////////

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);//解除订阅
    }
    private void initWebSetting() {
//声明WebSettings子类
        WebSettings webSettings = webview.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//给权限
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //优先使用缓存:
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        Log.d("ssss", "initWebSetting -------------------------->");
    }
}
