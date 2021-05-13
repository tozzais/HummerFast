package com.xianlv.business.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class RankAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public RankAdapter() {
        super(R.layout.item_rank, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        TextView tv_sign = helper.getView(R.id.tv_sign);
        TextView tv_number4 = helper.getView(R.id.tv_number4);

        tv_sign.setText("No."+(position+1));
        if (position == 0){
            tv_sign.setTextColor(getContext().getResources().getColor(R.color.white));
            tv_number4.setTextColor(getContext().getResources().getColor(R.color.yellow1));
            tv_sign.setBackgroundResource(R.mipmap.icon_rectangle1);
        }else if (position == 1){
            tv_sign.setTextColor(getContext().getResources().getColor(R.color.white));
            tv_sign.setBackgroundResource(R.mipmap.icon_rectangle2);
            tv_number4.setTextColor(getContext().getResources().getColor(R.color.lnkgray));
        }else if (position == 2){
            tv_sign.setTextColor(getContext().getResources().getColor(R.color.white));
            tv_sign.setBackgroundResource(R.mipmap.icon_rectangle3);
            tv_number4.setTextColor(getContext().getResources().getColor(R.color.yellow2));
        }else {
            tv_sign.setTextColor(getContext().getResources().getColor(R.color.red));
            tv_sign.setBackground(null);
        }


   }



}
