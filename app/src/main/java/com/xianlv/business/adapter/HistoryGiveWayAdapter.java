package com.xianlv.business.adapter;


import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GiveWayItem;

public class HistoryGiveWayAdapter extends BaseQuickAdapter<GiveWayItem, BaseViewHolder> implements LoadMoreModule {


    public HistoryGiveWayAdapter() {
        super(R.layout.item_history_give_way, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GiveWayItem item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);

        String[] split = item.createTime.split(" ");
        String time = "申请时间\n";
        for (String s:split){
            time += s+"\n";
        }
        time += "\n送达时间\n";
        if (!TextUtils.isEmpty(item.deliveryTime)){
            String[] split1 = item.deliveryTime.split(" ");
            for (String s:split1){
                time += s+"\n";
            }
        }

        helper.setText(R.id.tv_time,time)
                .setText(R.id.tv_room_number,"房间号："+item.roomNo)
                .setText(R.id.tv_goods,"房间号："+item.details)
                .setText(R.id.tv_phone,"入住人手机号："+item.phone);




   }



}
