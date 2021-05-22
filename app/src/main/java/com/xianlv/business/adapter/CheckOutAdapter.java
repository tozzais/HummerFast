package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckOutItem;

public class CheckOutAdapter extends BaseQuickAdapter<CheckOutItem, BaseViewHolder> implements LoadMoreModule {


    public CheckOutAdapter() {
        super(R.layout.item_checkout, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CheckOutItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,"房间号："+item.roomNumber)
                .setText(R.id.tv_text2,item.failureTime)
                .setText(R.id.tv_text3,"入住人手机号："+item.phone);


   }



}
