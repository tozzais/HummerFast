package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.ui.activity.RefundActivity;

public class CashPledgeManageAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public CashPledgeManageAdapter() {
        super(R.layout.item_cashpledge_manage, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.tv_refund).setOnClickListener(view -> {
            RefundActivity.launch(getContext(),0);
        });


   }



}
