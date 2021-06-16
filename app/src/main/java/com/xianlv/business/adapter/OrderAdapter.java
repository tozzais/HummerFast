package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.RoomOrderItem;
import com.xianlv.business.ui.activity.OrderDetailActivity;

public class OrderAdapter extends BaseQuickAdapter<RoomOrderItem, BaseViewHolder> implements LoadMoreModule {


    public OrderAdapter() {
        super(R.layout.item_order, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  RoomOrderItem item) {
        int position = helper.getAdapterPosition();

        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            OrderDetailActivity.launch(getContext(),item.roomOrderId);
        });
        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text2,""+item.shopName)
                .setText(R.id.tv_text3,"入住时间："+item.timeDesc)
                .setText(R.id.tv_text4,"房型："+item.roomName);


   }



}
