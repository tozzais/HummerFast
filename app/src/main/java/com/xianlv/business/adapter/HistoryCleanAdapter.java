package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class HistoryCleanAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public HistoryCleanAdapter() {
        super(R.layout.item_history_clean, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        View view2 = helper.getView(R.id.view2);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        view2.setVisibility(View.VISIBLE);

   }



}
