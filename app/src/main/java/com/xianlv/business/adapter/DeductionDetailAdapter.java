package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CardReduceDetail;

public class DeductionDetailAdapter extends BaseQuickAdapter<CardReduceDetail, BaseViewHolder> implements LoadMoreModule {


    public DeductionDetailAdapter() {
        super(R.layout.item_deduction_detail, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,  CardReduceDetail item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_name,item.nickname)
                .setText(R.id.tv_time,item.createtime)
                .setText(R.id.tv_level,item.cardName)
                .setText(R.id.tv_money,"￥"+item.balance)
                .setText(R.id.tv_address,"营业点："+item.department)
                .setText(R.id.tv_remark,"备注："+item.remark)
                .setText(R.id.tv_card_number,"卡号："+item.cardUserNo)
                .setText(R.id.tv_money1,"￥"+item.rechargeAmount)
                .setText(R.id.tv_money2,"￥"+item.givenAmount)
                .setText(R.id.tv_money3,"￥"+item.balance);


   }



}
