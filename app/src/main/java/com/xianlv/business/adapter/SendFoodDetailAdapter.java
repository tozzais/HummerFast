package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class SendFoodDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SendFoodDetailAdapter() {
        super(R.layout.item_send_food_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();


   }



}
