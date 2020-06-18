package com.wkx.dragondemo;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wkx.base.BaseMvpActivity;
import com.wkx.bean.BaseInfo;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.IComonModel;
import com.wkx.infaces.MyTextWatcher;
import com.wkx.model.AccountModel;
import com.wkx.utils.CheckUserNameAndPwd;
import com.wkx.utils.newAdd.RegexUtil;
import com.wkx.utils.newAdd.SoftInputControl;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import razerdp.design.DialogPopup;

public class RegisterActivity extends BaseMvpActivity implements DialogPopup.DialogClickCallBack {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.more_content)
    TextView moreContent;
    @BindView(R.id.telephone_desc)
    TextView telephoneDesc;
    @BindView(R.id.telephone_cut)
    View telephoneCut;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.cutLine_telephone)
    View cutLineTelephone;
    @BindView(R.id.getVerification)
    TextView getVerification;
    @BindView(R.id.veri_cut)
    View veriCut;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.verificationArea)
    ConstraintLayout verificationArea;
    @BindView(R.id.next_page)
    TextView nextPage;
    private DialogPopup mDialogPopup;
    private Disposable mTimeObserver;

    @Override
    protected IComonModel setModel() {
        return new AccountModel();
    }

    @Override
    protected void setupData() {
    }

    @Override
    protected void setupView() {
        titleContent.setText("请填写手机号");
        nextPage.setEnabled(false);
        password.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                nextPage.setEnabled(s.length() == 6 ? true : false);
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_regiester;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pD) {
        switch (witchApi) {
            case ApiConfig.CHECK_PHONE_IS_USED:
                BaseInfo checkInfo = (BaseInfo) pD[0];
                if (checkInfo.isSuccess()) {
                    mDialogPopup = new DialogPopup(this, true);
                    mDialogPopup.setContent(userName.getText().toString() + "\n" + "是否将短信发送到手机");
                    mDialogPopup.setDialogClickCallBack(this);
                    mDialogPopup.showPopupWindow();
                } else {
                    showToast("该手机已注册");
                }
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                BaseInfo sendResult = (BaseInfo) pD[0];
                if (sendResult.isSuccess()) {
                    showToast("验证码发送成功");
                    goTime();
                } else setLog(sendResult.msg);
                break;
            case ApiConfig.REGISTER_PHONE:
                BaseInfo info = (BaseInfo) pD[0];
                if (info.isSuccess()) {
                    showToast("验证码验证成功");
                    startActivity(new Intent(this, RegisterPhoneActivity.class).putExtra("phoneNum", telephoneDesc.getText().toString() + userName.getText().toString().trim()));
                    finish();
                } else setLog(info.msg);
                break;
        }
    }

    @OnClick({R.id.back_image, R.id.getVerification, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.getVerification:
                if (userName.getText() == null) {
                    showToast("请输入手机号");
                    return;
                }
                boolean phone = RegexUtil.isPhone(userName.getText().toString().trim());
                if (phone) {
                    SoftInputControl.hideSoftInput(this, userName);
                    comonPresenter.getData(ApiConfig.CHECK_PHONE_IS_USED, telephoneDesc.getText().toString() + userName.getText().toString().trim());
                } else showToast("手机号格式错误");
                break;
            case R.id.next_page:
                comonPresenter.getData(ApiConfig.REGISTER_PHONE, telephoneDesc.getText().toString() + userName.getText().toString().trim(), password.getText().toString().trim());
                break;
        }
    }


    private int preTime = 60;

    private void goTime() {
        mTimeObserver = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    if (preTime - l > 0) {
                        getVerification.setText(preTime - l + "s");
                    } else {
                        getVerification.setText("获取验证码");
                        disPose();
                    }
                });
    }

    private void disPose() {
        if (mTimeObserver != null && !mTimeObserver.isDisposed()) mTimeObserver.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disPose();
    }

    @Override
    public void onClick(int type) {
        if (type == mDialogPopup.OK) {
            comonPresenter.getData(ApiConfig.SEND_REGISTER_VERIFY, telephoneDesc.getText().toString() + userName.getText().toString().trim());
        }
        mDialogPopup.dismiss();
    }
}
