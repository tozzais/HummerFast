package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.RoomOrderItem;
import com.xianlv.business.bean.eventbus.RefreshRoomOrder;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.OrderDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderAdapter extends BaseQuickAdapter<RoomOrderItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public OrderAdapter(int type) {
        super(R.layout.item_order, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  RoomOrderItem item) {
        int position = helper.getAdapterPosition();
        View line = helper.getView(R.id.line);
        View ll_bottom = helper.getView(R.id.ll_bottom);
        line.setVisibility(type ==2 ?View.VISIBLE:View.GONE);
        ll_bottom.setVisibility(type ==2 ?View.VISIBLE:View.GONE);

        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            OrderDetailActivity.launch(getContext(),item.roomOrderId,type);
        });
        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text2,""+item.shopName)
                .setText(R.id.tv_text3,"入住时间："+item.timeDesc)
                .setText(R.id.tv_text4,"房型："+item.roomName);
        helper.getView(R.id.tv_cancel).setOnClickListener(v -> {
            refuse(item.roomOrderId);
        });
        helper.getView(R.id.tv_sure).setOnClickListener(v -> {
            pass(item.roomOrderId);

        });


   }

    private void pass(String id){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("roomOrderId", id);
        new RxHttp<BaseResult>().send(ApiManager.getService().roomOrderSure(map),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"确认成功");
                        EventBus.getDefault().post(new RefreshRoomOrder());
                    }
                });
    }

    private void refuse(String id){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("roomOrderId", id);
        new RxHttp<BaseResult>().send(ApiManager.getService().roomOrderCancel(map),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"拒绝成功");
                        EventBus.getDefault().post(new RefreshRoomOrder());
                    }
                });
    }



}
