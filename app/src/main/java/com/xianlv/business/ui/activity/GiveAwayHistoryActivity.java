package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CollectionRecordFragment;
import com.xianlv.business.ui.fragment.HistoryGiveWayFragment;

public class GiveAwayHistoryActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, GiveAwayHistoryActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content_white;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("送物历史");

    }

    @Override
    public void loadData() {
        HistoryGiveWayFragment fragment = new HistoryGiveWayFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();

    }
}