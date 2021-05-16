package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthResultActivity extends BaseActivity {


    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_home)
    TextView tvHome;

    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, AuthResultActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("劵码验证");
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            ivResult.setImageResource(R.mipmap.auth_success);
            tvResult.setText("验证成功");
            tvHome.setVisibility(View.GONE);
        }else {
            ivResult.setImageResource(R.mipmap.auth_failed);
            tvResult.setText("验证失败");
            tvHome.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadData() {

    }

    @OnClick(R.id.tv_home)
    public void onClick() {
        MainActivity.launch(mActivity);
    }
}