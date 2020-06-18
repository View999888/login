package com.wkx.dragondemo;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wkx.base.BaseMvpActivity;
import com.wkx.bean.BaseInfo;
import com.wkx.fragme.ApiConfig;
import com.wkx.infaces.MyTextWatcher;
import com.wkx.model.AccountModel;
import com.wkx.utils.CheckUserNameAndPwd;
import butterknife.BindView;
import butterknife.OnClick;
import static com.wkx.methods.JumpConstant.JUMP_KEY;
import static com.wkx.methods.JumpConstant.REGISTER_TO_LOGIN;

public class RegisterPhoneActivity extends BaseMvpActivity<AccountModel> {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.clearAccount)
    ImageView clearAccount;
    @BindView(R.id.accountContent)
    EditText accountContent;
    @BindView(R.id.visibleImage)
    ImageView visibleImage;
    @BindView(R.id.accountSecret)
    EditText accountSecret;
    @BindView(R.id.next_page)
    TextView nextPage;
    private String mPhoneNum;

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    protected void setupData() {
        titleContent.setText("创建账号");
        mPhoneNum = getIntent().getStringExtra("phoneNum");
    }

    @Override
    protected void setupView() {
        accountContent.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                clearAccount.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountSecret.getText().length() > 0);
            }
        });
        accountSecret.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                visibleImage.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountContent.getText().length() > 0);
            }
        });
    }

    @Override
    protected int setLayout() {
        return  R.layout.activity_register_phone;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pD) {
        switch (witchApi) {
            case ApiConfig.NET_CHECK_USERNAME:
                BaseInfo baseInfo = (BaseInfo) pD[0];
                if (baseInfo.isSuccess()) {
                    comonPresenter.getData(ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT, accountContent.getText().toString(), accountSecret.getText().toString(), mPhoneNum);
                } else if (baseInfo.errNo == 114) {
                    showToast("该用户名不可用");
                } else showToast(baseInfo.msg);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                BaseInfo base = (BaseInfo) pD[0];
                if (base.errNo == 24 || base.errNo == 114 || base.isSuccess()) {
                    showToast("注册成功");
                    startActivity(new Intent(this, LoginActivity.class).putExtra(JUMP_KEY, REGISTER_TO_LOGIN));
                    finish();
                } else showToast(base.msg);
                break;
        }
    }




    @OnClick({R.id.back_image, R.id.clearAccount, R.id.visibleImage, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.clearAccount:
                accountContent.setText("");
                break;
            case R.id.visibleImage:
                accountSecret.setTransformationMethod(visibleImage.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
                visibleImage.setSelected(!visibleImage.isSelected());
                break;
            case R.id.next_page:
                if (CheckUserNameAndPwd.verificationUsername(this, accountContent.getText().toString(), accountSecret.getText().toString()))
                    comonPresenter.getData(ApiConfig.NET_CHECK_USERNAME, accountContent.getText().toString());
                break;
        }
    }
}
