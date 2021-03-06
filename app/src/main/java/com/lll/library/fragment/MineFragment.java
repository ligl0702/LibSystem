package com.lll.library.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.activity.ChangePwdActivity;
import com.lll.library.activity.FeedBackActivity;
import com.lll.library.activity.LoginActivity;
import com.lll.library.util.Constant;
import com.lll.library.util.SpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by guoliangli on 2016/8/22.
 */

public class MineFragment extends Fragment {


    private Context context;
    private View contentView;
    @Bind(R.id.rl_userinfo)
    RelativeLayout rl_userinfo;
    @Bind(R.id.ll_main_feedback)
    LinearLayout ll_main_feedback;
    @Bind(R.id.ll_version_update)
    LinearLayout ll_version_update;
    @Bind(R.id.ll_contact_us)
    LinearLayout ll_contact_us;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.bt_logout)
    Button bt_logout;
    @Bind(R.id.iv_headview)
    ImageView iv_headview;
    @Bind(R.id.ll_change_password)
    LinearLayout ll_change_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        if (contentView == null) {
            initUI(inflater);
        }
        if (contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        return contentView;
    }

    private void initUI(LayoutInflater inflater) {
        contentView = inflater.inflate(R.layout.mine_fragment_layout, null);
        ButterKnife.bind(this, contentView);
        tv_name.setText("用户名");

        rl_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(context, UserDeatailsActivity.class));
            }
        });


        ll_main_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, FeedBackActivity.class));
            }
        });

        ll_version_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "已经是最新版", Toast.LENGTH_SHORT).show();
            }
        });

        ll_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("联系我们");
                builder.setMessage(getResources().getString(R.string.contact_us_str));
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        ll_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePwdActivity.class));
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLogoutRequest();

            }
        });
    }


    private void sendLogoutRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.confirm_logout));
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpUtil.putString(context, Constant.USER_PWD, "");
                SpUtil.putBoolean(context, Constant.IS_REMEMBER_PWD, false);
                Toast.makeText(context, "已注销", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
}
