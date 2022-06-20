package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.eventbus.RefreshRoomOrder;
import com.xianlv.business.bean.home.HomePowerItem;
import com.xianlv.business.global.Constant;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.AgreementWebViewActivity;
import com.xianlv.business.weight.SquareRoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeAdapter extends BaseQuickAdapter<HomePowerItem, BaseViewHolder> implements LoadMoreModule {


    public HomeAdapter() {
        super(R.layout.item_home, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  HomePowerItem item) {
//        View line = helper.getView(R.id.line);
        SquareRoundImageView iv_product = helper.getView(R.id.iv_product);
        ImageUtil.loadFullAddress(getContext(),iv_product,item.getLogo());

//        helper.getView(R.id.ll_root).setOnClickListener(view -> {
//            OrderDetailActivity.launch(getContext(),item.roomOrderId,type);
//        });
        helper.setText(R.id.tv_title,item.getPowerStationName())
                .setText(R.id.tv_price,""+item.getPrice())
                .setText(R.id.tv_number1,"空闲"+item.getTrickUsableNum())
                .setText(R.id.tv_number2,"总数"+item.getTrickleChargeNum())
                .setText(R.id.tv_number3,"空闲"+item.getFastUsableNum())
                .setText(R.id.tv_number4,"总数"+item.getFastChargeNum())
                .setText(R.id.tv_distance,item.getDistance()+"KM");
//        helper.getView(R.id.tv_cancel).setOnClickListener(v -> {
//            refuse(item.roomOrderId);
//        });
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            AgreementWebViewActivity.launch(getContext(), Constant.charge_info_url);

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
