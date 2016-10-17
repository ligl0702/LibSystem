package com.lll.library.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.entity.FeedBack;
import com.lll.library.entity.MyUser;
import com.lll.library.util.Constant;
import com.lll.library.util.SpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liguoliang on 16/10/17.
 */
public class FeedBackActivity extends BaseActivity{

    @Bind(R.id.my_opinion)
    EditText et_my_opinion;
    @Bind(R.id.title_bar_right)
    TextView title_bar_right;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        context=this;
        ButterKnife.bind(this);
        title_bar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendFeedBack();

            }
        });
    }

    private void sendFeedBack(){
        FeedBack feedBack = new FeedBack();
        feedBack.feed=et_my_opinion.getText().toString();
        feedBack.userId= SpUtil.getString(context,Constant.USER_NAME,"");
        if(TextUtils.isEmpty(feedBack.feed)){
            showToast("您还没有输入文字");
        }else{
            feedBack.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        showToast("反馈成功");
                        finish();
                    }else{
                        showToast("反馈失败：" + e.getMessage());
                    }
                }
            });
        }

    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
