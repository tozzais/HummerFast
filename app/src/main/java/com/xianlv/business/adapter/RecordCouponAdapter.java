package com.xianlv.business.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class RecordCouponAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public RecordCouponAdapter() {
        super(R.layout.item_record_coupon, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();



   }



}
