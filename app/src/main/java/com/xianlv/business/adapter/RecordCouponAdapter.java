package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.WriteOffHistoryItem;

public class RecordCouponAdapter extends BaseQuickAdapter<WriteOffHistoryItem, BaseViewHolder> implements LoadMoreModule {


    public RecordCouponAdapter() {
        super(R.layout.item_record_coupon, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  WriteOffHistoryItem item) {
        int position = helper.getAdapterPosition();



   }



}
