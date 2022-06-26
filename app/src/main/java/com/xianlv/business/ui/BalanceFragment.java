package com.xianlv.business.ui;

import android.os.Bundle;
import android.view.View;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.R;


public class BalanceFragment extends ChargeFragment  {

    public static BalanceFragment newInstance(String url){
        BalanceFragment balanceFragment = new BalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        balanceFragment.setArguments(bundle);
        return balanceFragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolbar.setVisibility(View.VISIBLE);
        tv_title.setText("充值");
    }
}
