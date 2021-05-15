package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

public class DeductionResultActivity extends BaseActivity {



    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, DeductionResultActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_dedution_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("储值卡扣款");

    }

    @Override
    public void loadData() {

    }
}