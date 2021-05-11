package com.xianlv.business.ui.activity;

import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.xianlv.business.R;

public class CheckInApplyActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("入住申请");

    }

    @Override
    public void loadData() {

    }
}