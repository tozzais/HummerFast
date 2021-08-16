package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsManageItemSku;

public class GoodsManageSecondAdapter extends BaseQuickAdapter<GoodsManageItemSku, BaseViewHolder> implements LoadMoreModule {


    public GoodsManageSecondAdapter() {
        super(R.layout.item_goods_manage_speci, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsManageItemSku item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_special,""+item.property)
                .setText(R.id.tv_price,"￥"+item.newPrice)
                .setText(R.id.tv_number,""+item.total);

   }



}
