package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class ParkCouponAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public ParkCouponAdapter() {
        super(R.layout.item_park_coupon, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();


   }



}
