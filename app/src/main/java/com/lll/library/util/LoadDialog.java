
package com.lll.library.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lll.library.R;


public class LoadDialog extends Dialog {
    private static LoadDialog customProgressDialog = null;

    public LoadDialog(Context context) {
        super(context);
    }

    public LoadDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadDialog createDialog(Context context) {
        customProgressDialog = new LoadDialog(context, R.style.CustomDialog);
        customProgressDialog.setContentView(R.layout.loading_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressDialog;
    }


    public LoadDialog setTitle(String strTitle) {
        return customProgressDialog;
    }


    public LoadDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
}
