package com.xianlv.business.adapter.switchroom;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.switchroom.RoomPriceDetail;
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
 * 房价管理
 */
public class ModifyRoomPriceAdapter extends BaseQuickAdapter<RoomPriceDetail, BaseViewHolder>  {


    public ModifyRoomPriceAdapter() {
        super(R.layout.item_modify_room_price, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  RoomPriceDetail item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_name,item.levelName+"：￥");
        LinearLayout  ll_root = helper.getView(R.id.ll_root);
        TextView tv_sure = helper.getView(R.id.tv_sure);
        EditText et_money = helper.getView(R.id.et_money);
        et_money.setHint("请输入"+item.levelName);

        if (position == 0 && item.isShow){
            tv_sure.setVisibility(View.VISIBLE);
        }else {
            tv_sure.setVisibility(View.GONE);
        }
        et_money.setText(item.price);
        if (position != 0){
            ll_root.setVisibility(item.isShow? View.VISIBLE:View.GONE);
        }
        tv_sure.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getData().get(0).price)){
                ToastCommom.createToastConfig().ToastShow(getContext(),et_money.getHint().toString());
                return;
            }
            RoomPriceDetail detail0 = getData().get(0);
            String price = detail0.price;
            char c = price.charAt(price.length() - 1);
            if (price.substring(0, price.length() - 1).contains(".") && ("" + c).equals(".")) {
                price = detail0.price.substring(0,price.length() - 1);
            }
            for (int i = 0; i < getData().size(); i++) {
                RoomPriceDetail detail = getData().get(i);
                detail.isShow = i != 0;
                if (i != 0){
                    try {
                        double price1 = Double.parseDouble(price) * Double.parseDouble(detail.discount);
                        price1 = Math.max(price1,0.01);
                        detail.price = price1+"";
                    }catch (Exception e){

                    }
                }
            }
            notifyDataSetChanged();
        });

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable edt) {
                String temp = et_money.getText().toString();
                if (temp.equals(".")) {
                    edt.clear();
                    return;
                }
                if (temp.length() > 1) {
                    char c = temp.charAt(temp.length() - 1);
                    if (temp.substring(0, temp.length() - 1).contains(".") && ("" + c).equals(".")) {
                        et_money.setText(temp.substring(0, temp.length() - 1));
                        et_money.setSelection(et_money.getText().toString().length());
                    }
                }
                item.price = temp;
                LogUtil.e("变化的价格1："+item.price);

                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
                item.price = temp;
                LogUtil.e("变化的价格2："+item.price);

            }
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
