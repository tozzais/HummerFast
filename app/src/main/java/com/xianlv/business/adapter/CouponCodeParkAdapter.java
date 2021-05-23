package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckDeductionItem;

public class CouponCodeParkAdapter extends BaseQuickAdapter<CheckDeductionItem, BaseViewHolder> implements LoadMoreModule {


    public CouponCodeParkAdapter() {
        super(R.layout.item_coupon_code_park, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CheckDeductionItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text1,"房间信息："+item.roomNumber+"\n有效时间："+item.effectiveTime);

   }



}
