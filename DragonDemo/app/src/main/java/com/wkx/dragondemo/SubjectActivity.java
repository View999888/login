package com.wkx.dragondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wkx.adapter.SubjectAdapter;
import com.wkx.base.BaseMvpActivity;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.SpecialtyChooseEntity;
import com.wkx.constants.ConstantKey;
import com.wkx.eventUtils.SelectEvent;
import com.wkx.fragme.ApiConfig;
import com.wkx.model.LauchModel;
import com.wkx.utils.newAdd.SharedPrefrenceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wkx.methods.JumpConstant.JUMP_KEY;
import static com.wkx.methods.JumpConstant.SPLASH_TO_SUB;
import static com.wkx.methods.JumpConstant.SUB_TO_LOGIN;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {


    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.more_content)
    TextView moreContent;
    private SubjectAdapter mAdapter;
    private String mFrom;

    @Override
    public LauchModel setModel() {
        return new LauchModel();
    }

    @Override
    protected void setupData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            mAdapter.notifyDataSetChanged();
        } else
            comonPresenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    protected void setupView() {
        mFrom = getIntent().getStringExtra(JUMP_KEY);
        titleContent.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(mAdapter);
        moreContent.setText("完成");
        moreContent.setOnClickListener(v->{
            if (myApplication.getSelectedInfo() == null){
                showToast("请选择专业");
                return;
            }
            if (SPLASH_TO_SUB.equals(mFrom)){
                if (myApplication.isLogin()){
                    startActivity(new Intent(SubjectActivity.this,HomeActivity.class));
                } else {
                    startActivity(new Intent(SubjectActivity.this,LoginActivity.class).putExtra(JUMP_KEY,SUB_TO_LOGIN));
                }
            }
            finish();
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_subject;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] objects) {
        switch (witchApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) objects[0];
                mListData.addAll(info.result);
                mAdapter.notifyDataSetChanged();
                //缓存的ConstantKey
                SharedPrefrenceUtils.putSerializableList(this, ConstantKey.SUBJECT_LIST, mListData);
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //保存选中的专业的数据
        SharedPrefrenceUtils.putObject(this, ConstantKey.SUBJECT_SELECT, myApplication.getSelectedInfo());
        EventBus.getDefault().post(new SelectEvent());
    }

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        finish();
    }
}