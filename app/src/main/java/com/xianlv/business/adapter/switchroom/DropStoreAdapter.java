package com.xianlv.business.adapter.switchroom;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.switchroom.ShopBean;

/**
 * 下拉筛选门店
 */
public class DropStoreAdapter extends BaseQuickAdapter<ShopBean, BaseViewHolder>  {


    public DropStoreAdapter() {
        super(R.layout.item_drop_store, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  ShopBean item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text2,item.shopName);



   }




}
