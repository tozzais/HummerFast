package com.xianlv.business.adapter.switchroom;


import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.switchroom.SwitchRoomShowData;


/**
 * 房型右侧的适配器
 */
public class SwitchRoomNameManageAdapter extends BaseQuickAdapter<SwitchRoomShowData, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public SwitchRoomNameManageAdapter(int type) {
        super(R.layout.item_switch_room, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  SwitchRoomShowData item) {
        int position = helper.getAdapterPosition();
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.data.getDate());

        RecyclerView rv_list = helper.getView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        if (type == 0){
            SwitchRoomDetailAdapter adapter = new SwitchRoomDetailAdapter();
            rv_list.setAdapter(adapter);
            adapter.setNewData(item.list);
        }else if (type == 1){
            RoomPriceManageDetailAdapter adapter = new RoomPriceManageDetailAdapter();
            rv_list.setAdapter(adapter);
            adapter.setNewData(item.list);
        }





   }



}
