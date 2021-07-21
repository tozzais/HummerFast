package com.xianlv.business.adapter;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.SunmiPrint;
import com.xianlv.business.bean.ReceiveOrderItem;

public class ReceivePayOrderAdapter extends BaseQuickAdapter<ReceiveOrderItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public ReceivePayOrderAdapter(int type) {
        super(R.layout.item_receive_pay_order, null);
        this.type = type;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ReceiveOrderItem item) {
        int position = helper.getAdapterPosition();

//        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
//        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
//        SendFoodReminderSubAdapter adapter = new SendFoodReminderSubAdapter();
//        rv_goods.setAdapter(adapter);
//        List<MealOrderItem.Goods> detailVOS = item.detailVOS;
//        String openStr = "展开(共"+ detailVOS.size()+"件)";
//        String closeStr = "收起(共"+ detailVOS.size()+"件)";
       TextView tv_text3 = helper.getView(R.id.tv_text3);
       TextView tv_text8 = helper.getView(R.id.tv_text8);
       TextView tv_text10 = helper.getView(R.id.tv_text10);
       TextView tv_text9 = helper.getView(R.id.tv_text9);
        if (type == 1){
            helper.setVisible(R.id.tv_text2, false);
            tv_text9.setVisibility(View.GONE);
            tv_text10.setVisibility(View.GONE);
            tv_text3.setText("收款金额："+item.money);
        }
        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text8,"收款类型："+item.purposeStatus)
                .setText(R.id.tv_text2,"房间号："+item.getReturnStatus())
                .setText(R.id.tv_text4,"付款人："+item.nickname)
                .setText(R.id.tv_text5,"收款员工："+item.operuser+" "+item.verPhone)
                .setText(R.id.tv_text6,"收款时间："+item.payTime)
                .setText(R.id.tv_text7,"备注"+item.remark);
        helper.getView(R.id.tv_pass).setOnClickListener(v -> {
            SunmiPrint.INSTANCE.printReceipt(getContext(),item,type);
        });

//        if (detailVOS.size() > 3){
//            tv_open.setVisibility(View.VISIBLE);
//        }else {
//            tv_open.setVisibility(View.GONE);
//        }
//        adapter.setNewData(detailVOS);
//        int itemHeight = DpUtil.dip2px(getContext(),60);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv_goods.getLayoutParams();
//        params.height = itemHeight*(detailVOS.size() > 3?3:detailVOS.size());
//        rv_goods.setLayoutParams(params);
//        tv_open.setOnClickListener(view -> {
//            if (tv_open.getText().toString().equals(openStr)){
//                params.height = itemHeight*detailVOS.size();
//                rv_goods.setLayoutParams(params);
//                tv_open.setText(closeStr);
//                 Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_up_gray);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv_open.setCompoundDrawables(null, null, drawable, null);
//            }else {
//                params.height = itemHeight*3;
//                rv_goods.setLayoutParams(params);
//                tv_open.setText(openStr);
//                Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_down_gray);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tv_open.setCompoundDrawables(null, null, drawable, null);
//            }
//        });
//        helper.getView(R.id.ll_root).setOnClickListener(view -> {
//            DeliveryDetailActivity.launch(getContext(),item.orderId,type);
//        });
//
//        TextView tv_cancel = helper.getView(R.id.tv_cancel);
//        TextView tv_sure = helper.getView(R.id.tv_sure);
//        if (type == 2){
//            tv_cancel.setVisibility(View.GONE);
//            tv_sure.setText("确认送达");
//        }
//
//        View view1 = helper.getView(R.id.ll_root);
//        rv_goods.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return view1.onTouchEvent(event);
//            }
//        });

    }
}
