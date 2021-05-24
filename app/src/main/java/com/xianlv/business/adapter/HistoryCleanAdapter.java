package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CleanItem;

public class HistoryCleanAdapter extends BaseQuickAdapter<CleanItem, BaseViewHolder> implements LoadMoreModule {


    public HistoryCleanAdapter() {
        super(R.layout.item_history_clean, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CleanItem item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        View view2 = helper.getView(R.id.view2);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        String[] split = item.createTime.split(" ");
        String[] split1 = item.completeTime.split(" ");
        String time = "申请时间\n";
        for (String s:split){
            time += s+"\n";
        }
        time += "\n完成时间\n";
        for (String s:split1){
            time += s+"\n";
        }
        helper.setText(R.id.tv_time,time)
                .setText(R.id.tv_room_number,"房间号："+item.roomNo)
                .setText(R.id.tv_phone,"入住人手机号："+item.phone);

   }



}
