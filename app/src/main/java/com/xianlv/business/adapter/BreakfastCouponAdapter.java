package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.CouponItem;
import com.xianlv.business.bean.eventbus.RefreshVoucher;
import com.xianlv.business.bean.request.RequestVoucherId;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

public class BreakfastCouponAdapter extends BaseQuickAdapter<CouponItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public BreakfastCouponAdapter(int type) {
        super(R.layout.item_breakfast_coupon, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  CouponItem item) {
        int position = helper.getAdapterPosition();
        View helperView = helper.getView(R.id.tv_type);
        helperView.setVisibility(type == 1?View.VISIBLE:View.GONE);
        helper.setText(R.id.tv_room_number,"房间号："+item.roomNumber)
                .setText(R.id.tv_time,""+item.applicationTime)
                .setText(R.id.tv_phone,"入住人手机号："+item.userPhone)
                .setText(R.id.tv_number,"申请券数："+item.count)
                .setText(R.id.tv_type,"类型："+item.breakfastTypesOf);
        helper.getView(R.id.tv_refuse).setOnClickListener(view -> {
            dealWith(item.voucherId,"2");
        });
        helper.getView(R.id.tv_pass).setOnClickListener(view -> {
            dealWith(item.voucherId,"2");
        });

   }

    private void dealWith(String id,String status){
        RequestVoucherId bean = new RequestVoucherId();
        bean.status = status;
        bean.voucherId = id;
        new RxHttp<BaseResult>().send(ApiManager.getService().couponDeal(bean),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new RefreshVoucher());
                    }
                });
    }



}
