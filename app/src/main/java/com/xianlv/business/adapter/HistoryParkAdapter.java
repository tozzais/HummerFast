package com.xianlv.business.adapter;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class HistoryParkAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public HistoryParkAdapter() {
        super(R.layout.item_history_park, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        TextView tv_status = helper.getView(R.id.tv_status);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        if (position%2 == 0){
            tv_status.setText("状态：已通过");
            tv_status.setTextColor(getContext().getResources().getColor(R.color.green_text));
        }else {
            tv_status.setText("状态：已拒绝");
            tv_status.setTextColor(getContext().getResources().getColor(R.color.red_text));
        }



   }



}
