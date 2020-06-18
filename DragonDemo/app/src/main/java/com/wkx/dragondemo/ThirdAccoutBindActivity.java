package com.wkx.dragondemo;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wkx.base.BaseMvpActivity;
import com.wkx.bean.ThirdLoginData;
import com.wkx.constants.ConstantKey;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.IComonModel;
import com.wkx.model.AccountModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ThirdAccoutBindActivity extends BaseMvpActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    private ThirdLoginData mThirdData;

    @Override
    protected IComonModel setModel() {
        return new AccountModel();
    }

    @Override
    protected void setupData() {
        titleContent.setText("绑定账号");
    }

    @Override
    protected void setupView() {
        mThirdData = getIntent().getParcelableExtra("thirdData");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_third_accout_bind;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] objects) {
        switch (witchApi) {
            case ApiConfig.BIND_ACCOUNT:
                setResult(ConstantKey.BIND_BACK_LOGIN);
                finish();
                break;
        }
    }

    @OnClick({R.id.back_image, R.id.button_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.button_bind:
                if (TextUtils.isEmpty(account.getText().toString())) {
                    showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showToast("密码不能为空");
                    return;
                }
                comonPresenter.getData(ApiConfig.BIND_ACCOUNT, account.getText().toString(), password.getText().toString(), mThirdData);
                break;
        }
    }
}