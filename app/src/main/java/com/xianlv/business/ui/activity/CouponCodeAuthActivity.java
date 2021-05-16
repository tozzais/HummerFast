package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CouponCodeAuthFragment;

public class CouponCodeAuthActivity extends BaseActivity {



    public static void launch(Context from,int type) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, CouponCodeAuthActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("劵码验证");

    }

    @Override
    public void loadData() {
        CouponCodeAuthFragment fragment = CouponCodeAuthFragment.newInstance(getIntent().getIntExtra("type",0));
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }


}