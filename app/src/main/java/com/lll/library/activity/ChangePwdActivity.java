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
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.util.Constant;
import com.lll.library.util.MyLoadingDialog;
import com.lll.library.util.SpUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LLL on 2016/10/17.
 */
public class ChangePwdActivity extends Activity implements View.OnClickListener {
    private EditText mOldPwdEt, mNewPwd1Et, mNewPwd2Et;
    private Button mChangeBtn;

    private String mOldStrFromSP, mOldPwdFromInput, mNewStr1FromInput, mNewStr2FromInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pwd_layout);

        initView();
    }

    private void initView() {
        mOldPwdEt = (EditText) findViewById(R.id.et_old_pwd);
        mNewPwd1Et = (EditText) findViewById(R.id.et_new_pwd1);
        mNewPwd2Et = (EditText) findViewById(R.id.et_new_pwd2);

        mChangeBtn = (Button) findViewById(R.id.btn_change);
        mChangeBtn.setOnClickListener(this);
    }

    private void changePwd() {
        if (validate()) {
            MyLoadingDialog.showLoading(this);

            BmobUser.updateCurrentUserPassword(mOldPwdFromInput, mNewStr1FromInput, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    MyLoadingDialog.dismissLoading();

                    if (e == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePwdActivity.this);
                        builder.setTitle("确认");
                        builder.setMessage("密码修改成功，请重新登陆");
                        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SpUtil.putString(ChangePwdActivity.this, Constant.USER_PWD, "");
                                SpUtil.putBoolean(ChangePwdActivity.this, Constant.IS_REMEMBER_PWD, false);
                                startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class));
                            }
                        });
                        builder.show();
                    } else {
                        showToast(e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    private boolean validate() {
        mOldStrFromSP = SpUtil.getString(this, Constant.USER_PWD, "");
        mOldPwdFromInput = mOldPwdEt.getText().toString().trim();
        mNewStr1FromInput = mNewPwd1Et.getText().toString().trim();
        mNewStr2FromInput = mNewPwd2Et.getText().toString().trim();

        if (TextUtils.isEmpty(mOldPwdFromInput)) {
            showToast("请输入旧密码");
            mOldPwdEt.requestFocus();
            return false;
        }

        if (!TextUtils.isEmpty(mOldPwdFromInput)) {
            if (!TextUtils.equals(mOldStrFromSP, mOldPwdFromInput)) {
                showToast("请输入正确的旧密码");
                mOldPwdEt.requestFocus();
                return false;
            }
        }

        if (TextUtils.isEmpty(mNewStr1FromInput)) {
            showToast("请输入新密码");
            mNewPwd1Et.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mNewStr2FromInput)) {
            showToast("请再次输入新密码");
            mNewPwd2Et.requestFocus();
            return false;
        }

        if (!TextUtils.equals(mNewStr1FromInput, mNewStr2FromInput)) {
            showToast("两次输入的新密码不一致，请检查");
            mNewPwd2Et.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                changePwd();
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
