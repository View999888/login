package com.wkx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.wkx.base.BaseMvpFragment;
import com.wkx.base.MyApplication;
import com.wkx.bean.SpecialtyChooseEntity;
import com.wkx.design.BottomTabView;
import com.wkx.dragondemo.R;
import com.wkx.dragondemo.SubjectActivity;
import com.wkx.eventUtils.SelectEvent;
import com.wkx.model.ComnomHomeMdoel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wkx.methods.JumpConstant.HOME_TO_SUB;
import static com.wkx.methods.JumpConstant.JUMP_KEY;

public class HomeFragment extends BaseMvpFragment<ComnomHomeMdoel> implements BottomTabView.OnBottomTabClickCallBack, NavController.OnDestinationChangedListener {

    @BindView(R.id.select_subject)
    TextView selectSubject;
    private List<Integer> normalIcon = new ArrayList<>();//为选中的icon
    private List<Integer> selectedIcon = new ArrayList<>();// 选中的icon
    private List<String> tabContent = new ArrayList<>();//tab对应的内容
    protected NavController mHomeController;
    private final int MAIN_PAGE = 1, COURSE = 2, VIP = 3, DATA = 4, MINE = 5;

    private BottomTabView mTabView;
    private String preFragment = "";
    private String mCurrentFragment = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment.findNavController(this).addOnDestinationChangedListener((controller, destination, arguments) -> {
            mCurrentFragment = destination.getLabel().toString();
            new Handler().postDelayed(() -> {
                if (preFragment.equals("DataGroupDetailFragment") && mCurrentFragment.equals("HomeFragment"))
                    mTabView.changeSelected(DATA);
                preFragment = mCurrentFragment;
            },50);
        });
    }


    @Override
    protected ComnomHomeMdoel setModel() {
        return null;
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupView() {
        //注册
        EventBus.getDefault().register(this);
        mTabView= getView().findViewById(R.id.bottom_tab);
        //默认的图片
        Collections.addAll(normalIcon, R.drawable.main_page_view, R.drawable.course_view, R.drawable.vip_view, R.drawable.data_view, R.drawable.mine_view);
        //选中 的 图片
        Collections.addAll(selectedIcon, R.drawable.main_selected, R.drawable.course_selected, R.drawable.vip_selected, R.drawable.data_selected, R.drawable.mine_selected);
        //tab 的文字
        Collections.addAll(tabContent, "主页", "课程", "VIP", "资料", "我的");
        //设置每个tab的内容  默认进去是主页
        mTabView.setResource(normalIcon, selectedIcon, tabContent);
        mTabView.setOnBottomTabClickCallBack(this);

        mHomeController = Navigation.findNavController(getView().findViewById(R.id.home_fragment_container));
        mHomeController.addOnDestinationChangedListener(this);

        selectSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SubjectActivity.class), 200);
            }
        });

        SpecialtyChooseEntity.DataBean selectedInfo = MyApplication.getFrameApplication().getSelectedInfo();
        selectSubject.setText(selectedInfo.getSpecialty_name());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(SelectEvent pSelectEvent) {
        SpecialtyChooseEntity.DataBean selectedInfo = MyApplication.getFrameApplication().getSelectedInfo();
        selectSubject.setText(selectedInfo.getSpecialty_name());
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] objects) {

    }

    @Override
    public void clickTab(int tab) {
        switch (tab) {
            case MAIN_PAGE:
                mHomeController.navigate(R.id.mainPageFragment);
                break;
            case COURSE:
                mHomeController.navigate(R.id.courseFragment);
                break;
            case VIP:
                mHomeController.navigate(R.id.vipFragment);
                break;
            case DATA:
                mHomeController.navigate(R.id.dataFragment);
                break;
            case MINE:
                mHomeController.navigate(R.id.mineFragment);
                break;
        }
    }


    @OnClick({R.id.select_subject, R.id.search, R.id.message, R.id.scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_subject:
                startActivity(new Intent(getContext(), SubjectActivity.class).putExtra(JUMP_KEY, HOME_TO_SUB));
                break;
            case R.id.search:
                break;
            case R.id.message:
                break;
            case R.id.scan:
                break;
        }
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        setLog(destination.getLabel().toString());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        normalIcon.clear();
        selectedIcon.clear();
        tabContent.clear();
        EventBus.getDefault().unregister(this);
    }

}
