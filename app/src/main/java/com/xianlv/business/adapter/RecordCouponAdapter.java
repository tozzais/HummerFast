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
        helper.setText(R.id.tv_text1,"金额：￥"+item.money)
                .setText(R.id.tv_text2,""+item.createtime)
                .setText(R.id.tv_text3,"姓名："+item.nickname)
                .setText(R.id.tv_text4,"等级："+item.levelName)
                .setText(R.id.tv_text5,"核销员："+item.vername)
                .setText(R.id.tv_text6,""+item.viewVo)
                .setText(R.id.tv_text7,""+item.couponName);



   }



}
