package com.wkx.adapter;


import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用界面有：
 * 首页课程页
 * 首页VIP页
 * 搜索结果页
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    public TabViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    /**
     * 重写此方法，处理Fragment刷新无效的的问题
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //拿到缓存的fragment，如果没有缓存的，就新建一个，新建发生在fragment的第一次初始化时
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        String fragmentTag = fragment.getTag();
        if (fragment != getItem(position)) {
            //如果是新建的fragment，f 就和getItem(position)是同一个fragment，否则进入下面
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = getItem(position);
            //添加新fragment时必须用前面获得的tag
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commitAllowingStateLoss();
        }
        return fragment;
    }
}
