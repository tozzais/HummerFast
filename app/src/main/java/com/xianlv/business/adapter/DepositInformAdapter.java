package com.xianlv.business.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.DepositItem;
import com.xianlv.business.global.ImageUtil;

public class DepositInformAdapter extends BaseQuickAdapter<DepositItem, BaseViewHolder> implements LoadMoreModule {


    public DepositInformAdapter() {
        super(R.layout.item_depositinform, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  DepositItem item) {
        int position = helper.getAdapterPosition();
        ImageView imageView = helper.getView(R.id.iv_goods);
        ImageUtil.loadFullAddress(getContext(),imageView,item.photo);
        helper.setText(R.id.tv_text1,item.name)
                .setText(R.id.tv_text2,"用户手机号："+item.userPhone)
                .setText(R.id.tv_text3,"存放时间："+item.time)
                .setText(R.id.tv_text4,"锁号："+item.lockNumber);



   }



}
