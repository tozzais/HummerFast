package com.xianlv.business.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.MealOrderItem;
import com.xianlv.business.global.ImageUtil;

public class SendFoodReminderSubAdapter extends BaseQuickAdapter<MealOrderItem.Goods, BaseViewHolder> implements LoadMoreModule {


    public SendFoodReminderSubAdapter() {
        super(R.layout.item_send_food_sub, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  MealOrderItem.Goods item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.pics);
        helper.setText(R.id.tv_title,item.foodName)
                .setText(R.id.tv_special,item.skuName)
                .setText(R.id.tv_money,item.skuPrice)
                .setText(R.id.tv_number,"x"+item.num);


   }



}
