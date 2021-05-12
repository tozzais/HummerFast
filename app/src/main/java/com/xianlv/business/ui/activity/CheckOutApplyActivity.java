package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CheckOutFragment;
import com.xianlv.business.ui.fragment.CollectionRecordFragment;

public class CheckOutApplyActivity extends BaseActivity {

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, CheckOutApplyActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("离店申请");
        setLineVisibility();

    }

    @Override
    public void loadData() {
        CheckOutFragment fragment = new CheckOutFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();

    }
}