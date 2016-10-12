package com.lll.library.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;

import com.lll.library.R;

/**
 * Created by LLL on 2016/10/12.
 */
public class MyLoadingDialog extends ProgressDialog {
    private ProgressDialog progressDialog = null;
    private Context context;
    private static MyLoadingDialog myLoadingDialog = null;

    public MyLoadingDialog(Context context) {
        super(context);
    }

    public MyLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static MyLoadingDialog showLoading(Context context) {
        myLoadingDialog = new MyLoadingDialog(context);
        myLoadingDialog.setCanceledOnTouchOutside(false);
        myLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        myLoadingDialog.setMessage(context.getString(R.string.loading));
        myLoadingDialog.show();

        return myLoadingDialog;
    }

    public static void dismissLoading() {
        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
    }
}
