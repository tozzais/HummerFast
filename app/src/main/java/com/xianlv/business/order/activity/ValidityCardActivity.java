package com.xianlv.business.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.order.fragment.ValidityOrderFragment;

/**
 * 有效期卡订单
 */
public class ValidityCardActivity extends BaseActivity {

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ValidityCardActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("有效期卡订单");
        setLineVisibility();

    }

    @Override
    public void loadData() {
        ValidityOrderFragment fragment = new ValidityOrderFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}