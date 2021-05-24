package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CallMorningItem;

public class CallMorningAdapter extends BaseQuickAdapter<CallMorningItem, BaseViewHolder> implements LoadMoreModule {


    public CallMorningAdapter() {
        super(R.layout.item_collection_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CallMorningItem item) {
        int position = helper.getAdapterPosition();
        View view = helper.getView(R.id.view1);
        View tv_number = helper.getView(R.id.tv_number);
        tv_number.setVisibility(View.GONE);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        helper.setText(R.id.tv_time,"叫早时间："+item.callDay+item.timePoint)
                .setText(R.id.tv_number,"房间号："+item.roomNumber);

    }



}
