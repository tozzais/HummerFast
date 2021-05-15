package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CheckOutFragment;
import com.xianlv.business.ui.fragment.DeductionDetailFragment;

public class DeductionDetailActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, DeductionDetailActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("明细");
        setLineVisibility();

    }

    @Override
    public void loadData() {
        DeductionDetailFragment fragment = new DeductionDetailFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();

    }
}