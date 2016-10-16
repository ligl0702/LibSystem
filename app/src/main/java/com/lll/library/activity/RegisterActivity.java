package com.lll.library.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.entity.MyUser;
import com.lll.library.util.Constant;
import com.lll.library.util.MyLoadingDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LLL on 2016/10/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button mRegisterBtn;
    private EditText mLoginNameEt;
    private EditText mLoginPwdEt;

    private RadioGroup mRoleRg;//角色，管理员1/读者0
    private RadioButton mAdminRb;//管理员1
    private RadioButton mReaderRb;//读者0

    private String mRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        intiView();
    }

    private void intiView() {
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mRegisterBtn.setOnClickListener(this);

        mLoginNameEt = (EditText) findViewById(R.id.et_name);
        mLoginPwdEt = (EditText) findViewById(R.id.et_password);

        mRoleRg = (RadioGroup) findViewById(R.id.rg_role);
        mAdminRb = (RadioButton) findViewById(R.id.rb_administrator);
        mReaderRb = (RadioButton) findViewById(R.id.rb_reader);
        mRoleRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_administrator:
                        mRole = "1";
                        break;

                    case R.id.rb_reader:
                        mRole = "0";
                        break;
                }
            }
        });

    }

    private void register() {
        if (validate()) {
            MyLoadingDialog.showLoading(this);

            MyUser user = new MyUser();
            user.setUsername(mLoginNameEt.getText().toString().trim());
            user.setPassword(mLoginPwdEt.getText().toString().trim());
            user.role = mRole;

            //注意：不能用save方法进行注册
            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(final MyUser o, BmobException e) {
                    MyLoadingDialog.dismissLoading();

                    if (e == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(getString(R.string.register_success_tip));
                        builder.setPositiveButton(getString(R.string.goto_login_tip), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra(Constant.USER_NAME, mLoginNameEt.getText().toString().trim());
                                intent.putExtra(Constant.USER_PWD, mLoginPwdEt.getText().toString().trim());
                                setResult(RESULT_OK, intent);
                                RegisterActivity.this.finish();
                            }
                        });
                        builder.show();
                    } else {
                        showToast(e.getMessage());
                    }
                }
            });
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;

            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
