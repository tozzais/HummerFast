package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckDeductionItem;

public class CouponWriteOffAdapter extends BaseQuickAdapter<CheckDeductionItem, BaseViewHolder> implements LoadMoreModule {


    public CouponWriteOffAdapter() {
        super(R.layout.item_coupon_writeoff, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CheckDeductionItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_type,item.couponName)
                .setText(R.id.tv_code,item.viewVo)
                .setText(R.id.tv_desc,item.employVo)
                .setText(R.id.tv_time,"有效时间："+item.dayTimeVo);


   }



}
