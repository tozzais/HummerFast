package com.xianlv.business.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsOrderItem;
import com.xianlv.business.global.ImageUtil;

public class GoodsDetailSubAdapter extends BaseQuickAdapter<GoodsOrderItem.Goods, BaseViewHolder> implements LoadMoreModule {


    public GoodsDetailSubAdapter() {
        super(R.layout.item_goods_order_sub, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsOrderItem.Goods item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.img);
        helper.setText(R.id.tv_title,item.productName)
                .setText(R.id.tv_special,"规格："+item.propertyName)
                .setText(R.id.tv_number,"数量：*"+item.quantity);


   }



}
