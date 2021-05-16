package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class CouponCodeBreakfastAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public CouponCodeBreakfastAdapter() {
        super(R.layout.item_coupon_code_breakfast, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();


   }



}
