package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.MessageItem;
import com.xianlv.business.bean.eventbus.RefreshCheckIn;
import com.xianlv.business.bean.eventbus.RefreshMain;
import com.xianlv.business.bean.request.RequestStaffHousingId;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

public class MessageAdapter extends BaseQuickAdapter<MessageItem, BaseViewHolder> implements LoadMoreModule {


    public MessageAdapter() {
        super(R.layout.item_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  MessageItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_text1,item.title)
                .setText(R.id.tv_text2,item.createTime)
                .setText(R.id.tv_text3,item.content);
        View view = helper.getView(R.id.tv_tip);
        view.setVisibility("0".equals(item.status)?View.VISIBLE:View.GONE);
//        helper.getView(R.id.tv_refuse).setOnClickListener(view -> {
//            refuse(item.id);
//        });
//        helper.getView(R.id.tv_pass).setOnClickListener(view -> {
//            pass(item.id);
//        });

   }

   private void pass(String id){
       RequestStaffHousingId bean = new RequestStaffHousingId();
       bean.staffHousingId = id;
       new RxHttp<BaseResult>().send(ApiManager.getService().checkInPass(bean),
               new Response<BaseResult>(getContext()) {
                   @Override
                   public void onSuccess(BaseResult result) {
                       ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                       EventBus.getDefault().post(new RefreshCheckIn());
                       EventBus.getDefault().post(new RefreshMain());
                   }
               });
   }

    private void refuse(String id){
        RequestStaffHousingId bean = new RequestStaffHousingId();
        bean.staffHousingId = id;
        new RxHttp<BaseResult>().send(ApiManager.getService().checkInRefuse(bean),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new RefreshCheckIn());
                        EventBus.getDefault().post(new RefreshMain());
                    }
                });
    }


}
