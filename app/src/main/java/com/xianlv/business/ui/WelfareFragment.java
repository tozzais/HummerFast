package com.xianlv.business.ui;

import android.os.Bundle;
import android.view.View;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.R;


public class WelfareFragment extends ChargeFragment  {


    public static WelfareFragment newInstance(String url){
        WelfareFragment balanceFragment = new WelfareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        balanceFragment.setArguments(bundle);
        return balanceFragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolbar.setVisibility(View.VISIBLE);
        tv_title.setText("福利活动");
    }



}
