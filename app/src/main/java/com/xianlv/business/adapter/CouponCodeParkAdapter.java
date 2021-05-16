package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class CouponCodeParkAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public CouponCodeParkAdapter() {
        super(R.layout.item_coupon_code_park, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();


   }



}
