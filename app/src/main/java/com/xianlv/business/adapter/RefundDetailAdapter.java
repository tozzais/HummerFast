package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CashDetailItem;

public class RefundDetailAdapter extends BaseQuickAdapter<CashDetailItem, BaseViewHolder> implements LoadMoreModule {


    public RefundDetailAdapter() {
        super(R.layout.item_refund_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CashDetailItem item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        View view2 = helper.getView(R.id.view2);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        view2.setVisibility(View.VISIBLE);

        String time = "";
        String[] split = item.time.split(" ");
        for (String s :split) {
            time = time+s+"\n";
        }
        helper.setText(R.id.tv_time,time)
                .setText(R.id.tv_name,item.payment)
                .setText(R.id.tv_content,"支付类型："+item.accountDesc)
                .setText(R.id.tv_money,"-"+item.money);



   }



}
