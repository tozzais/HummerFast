package com.xianlv.business.adapter.order;


import android.annotation.SuppressLint;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.eventbus.RefreshCommonReserveOrder;
import com.xianlv.business.bean.order.ReserveOrderItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.order.activity.ReserveOrderDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonReserveAdapter extends BaseQuickAdapter<ReserveOrderItem, BaseViewHolder> implements LoadMoreModule {



    private int type;
    public CommonReserveAdapter(int type) {
        super(R.layout.item_common_reserve, null);
        this.type = type;
    }

    public CommonReserveAdapter() {
        super(R.layout.item_common_reserve, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ReserveOrderItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                .setText(R.id.tv_text2,item.statusName)
                .setText(R.id.tv_text3,""+item.appointmentName)
                .setText(R.id.tv_text4,"联系人："+item.truename)
                .setText(R.id.tv_text5,"联系电话："+item.phone)
                .setText(R.id.tv_money,""+item.appointPrice);
            helper.getView(R.id.view).setVisibility(type == 1? View.VISIBLE:View.GONE);
            helper.getView(R.id.ll_bottom).setVisibility(type == 1? View.VISIBLE:View.GONE);

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            ReserveOrderDetailActivity.launch(getContext(),item.appointmentOrderId,1,type);
        });
        helper.getView(R.id.tv_cancel).setOnClickListener(v -> {
            cancel(item.appointmentOrderId);
        });

        helper.getView(R.id.tv_sure).setOnClickListener(v -> {
            sure(item.appointmentOrderId);
        });


    }


    private void sure(String id){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("appointmentOrderId",id);
        new RxHttp<BaseResult>().send(ApiManager.getService().reserveOrderConfirm(map),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"确认成功");
                        EventBus.getDefault().post(new RefreshCommonReserveOrder());
                    }
                });
    }

    private void cancel(String id){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("appointmentOrderId",id);
        new RxHttp<BaseResult>().send(ApiManager.getService().reserveOrderCancel(map),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"取消成功");
                        EventBus.getDefault().post(new RefreshCommonReserveOrder());
                    }
                });
    }
}
