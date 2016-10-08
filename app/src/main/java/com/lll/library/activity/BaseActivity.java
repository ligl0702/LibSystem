package com.lll.library.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lll.library.R;
import com.lll.library.util.LoadDialog;

@SuppressLint("Registered")
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }

    // /////////////////////////////////////////////////////////////////////
    // loading dialog.

    private LoadDialog mLoadDialog;
    private DialogHandler mDialogHandler;
    private String mShowLoadingText;

    public void showLoadingDialogDirect() {
        if (mDialogHandler == null) {
            mDialogHandler = new DialogHandler();
        }
        mDialogHandler.sendEmptyMessage(1);
    }

    public void showLoadingDialog() {
        if (mDialogHandler == null) {
            mDialogHandler = new DialogHandler();
        }
        if (mLoadDialog == null) {
            mDialogHandler.sendEmptyMessageDelayed(1, 100);
        } else {
            mDialogHandler.sendEmptyMessage(1);
        }
    }

    public void showLoadingTextDialog(String message) {
        if (mDialogHandler == null) {
            mDialogHandler = new DialogHandler();
        }
        if (mLoadDialog == null) {
            mDialogHandler.sendEmptyMessageDelayed(1, 100);
        } else {
            mDialogHandler.sendEmptyMessage(1);
        }
    }

    public void hideLoadingDialog() {

        if (mDialogHandler == null)
            return;
        mDialogHandler.removeMessages(1);
        mDialogHandler.removeMessages(2);

        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @SuppressLint("HandlerLeak")
    private class DialogHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    if (mLoadDialog == null) {
                        mLoadDialog = LoadDialog.createDialog(BaseActivity.this);
                        mLoadDialog
                                .setMessage(getString(R.string.group_name_loading));
                    } else {
                        mLoadDialog
                                .setMessage(getString(R.string.group_name_loading));
                    }
                    mLoadDialog.show();
                    break;
                case 2:
                    int messageId = msg.arg1;
                    if (messageId == 0)
                        messageId = R.string.group_name_loading;
                    boolean cancelable = msg.arg2 == 1;
                    if (mLoadDialog == null) {
                        mLoadDialog = LoadDialog.createDialog(BaseActivity.this);
                        mLoadDialog.setMessage(getString(messageId));
                        mLoadDialog.setCancelable(cancelable);
                        mLoadDialog.setCanceledOnTouchOutside(cancelable);
                    } else {
                        mLoadDialog.setMessage(getString(messageId));
                    }
                    mLoadDialog.show();

                    break;
            }
        }
    }

}
