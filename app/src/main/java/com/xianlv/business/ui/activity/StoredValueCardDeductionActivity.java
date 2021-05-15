package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CouponWriteOffFragment;
import com.xianlv.business.ui.fragment.StoredCardWriteOffFragment;

public class StoredValueCardDeductionActivity extends BaseActivity {




    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, StoredValueCardDeductionActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("储值卡扣款");

    }

    @Override
    public void loadData() {
        StoredCardWriteOffFragment fragment = new StoredCardWriteOffFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}