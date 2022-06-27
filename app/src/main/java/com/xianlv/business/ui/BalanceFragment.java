package com.xianlv.business.ui;

import android.os.Bundle;
import android.view.View;


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
