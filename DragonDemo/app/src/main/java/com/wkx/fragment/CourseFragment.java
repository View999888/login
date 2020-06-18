package com.wkx.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wkx.adapter.MyFragmentAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.dragondemo.R;
import com.wkx.fragme.IComonModel;
import com.wkx.model.MainPageModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class CourseFragment extends BaseMvpFragment {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mFragmentAdapter;

    public static final int TRAINTAB = 3;
    public static final int BESTTAB = 1;
    public static final int PUBLICTAB = 2;

    @Override
    protected IComonModel setModel() {
        return new MainPageModel();
    }

    @Override
    protected void setupData() {
        Collections.addAll(titleList, "训练营", "精品课", "公开课");
        Collections.addAll(mFragments,CourseChildFragment.getInstance(TRAINTAB),CourseChildFragment.getInstance(BESTTAB),CourseChildFragment.getInstance(PUBLICTAB));
        mFragmentAdapter.notifyDataSetChanged();
        slideLayout.notifyDataSetChanged();
    }

    @Override
    protected void setupView() {
        mFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_course;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] objects) {

    }
}
