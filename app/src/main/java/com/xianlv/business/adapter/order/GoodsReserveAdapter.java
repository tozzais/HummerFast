package com.xianlv.business.adapter.order;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.order.ReserveOrderItem;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.order.activity.ReserveOrderDetailActivity;

public class GoodsReserveAdapter extends BaseQuickAdapter<ReserveOrderItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public GoodsReserveAdapter(int type) {
        super(R.layout.item_goods_reserve, null);
        this.type = type;
    }

    public GoodsReserveAdapter() {
        super(R.layout.item_goods_reserve, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ReserveOrderItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text2,item.statusName)
                .setText(R.id.tv_text3,""+item.appointmentName)
                .setText(R.id.tv_text4,"规格："+item.property)
                .setText(R.id.tv_text6,"数量：*"+item.people);
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.img);

        helper.getView(R.id.view).setVisibility(type == 1? View.VISIBLE:View.GONE);
        helper.getView(R.id.ll_bottom).setVisibility(type == 1? View.VISIBLE:View.GONE);

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            ReserveOrderDetailActivity.launch(getContext(),item.appointmentOrderId,2,type);
        });

    }
}
