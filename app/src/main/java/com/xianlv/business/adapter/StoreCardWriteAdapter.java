package com.xianlv.business.adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckDeductionCardItem;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.ui.activity.ScanCodeDeductionActivity;

public class StoreCardWriteAdapter extends BaseQuickAdapter<CheckDeductionCardItem, BaseViewHolder> implements LoadMoreModule {


    public StoreCardWriteAdapter() {
        super(R.layout.item_store_card_write, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CheckDeductionCardItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.logo);

        String store = "";
        for (String s:item.shopNames){
            store = store+s;
        }
        helper.setText(R.id.tv_name,item.cardName)
                .setText(R.id.tv_card_number,"卡号："+item.cardUserNo)
                .setText(R.id.tv_name,"适用门店："+store);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            ScanCodeDeductionActivity.launch(getContext(),item.cardUserNo,"3","扫码扣款");
        });


   }



}
