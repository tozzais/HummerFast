package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CashItem;
import com.xianlv.business.ui.activity.RefundActivity;

public class CashPledgeManageAdapter extends BaseQuickAdapter<CashItem, BaseViewHolder> implements LoadMoreModule {


    public CashPledgeManageAdapter() {
        super(R.layout.item_cashpledge_manage, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CashItem item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.tv_refund).setOnClickListener(view -> {
            RefundActivity.launch(getContext(),item.cashId);
        });

        helper.setText(R.id.tv_name,"用户名："+item.phone)
                .setText(R.id.tv_pay_time,"支付时间："+item.payTime)
                .setText(R.id.tv_pay_type,"支付类型："+item.roomOrderIdDesc)
                .setText(R.id.tv_pay_money,"￥："+item.payMoney)
                .setText(R.id.tv_remain_money,"￥："+item.money);




   }



}
