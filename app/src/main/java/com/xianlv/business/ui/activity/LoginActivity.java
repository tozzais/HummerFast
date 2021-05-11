package com.xianlv.business.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(1);
        tvTitle.setText("登录");
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());
    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_register, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                break;
            case R.id.tv_login:
                MainActivity.launch(mActivity, "");
                break;

        }
    }


}