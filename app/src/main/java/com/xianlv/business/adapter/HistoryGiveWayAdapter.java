package com.xianlv.business.adapter;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class HistoryGiveWayAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public HistoryGiveWayAdapter() {
        super(R.layout.item_history_give_way, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);




   }



}
