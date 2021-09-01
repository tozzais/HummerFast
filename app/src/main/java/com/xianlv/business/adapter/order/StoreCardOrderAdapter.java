package com.xianlv.business.adapter.order;


import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.order.StoreCardOrderItem;

public class StoreCardOrderAdapter extends BaseQuickAdapter<StoreCardOrderItem, BaseViewHolder> implements LoadMoreModule {


    public StoreCardOrderAdapter() {
        super(R.layout.item_store_card_order, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  StoreCardOrderItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,"储值卡号："+item.cardUserNo)
                .setText(R.id.tv_text2,item.cardName)
                .setText(R.id.tv_text3,"会员名称："+item.nickname)
                .setText(R.id.tv_text4,"会员登记："+item.levelName)
                .setText(R.id.tv_text5,"充值金额：￥"+item.rechargeAmount)
                .setText(R.id.tv_text6,"赠送金额："+item.givenAmount)
                .setText(R.id.tv_text7,"购买时间："+item.createtime);

    }
}
