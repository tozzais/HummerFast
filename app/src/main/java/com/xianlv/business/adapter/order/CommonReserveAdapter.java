package com.xianlv.business.adapter.order;


import android.annotation.SuppressLint;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.order.ReserveOrderItem;

public class CommonReserveAdapter extends BaseQuickAdapter<ReserveOrderItem, BaseViewHolder> implements LoadMoreModule {



    private int type;
    public CommonReserveAdapter(int type) {
        super(R.layout.item_common_reserve, null);
        this.type = type;
    }

    public CommonReserveAdapter() {
        super(R.layout.item_common_reserve, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ReserveOrderItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text2,item.statusName)
                .setText(R.id.tv_text3,""+item.appointmentName)
                .setText(R.id.tv_text4,"联系人："+item.truename)
                .setText(R.id.tv_text5,"联系电话："+item.phone)
                .setText(R.id.tv_money,"价格：："+item.appointPrice);
            helper.getView(R.id.view).setVisibility(type == 1? View.VISIBLE:View.GONE);
            helper.getView(R.id.ll_bottom).setVisibility(type == 1? View.VISIBLE:View.GONE);

    }
}
