package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.adapter.SendFoodDetailAdapter;
import com.xianlv.business.bean.MealOrderItem;
import com.xianlv.business.bean.eventbus.RefreshOrder;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.CenterDialogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryDetailActivity extends BaseActivity {


    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_room_number)
    TextView tvRoomNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_expect_time)
    TextView tvExpectTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    public static void launch(Context from, String orderId, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, DeliveryDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    private String orderId;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("订单详情");

        orderId = getIntent().getStringExtra("orderId");
        type = getIntent().getIntExtra("type",1);
        if (type == 123){
            btnBottom.setText("确认订单");
            btnCancel.setText("取消订单");
        }else if (type == 456){
            btnCancel.setVisibility(View.GONE);
            btnBottom.setText("确认送达");
        }else {
            btnCancel.setVisibility(View.GONE);
            btnBottom.setVisibility(View.GONE);
        }

    }

    @Override
    public void loadData() {
        rvGoods.setLayoutManager(new LinearLayoutManager(mActivity));


        if (!isLoad) {
            showProress();
        }
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("orderId", "" + orderId);
        new RxHttp<BaseResult<MealOrderItem>>().send(ApiManager.getService().mealOrderDetail(map),
                new Response<BaseResult<MealOrderItem>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MealOrderItem> result) {
                        showContent();
                        isLoad = true;
                        setData(result.data);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }

                });

    }

    private void setData(MealOrderItem detail){
        MealOrderItem.Consumer consumer = detail.consumer;
        tvRoomNumber.setText("房间号："+consumer.roomNo);
        tvName.setText("用户名："+consumer.consumerName);
        tvPhone.setText("手机号："+consumer.consumerPhone);
        tvOrderNumber.setText(""+detail.orderNo);
        tv_create_time.setText(""+detail.createTime);
        tvPayTime.setText(""+detail.payTime);
        tvExpectTime.setText(""+consumer.expect);
        tvStatus.setText(""+detail.statusName);
        tvPayWay.setText(""+detail.payTypeDesc);
        SendFoodDetailAdapter adapter = new SendFoodDetailAdapter();
        rvGoods.setAdapter(adapter);
        adapter.setNewData(detail.detailVOS);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                CenterDialogUtil.show(mActivity, "提示", "确认取消订单吗？", s -> {
                    if ("1".equals(s))cancel();
                });
                break;
            case R.id.btn_bottom:
                if (type == 456){
                    send();
                    return;
                }
                sure();

                break;
        }
    }

    private void cancel(){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("orderId", orderId);
        new RxHttp<BaseResult>().send(ApiManager.getService().mealOrderCancel(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"取消订单成功");
                        EventBus.getDefault().post(new RefreshOrder());
                        finish();
                    }
                });
    }
    private void sure(){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("orderId", orderId);
        new RxHttp<BaseResult>().send(ApiManager.getService().mealOrderSure(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"确认订单成功");
                        EventBus.getDefault().post(new RefreshOrder());
                        finish();
                    }
                });
    }
    private void send(){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("orderId", orderId);
        new RxHttp<BaseResult>().send(ApiManager.getService().mealOrderSend(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"确认送达成功");
                        EventBus.getDefault().post(new RefreshOrder());
                        finish();
                    }
                });
    }
}