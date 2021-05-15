package com.xianlv.business.adapter;


import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class MallCouponWriteOffAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public MallCouponWriteOffAdapter() {
        super(R.layout.item_write_off_mall, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        LinearLayout ll_header = helper.getView(R.id.ll_header);
        ll_header.setVisibility(position == 0 ? View.VISIBLE:View.GONE);


   }



}
