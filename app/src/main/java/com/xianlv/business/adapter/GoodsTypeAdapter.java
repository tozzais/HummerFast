package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsTypeItem;

public class GoodsTypeAdapter extends BaseQuickAdapter<GoodsTypeItem, BaseViewHolder>  {


    public GoodsTypeAdapter() {
        super(R.layout.item_goods_type, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsTypeItem item) {
        int position = helper.getAdapterPosition();



   }




}
