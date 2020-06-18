package com.wkx.dragondemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.wkx.infaces.MyTextWatcher;
import com.wkx.utils.ext.ToastUtils;
import com.wkx.utils.newAdd.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends RelativeLayout {
    @BindView(R.id.account_login)
    TextView accountLogin;
    @BindView(R.id.verify_login)
    TextView verifyLogin;
    @BindView(R.id.account_point)
    View accountPoint;
    @BindView(R.id.verify_point)
    View verifyPoint;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.account_secrete)
    EditText accountSecrete;
    @BindView(R.id.account_module)
    ConstraintLayout accountModule;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.verify_account_first_cut_line)
    View verifyAccountFirstCutLine;
    @BindView(R.id.verify_name)
    EditText verifyName;
    @BindView(R.id.verify_vertical_cut_line)
    View verifyVerticalCutLine;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.get_verify_code)
    TextView getVerifyCode;
    @BindView(R.id.verify_area)
    ConstraintLayout verifyArea;
    @BindView(R.id.login_press)
    TextView loginPress;
    @BindView(R.id.delete_account_name)
    ImageView deleteAccountName;
    @BindView(R.id.is_visible)
    ImageView isPswVisible;

    public final int ACCOUNT_TYPE = 1, VERIFY_TYPE = 2;
    public int mCurrentLoginType = ACCOUNT_TYPE;


    private Context mContext;
    private final boolean mIsMoreType;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.login_view, this);
        ButterKnife.bind(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginView, 0, 0);
        mIsMoreType = typedArray.getBoolean(R.styleable.LoginView_isMoreType, true);
        initView();

        if (!mIsMoreType) {
            accountLogin.setVisibility(GONE);
            accountPoint.setVisibility(GONE);
            verifyPoint.setVisibility(GONE);
            verifyLogin.setVisibility(GONE);
        }
    }

    private void initView() {
        loginPress.setEnabled(false);

        //输入框(手机号...)
        accountName.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                //这个是输入框不为空和输入长度大于0 显示  INVISIBLE:占位符
                deleteAccountName.setVisibility(s.length() == 0 ? INVISIBLE : VISIBLE);
            }
        });

        //验证码
        accountSecrete.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5 && !TextUtils.isEmpty(accountName.getText().toString().trim()))
                    loginPress.setEnabled(true);
                else loginPress.setEnabled(false);

                isPswVisible.setVisibility(s.length() > 0 ? VISIBLE : INVISIBLE);
            }
        });

        //获取验证码
        verifyCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5 && RegexUtil.isPhone(verifyName.getText().toString().trim()))
                    loginPress.setEnabled(true);
                else loginPress.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.account_login, R.id.verify_login, R.id.get_verify_code, R.id.login_press, R.id.delete_account_name, R.id.is_visible})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_account_name:
                accountName.setText("");
                break;
            case R.id.is_visible:
                //输入密码:accountSecrete  此方法 提供了显示密码和隐藏密码
                accountSecrete.setTransformationMethod(isPswVisible.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());

                break;
            case R.id.account_login:
                if (mCurrentLoginType != ACCOUNT_TYPE) {
                    accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    accountPoint.setVisibility(VISIBLE);
                    verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                    verifyPoint.setVisibility(INVISIBLE);
                    accountModule.setVisibility(VISIBLE);
                    verifyArea.setVisibility(INVISIBLE);
                    mCurrentLoginType = ACCOUNT_TYPE;
                }
                break;
            case R.id.verify_login:
                if (mCurrentLoginType != VERIFY_TYPE) {
                    accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                    accountPoint.setVisibility(INVISIBLE);
                    verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    verifyPoint.setVisibility(VISIBLE);
                    accountModule.setVisibility(INVISIBLE);
                    verifyArea.setVisibility(VISIBLE);
                    verifyPoint.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                    mCurrentLoginType = VERIFY_TYPE;
                }
                break;
            case R.id.get_verify_code:
                if (TextUtils.isEmpty(verifyName.getText().toString().trim())) {
                    ToastUtils.show(mContext, "用户名为空!");
                    return;
                }

                if (!RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    ToastUtils.show(mContext, "手机号格式错误!");
                    return;
                }
                if (mLoginViewCallBack != null)
                mLoginViewCallBack.sendVerifyCode(areaCode.getText().toString().trim()+verifyName.getText().toString().trim());
                break;
            case R.id.login_press:
                String userName = "", passWord = "";
                if (mCurrentLoginType == ACCOUNT_TYPE) {
                    userName = accountName.getText().toString().trim();
                    passWord = accountSecrete.getText().toString().trim();
                } else {
                    userName = verifyName.getText().toString().trim();
                    passWord = verifyCode.getText().toString().trim();
                }
                if (mLoginViewCallBack != null)
                    mLoginViewCallBack.loginPress(mCurrentLoginType, userName, passWord);
                break;
        }
    }

    private LoginViewCallBack mLoginViewCallBack;

    public void setLoginViewCallBack(LoginViewCallBack pLoginViewCallBack) {
        mLoginViewCallBack = pLoginViewCallBack;
    }

    public interface LoginViewCallBack {
        void sendVerifyCode(String phoneNum);

        void loginPress(int type, String userName, String pwd);
    }
}
