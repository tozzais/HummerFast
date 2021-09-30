package com.xianlv.business.adapter.switchroom;


import android.widget.Switch;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.xianlv.business.R;
import com.xianlv.business.bean.switchroom.SwitchDetailItem;
import com.xianlv.business.bean.switchroom.SwitchRoomItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.ListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.CenterDialogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 每个日期下面 不同房型(包括促销方案)对应的数据适配器
 */
public class SwitchRoomDetailAdapter extends BaseQuickAdapter<SwitchDetailItem, BaseViewHolder>  {


    public SwitchRoomDetailAdapter() {
        super(R.layout.item_switch_room_data, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  SwitchDetailItem item) {
        helper.setText(R.id.tv_price,"￥"+item.price)
                .setText(R.id.tv_inventory,"库存:"+item.store);
        Switch close = helper.getView(R.id.vibrate_switch);
        close.setChecked(item.isOpen());
        close.setOnClickListener(v -> {
            close.setChecked(item.isOpen());
            switchSwitch(item);
        });



   }

    /**
     * 切换开关房开关
     * @param item
     */
   private void switchSwitch(SwitchDetailItem item){
       String title = "";
       String content = "";
       if (item.isOpen()){
           title = "确认关房";
           content = "是否将"+item.roomName+"的房间状态更改为关？";
       }else {
           title = "确认开房";
           content = "是否将"+item.roomName+"的房间状态更改为开？";

       }
       CenterDialogUtil.show(getContext(),title,content,s->{
           if ("1".equals(s)){
               Map<String,String> map = new HashMap<>();
               map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
               map.put("roomStoreId", item.roomStoreId);
               map.put("status",""+(item.isOpen()?1:0));
               new RxHttp<BaseResult<ListResult<SwitchRoomItem>>>().send(ApiManager.getService().switchRoomSwitch(map),
                       new Response<BaseResult<ListResult<SwitchRoomItem>>>(getContext()) {
                           @Override
                           public void onSuccess(BaseResult<ListResult<SwitchRoomItem>> result) {
                                    item.status = (item.isOpen()?1:0);
                                    notifyDataSetChanged();
                           }
                       });
           }
       });

   }




}
