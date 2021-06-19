package com.xianlv.business.adapter;


import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsOrderItem;
import com.xianlv.business.ui.activity.GoodsOrderDetailActivity;

public class GoodsOrderAdapter extends BaseQuickAdapter<GoodsOrderItem, BaseViewHolder> implements LoadMoreModule {


    public GoodsOrderAdapter() {
        super(R.layout.item_goods_order, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  GoodsOrderItem item) {
        int position = helper.getAdapterPosition();

        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        GoodsDetailSubAdapter adapter = new GoodsDetailSubAdapter();
        rv_goods.addItemDecoration(new LinearSpace());
        rv_goods.setAdapter(adapter);
        helper.setText(R.id.tv_order_number,"订单编号："+item.orderNo);
        adapter.setNewData(item.orderDetailTitleList);
        View view1 = helper.getView(R.id.ll_root);
        view1.setOnClickListener(view -> {
            GoodsOrderDetailActivity.launch(getContext(),item.orderId);
        });
        rv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return view1.onTouchEvent(event);
            }
        });

    }
}
