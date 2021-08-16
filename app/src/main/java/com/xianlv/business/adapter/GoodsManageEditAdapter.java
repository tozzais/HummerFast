package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsManageItemSku;

public class GoodsManageEditAdapter extends BaseQuickAdapter<GoodsManageItemSku, BaseViewHolder> implements LoadMoreModule {


    public GoodsManageEditAdapter() {
        super(R.layout.item_goods_manage_edit, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsManageItemSku item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_name,""+item.property)
                .setText(R.id.tv_price,"￥"+item.newPrice)
                .setText(R.id.tv_total,""+item.total);

   }



}
