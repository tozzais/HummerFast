package com.xianlv.business.adapter;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.util.DpUtil;
import com.xianlv.business.R;
import com.xianlv.business.bean.MealOrderItem;
import com.xianlv.business.ui.activity.DeliveryDetailActivity;

import java.util.List;

public class SendFoodReminderAdapter extends BaseQuickAdapter<MealOrderItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public SendFoodReminderAdapter(int type) {
        super(R.layout.item_send_food_reminder, null);
        this.type = type;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  MealOrderItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_open = helper.getView(R.id.tv_open);

        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        SendFoodReminderSubAdapter adapter = new SendFoodReminderSubAdapter();
        rv_goods.setAdapter(adapter);
        List<MealOrderItem.Goods> detailVOS = item.detailVOS;
        String openStr = "展开(共"+ detailVOS.size()+"件)";
        String closeStr = "收起(共"+ detailVOS.size()+"件)";
        helper.setText(R.id.tv_order_number,"订单编号："+item.orderNo)
                .setText(R.id.tv_room_number,"房间号："+item.consumer.roomNo)
                .setText(R.id.tv_time,"下单时间："+item.createTime)
                .setText(R.id.tv_name,"用户名："+item.consumer.consumerName)
                .setText(R.id.tv_phone,"手机号："+item.consumer.consumerPhone)
                .setText(R.id.tv_money,"￥"+item.payMoney)
                .setText(R.id.tv_open,openStr);
        if (detailVOS.size() > 3){
            tv_open.setVisibility(View.VISIBLE);
        }else {
            tv_open.setVisibility(View.GONE);
        }
        adapter.setNewData(detailVOS);
        int itemHeight = DpUtil.dip2px(getContext(),60);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv_goods.getLayoutParams();
        params.height = itemHeight*(detailVOS.size() > 3?3:detailVOS.size());
        rv_goods.setLayoutParams(params);
        tv_open.setOnClickListener(view -> {
            if (tv_open.getText().toString().equals(openStr)){
                params.height = itemHeight*detailVOS.size();
                rv_goods.setLayoutParams(params);
                tv_open.setText(closeStr);
                 Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_up_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(null, null, drawable, null);
            }else {
                params.height = itemHeight*3;
                rv_goods.setLayoutParams(params);
                tv_open.setText(openStr);
                Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_down_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(null, null, drawable, null);
            }
        });
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            DeliveryDetailActivity.launch(getContext(),item.orderId,type);
        });

        TextView tv_cancel = helper.getView(R.id.tv_cancel);
        TextView tv_sure = helper.getView(R.id.tv_sure);
        if (type == 123){
            tv_sure.setText("确认订单");
            tv_cancel.setText("取消订单");
        }else if (type == 456){
            tv_cancel.setVisibility(View.GONE);
            tv_sure.setText("确认送达");
        }else if (type == 0){
            tv_cancel.setVisibility(View.GONE);
            tv_sure.setVisibility(View.GONE);
        }

        View view1 = helper.getView(R.id.ll_root);
        rv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return view1.onTouchEvent(event);
            }
        });

    }
}
