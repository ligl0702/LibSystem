package com.lll.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.util.Constant;
import com.lll.library.util.MyLoadingDialog;
import com.lll.library.util.SpUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mLoginBtn, mRegisterBtn;
    private EditText mLoginNameEt;
    private EditText mLoginPwdEt;
    private CheckBox mRememberPwdCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login_layout);

        initView();

        initData();
    }

    private void initData() {
        mRememberPwdCb.setChecked(SpUtil.getBoolean(LoginActivity.this, Constant.IS_REMEMBER_PWD, false));

        String userName = SpUtil.getString(LoginActivity.this, Constant.USER_NAME, "");
        if (!TextUtils.isEmpty(userName)) {
            mLoginNameEt.setText(userName);
            mLoginNameEt.setSelection(userName.length());
        }

        if (mRememberPwdCb.isChecked()) {
            String userPwd = SpUtil.getString(LoginActivity.this, Constant.USER_PWD, "");
            if (!TextUtils.isEmpty(userPwd)) {
                mLoginPwdEt.setText(userPwd);
            }
        }
    }

    private void initView() {
        mLoginNameEt = (EditText) findViewById(R.id.et_name);
        mLoginPwdEt = (EditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mRememberPwdCb = (CheckBox) findViewById(R.id.cb_remember_password);

        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mLoginNameEt.getText().toString().trim())) {
            showToast(getString(R.string.enter_login_name_tip));
            return false;
        }

        if (TextUtils.isEmpty(mLoginPwdEt.getText().toString().trim())) {
            showToast(getString(R.string.enter_login_pwd_tip));
            return false;
        }

        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        if (validate()) {
            MyLoadingDialog.showLoading(this);

            BmobUser user = new BmobUser();
            user.setUsername(mLoginNameEt.getText().toString().trim());
            user.setPassword(mLoginPwdEt.getText().toString().trim());
            user.login(new SaveListener<Object>() {
                @Override
                public void done(Object o, BmobException e) {
                    //通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息

                    if (e == null) {
                        MyLoadingDialog.dismissLoading();

                        showToast(getString(R.string.login_success_tip));
                        SpUtil.putString(LoginActivity.this, Constant.USER_NAME, mLoginNameEt.getText().toString().trim());

                        if (mRememberPwdCb.isChecked()) {
                            SpUtil.putBoolean(LoginActivity.this, Constant.IS_REMEMBER_PWD, true);
                            SpUtil.putString(LoginActivity.this, Constant.USER_PWD, mLoginPwdEt.getText().toString().trim());
                        } else {
                            SpUtil.putBoolean(LoginActivity.this, Constant.IS_REMEMBER_PWD, false);
                        }
                        startActivity(new Intent(LoginActivity.this, MainFragmentActivity.class));
                        finish();
                    } else {
                        showToast(e.getMessage());
                    }
                }
            });
        }
    }

    private void register() {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), Constant.REGISTER_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;

            case R.id.btn_register:
                register();
                break;

            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REGISTER_REQUEST_CODE) {
            if (data != null) {
                mLoginNameEt.setText(data.getStringExtra(Constant.USER_NAME));
                mLoginPwdEt.setText(data.getStringExtra(Constant.USER_PWD));

                login();
            }
        }
    }
}
