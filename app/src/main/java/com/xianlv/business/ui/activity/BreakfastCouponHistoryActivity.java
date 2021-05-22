package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.HistoryBreakfastFragment;

public class BreakfastCouponHistoryActivity extends BaseActivity {



    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, BreakfastCouponHistoryActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content_white;
    }

    private int type;
    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",1);
        if (type == 1){
            setBackTitle("早餐券历史");
        }else {
            setBackTitle("停车券历史");
        }

    }

    @Override
    public void loadData() {
        HistoryBreakfastFragment fragment = HistoryBreakfastFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();

    }
}