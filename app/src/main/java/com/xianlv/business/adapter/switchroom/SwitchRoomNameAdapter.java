package com.xianlv.business.adapter.switchroom;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class SwitchRoomNameAdapter extends BaseQuickAdapter<String, BaseViewHolder>  {


    public SwitchRoomNameAdapter() {
        super(R.layout.item_switch_room_name, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_title,item);



   }




}
