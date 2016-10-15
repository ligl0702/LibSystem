package com.lll.library.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lll.library.R;
import com.lll.library.activity.AddBookActivity;
import com.lll.library.activity.CommonScanActivity;
import com.lll.library.util.Constant;
import com.yxp.permission.util.lib.PermissionUtil;
import com.yxp.permission.util.lib.callback.PermissionResultCallBack;


/**
 * Created by guoliangli on 2016/9/9.
 */
public class AddFragment extends Fragment implements View.OnClickListener {
    private Button mBarCodeBtn;
    private Button mAddManuallyBtn;

    private View contentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            initView(inflater);
        }
        if (contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }

        return contentView;
    }

    private void initView(LayoutInflater inflater) {
        contentView = inflater.inflate(R.layout.add_fragment_layout, null);

        mBarCodeBtn = (Button) contentView.findViewById(R.id.btn_bar_code);
        mAddManuallyBtn = (Button) contentView.findViewById(R.id.btn_add_manually);
        mBarCodeBtn.setOnClickListener(this);
        mAddManuallyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bar_code:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionUtil.getInstance().request(new String[]{Manifest.permission.CAMERA},
                            new PermissionResultCallBack() {

                                @Override
                                public void onPermissionGranted() {

                                }

                                @Override
                                public void onPermissionGranted(String... strings) {
                                    Intent intent = new Intent(getActivity(), CommonScanActivity.class);
                                    intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
                                    startActivity(intent);
                                }

                                @Override
                                public void onPermissionDenied(String... strings) {

                                }

                                @Override
                                public void onRationalShow(String... strings) {

                                }
                            });
                }else{
                    Intent intent = new Intent(getActivity(), CommonScanActivity.class);
                    intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
                    startActivity(intent);
                }



                break;

            case R.id.btn_add_manually:
                startActivity(new Intent(getActivity(), AddBookActivity.class));
                break;
        }
    }
}
