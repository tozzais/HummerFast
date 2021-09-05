package com.xianlv.business.util.datapick;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;


/**
 *
 */
public class DataRecycleAdapter extends BaseQuickAdapter<DataPickItem, BaseViewHolder> {

    public DataRecycleAdapter() {
        super(R.layout.item_datapick, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final DataPickItem dataPickItem) {
        int position = helper.getAdapterPosition();
        TextView tv_data = helper.getView(R.id.tv_data);
        tv_data.setText(dataPickItem.data);

        tv_data.setOnClickListener(v -> {
            if (dataPickItem.isClick){
                dataPickItem.isSelete = !dataPickItem.isSelete;
                notifyDataSetChanged();
            }
        });
        if (dataPickItem.isSelete){
            tv_data.setBackgroundResource(R.drawable.shape_oval_basecolor);
            tv_data.setTextColor(getContext().getResources().getColor(R.color.white));
        }else {
            tv_data.setBackgroundResource(R.drawable.shape_white5);
            if (dataPickItem.isClick){
                tv_data.setTextColor(getContext().getResources().getColor(R.color.black_title_color));
            }else {
                tv_data.setTextColor(getContext().getResources().getColor(R.color.gray_line));

            }

        }


    }



}
