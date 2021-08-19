package com.xianlv.business.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.eventbus.RefreshCommonReserveOrder;
import com.xianlv.business.bean.order.ReserveOrderDetail;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 预约订单详情
 */
public class ReserveOrderDetailActivity extends BaseActivity {


    @BindView(R.id.ll_goods)
    LinearLayout ll_goods;
    @BindView(R.id.ll_bottom)
    RelativeLayout ll_bottom;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_text6)
    TextView tv_text6;
    @BindView(R.id.tv_text3)
    TextView tv_text3;
    @BindView(R.id.tv_text4)
    TextView tv_text4;
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

    /**
     *
     * @param from
     * @param appointmentOrderId
     * @param type  1：普通预约 2：商品预约
     * @param status 0 : 历史订单 1：待确认订单
     */
    public static void launch(Context from, String appointmentOrderId, int type, int status) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ReserveOrderDetailActivity.class);
        intent.putExtra("appointmentOrderId", appointmentOrderId);
        intent.putExtra("type", type);
        intent.putExtra("status", status);
        from.startActivity(intent);
    }

    private String appointmentOrderId;
    private int type;
    private int status;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reserve_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("订单详情");

        appointmentOrderId = getIntent().getStringExtra("appointmentOrderId");
        type = getIntent().getIntExtra("type",1);
        status = getIntent().getIntExtra("status",1);
//        if (type == 123){
//            btnBottom.setText("确认订单");
//            btnCancel.setText("取消订单");
//        }else if (type == 456){
//            btnCancel.setVisibility(View.GONE);
//            btnBottom.setText("确认送达");
//        }else {
//            btnCancel.setVisibility(View.GONE);
//            btnBottom.setVisibility(View.GONE);
//        }

    }

    @Override
    public void loadData() {
        if (!isLoad) {
            showProress();
        }
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("appointmentOrderId", "" + appointmentOrderId);
        new RxHttp<BaseResult<ReserveOrderDetail>>().send(ApiManager.getService().reserveOrderDetail(map),
                new Response<BaseResult<ReserveOrderDetail>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ReserveOrderDetail> result) {
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

    private void setData(ReserveOrderDetail detail){
        tvRoomNumber.setText(""+detail.shopName);
        tvName.setText("地址："+detail.address);
        tvPhone.setText("电话："+detail.shopPhone);

        if (type == 2){
            ll_goods.setVisibility(View.VISIBLE);
            ImageUtil.loadFullAddress(mActivity,iv_image,detail.img);
            tv_text3.setText(detail.appointmentName);
            tv_text4.setText("规格："+detail.property);
            tv_text6.setText("数量：*"+detail.people);
        }else {
            ll_goods.setVisibility(View.GONE);
        }
        if (status == 0){
            ll_bottom.setVisibility(View.GONE);
        }else {
            ll_bottom.setVisibility(View.VISIBLE);
        }

        tvOrderNumber.setText(""+detail.orderNo);
        tv_create_time.setText(""+detail.quantumTime);
        tvPayTime.setText(""+detail.truename);
        tvExpectTime.setText(""+detail.phone);
        tvStatus.setText(""+detail.statusName);
        tvPayWay.setText(""+detail.remark);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_bottom:
                sure();
                break;
        }
    }

    private void cancel(){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("appointmentOrderId",appointmentOrderId);
        new RxHttp<BaseResult>().send(ApiManager.getService().reserveOrderCancel(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"取消成功");
                        EventBus.getDefault().post(new RefreshCommonReserveOrder());
                        finish();
                    }
                });
    }
    private void sure(){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("appointmentOrderId",appointmentOrderId);
        new RxHttp<BaseResult>().send(ApiManager.getService().reserveOrderConfirm(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"确认成功");
                        EventBus.getDefault().post(new RefreshCommonReserveOrder());
                        finish();
                    }
                });
    }

}