package com.example.administrator.myonetext.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class NearbyFragmentAdapter extends FragmentPagerAdapter {
    private String title[] = {"吃美食", "茶市场", "买买买", "全部"};
    private List<Fragment> fragment;
    public NearbyFragmentAdapter(FragmentManager fm,List<Fragment> fragment) {
        super(fm);
        this.fragment=fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size()==0?0:fragment.size();
    }
    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
