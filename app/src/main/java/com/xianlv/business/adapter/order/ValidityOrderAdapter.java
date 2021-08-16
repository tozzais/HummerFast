package com.xianlv.business.adapter.order;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.order.ValidityOrderItem;

public class ValidityOrderAdapter extends BaseQuickAdapter<ValidityOrderItem, BaseViewHolder> implements LoadMoreModule {


    public ValidityOrderAdapter() {
        super(R.layout.item_validity_order, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ValidityOrderItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,"储值卡号："+item.validityNo)
                .setText(R.id.tv_text2,item.validityName)
                .setText(R.id.tv_text3,"会员名称："+item.nickname)
                .setText(R.id.tv_text4,"会员登记："+item.levelName)
                .setText(R.id.tv_text5,"激活人："+ (TextUtils.isEmpty(item.name)?"":item.name))
                .setText(R.id.tv_text6,"购买次数："+item.validityNum)
                .setText(R.id.tv_text7,"剩余次数："+(TextUtils.isEmpty(item.residueNum)?"":item.residueNum))
                .setText(R.id.tv_text8,"购买时间："+item.createtime)
                .setText(R.id.tv_text9,"激活状态："+item.statusDesc)
                .setText(R.id.tv_text10,"有效期："+(TextUtils.isEmpty(item.vaildtimeVo)?"":item.vaildtimeVo));

    }
}
