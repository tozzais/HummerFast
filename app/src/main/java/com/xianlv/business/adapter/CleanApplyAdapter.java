package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.CleanItem;
import com.xianlv.business.bean.eventbus.RefreshClean;
import com.xianlv.business.bean.request.RequestSweepId;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

public class CleanApplyAdapter extends BaseQuickAdapter<CleanItem, BaseViewHolder> implements LoadMoreModule {


    public CleanApplyAdapter() {
        super(R.layout.item_clean_apply, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CleanItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_room_number,"房间号："+item.roomNo)
                .setText(R.id.tv_time,""+item.createTime)
                .setText(R.id.tv_phone,"入住人手机号："+item.phone);
        helper.getView(R.id.tv_sure).setOnClickListener(view -> {
            pass(item.sweepId);
        });


   }

    private void pass(String id){
        RequestSweepId bean = new RequestSweepId();
        bean.sweepId = id;
        new RxHttp<BaseResult>().send(ApiManager.getService().clean_complete(bean),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new RefreshClean());
                    }
                });
    }



}
