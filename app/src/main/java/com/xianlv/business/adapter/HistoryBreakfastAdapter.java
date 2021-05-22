package com.xianlv.business.adapter;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CouponHistoryItem;

public class HistoryBreakfastAdapter extends BaseQuickAdapter<CouponHistoryItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public HistoryBreakfastAdapter(int type) {
        super(R.layout.item_history_breakfast, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  CouponHistoryItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_type = helper.getView(R.id.tv_type);
        if (type == 1){
            tv_type.setVisibility(View.VISIBLE);
        }else {
            tv_type.setVisibility(View.GONE);
        }
        View view = helper.getView(R.id.view1);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        if (item.useStatus.contains("通过")){
            tv_status.setTextColor(getContext().getResources().getColor(R.color.green_text));
        }else {
            tv_status.setTextColor(getContext().getResources().getColor(R.color.red_text));
        }
        tv_status.setText("状态："+item.useStatus);
        helper.setText(R.id.tv_apply_time,"申请时间\n"+item.applicationTime+"\n审核时间\n"+item.auditTime)
                .setText(R.id.tv_room_number,"房间号："+item.roomNumber)
                .setText(R.id.tv_phone,"入住人手机号："+item.userPhone)
                .setText(R.id.tv_type,"类型："+item.breakfastTypesOf)
                .setText(R.id.tv_number,"申请数量："+item.count);





   }



}
