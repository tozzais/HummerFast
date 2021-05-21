package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CollectionHistoryItem;

public class CollectionRecordAdapter extends BaseQuickAdapter<CollectionHistoryItem, BaseViewHolder> implements LoadMoreModule {


    public CollectionRecordAdapter() {
        super(R.layout.item_collection_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CollectionHistoryItem item) {
        int position = helper.getAdapterPosition();
        View view = helper.getView(R.id.view1);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);

        helper.setText(R.id.tv_time,item.createtime)
                .setText(R.id.tv_name,item.nickname)
                .setText(R.id.tv_number,"+"+item.money);

    }



}
