package com.xianlv.business.ui.activity;

import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.xianlv.business.R;

public class StudyEffortActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("学习成就");

    }

    @Override
    public void loadData() {

    }
}