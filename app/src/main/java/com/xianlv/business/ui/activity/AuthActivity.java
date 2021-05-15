package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

public class AuthActivity extends BaseActivity {


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, AuthActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("认证");

    }

    @Override
    public void loadData() {

    }
}