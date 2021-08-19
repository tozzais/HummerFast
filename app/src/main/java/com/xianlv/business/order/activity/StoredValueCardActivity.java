package com.xianlv.business.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.order.fragment.StoredValueCardOrderFragment;

/**
 * 储值卡订单
 */
public class StoredValueCardActivity extends BaseActivity {

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, StoredValueCardActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("储值卡订单");

    }

    @Override
    public void loadData() {
        StoredValueCardOrderFragment fragment = new StoredValueCardOrderFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}