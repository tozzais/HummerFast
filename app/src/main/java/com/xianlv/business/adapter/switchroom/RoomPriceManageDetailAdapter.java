package com.xianlv.business.adapter.switchroom;


import android.widget.RelativeLayout;

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
import com.xianlv.business.ui.roommanage.ModifyRoomPriceActivity;
import com.xianlv.business.util.CenterDialogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 房价管理
 */
public class RoomPriceManageDetailAdapter extends BaseQuickAdapter<SwitchDetailItem, BaseViewHolder>  {


    public RoomPriceManageDetailAdapter() {
        super(R.layout.item_switch_room_price_manage, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  SwitchDetailItem item) {
        helper.setText(R.id.tv_price,"￥"+item.price)
                .setText(R.id.tv_status,"状态:"+(item.isOpen()?"开":"关"))
                .setText(R.id.tv_inventory,"库存:"+item.store);
        RelativeLayout iv_edit = helper.getView(R.id.rl_edit);
        iv_edit.setOnClickListener(v -> {
            ModifyRoomPriceActivity.launch(getContext(),item.promotionId,item.workDateStr);
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
