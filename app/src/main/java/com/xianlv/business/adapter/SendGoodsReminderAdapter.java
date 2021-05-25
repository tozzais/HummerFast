package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.GiveWayItem;
import com.xianlv.business.bean.eventbus.RefreshMain;
import com.xianlv.business.bean.eventbus.RefreshSendGoods;
import com.xianlv.business.bean.request.RequestGoodsOrderId;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

public class SendGoodsReminderAdapter extends BaseQuickAdapter<GiveWayItem, BaseViewHolder> implements LoadMoreModule {


    public SendGoodsReminderAdapter() {
        super(R.layout.item_send_goods_reminder, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GiveWayItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_room_number,"房间号："+item.roomNo)
                .setText(R.id.tv_time,""+item.createTime)
                .setText(R.id.tv_phone,"入住人手机号："+item.phone)
                .setText(R.id.tv_goods,"物品名称："+item.details);
        helper.getView(R.id.tv_sure).setOnClickListener(view -> {
                pass(item.goodsOrderId);
        });


   }

    private void pass(String id){
        RequestGoodsOrderId bean = new RequestGoodsOrderId();
        bean.goodsOrderId = id;
        new RxHttp<BaseResult>().send(ApiManager.getService().give_way_confirm(bean),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new RefreshSendGoods());
                        EventBus.getDefault().post(new RefreshMain());
                    }
                });
    }


}
