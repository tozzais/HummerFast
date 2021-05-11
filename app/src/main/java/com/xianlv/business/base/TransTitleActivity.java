package com.xianlv.business.base;

import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.xianlv.business.R;

public class TransTitleActivity extends BaseActivity {



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

    }

    @Override
    public void loadData() {

    }


}