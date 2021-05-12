package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class BreakfastCouponAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public BreakfastCouponAdapter() {
        super(R.layout.item_breakfast_coupon, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();


   }



}
