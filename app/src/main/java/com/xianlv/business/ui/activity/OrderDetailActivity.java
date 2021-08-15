package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.RoomOrderDetail;
import com.xianlv.business.bean.eventbus.RefreshRoomOrder;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.tv_text4)
    TextView tvText4;
    @BindView(R.id.tv_text5)
    TextView tvText5;
    @BindView(R.id.tv_text6)
    TextView tvText6;
    @BindView(R.id.tv_text7)
    TextView tvText7;
    @BindView(R.id.tv_text8)
    TextView tvText8;
    @BindView(R.id.tv_text9)
    TextView tvText9;
    @BindView(R.id.tv_text10)
    TextView tvText10;
    @BindView(R.id.tv_text11)
    TextView tvText11;
    @BindView(R.id.tv_text12)
    TextView tvText12;
    @BindView(R.id.tv_text13)
    TextView tvText13;
    @BindView(R.id.tv_text14)
    TextView tvText14;
    @BindView(R.id.ll_bottom)
    RelativeLayout ll_bottom;

    public static void launch(Context from, String roomOrderId,int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, OrderDetailActivity.class);
        intent.putExtra("roomOrderId", roomOrderId);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    private String roomOrderId;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("订单详情");
        roomOrderId = getIntent().getStringExtra("roomOrderId");
        type = getIntent().getIntExtra("type",0);
        if (type == 0){
            ll_bottom.setVisibility(View.GONE);
        }else {
            ll_bottom.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void loadData() {
        if (!isLoad){
            showProress();
        }
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("roomOrderId", roomOrderId);
        new RxHttp<BaseResult<RoomOrderDetail>>().send(ApiManager.getService().roomOrderDetail(map),
                new Response<BaseResult<RoomOrderDetail>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<RoomOrderDetail> result) {
                        showContent();
                        isLoad = true;
                        RoomOrderDetail data = result.data;
                        tvText1.setText(data.roomName);
                        tvText2.setText(data.promotionName);
                        tvText3.setText("入住："+data.timeDesc);
                        tvText4.setText(data.num+"间 "+data.days+"晚");
                        tvText5.setText("￥"+data.money);
                        tvText6.setText(data.shopName);
                        tvText7.setText("地址："+data.address);
                        tvText8.setText("电话："+data.shopPhone);
                        tvText9.setText(""+data.orderNo);
                        tvText10.setText(""+data.createTime);
                        tvText11.setText(""+data.statusDesc);
                        tvText12.setText(""+data.payTypeDesc);
                        tvText13.setText(""+data.trueName);
                        tvText14.setText(""+data.userPhone);
                    }

                    @Override
                    public void onErrorShow(String s) {

                        showError(s);
                    }
                });

    }


    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                refuse(roomOrderId);
                break;
            case R.id.btn_bottom:
                pass(roomOrderId);
                break;
        }
    }
    private void pass(String id){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("roomOrderId", id);
        new RxHttp<BaseResult>().send(ApiManager.getService().roomOrderSure(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"确认成功");
                        EventBus.getDefault().post(new RefreshRoomOrder());
                        finish();
                    }
                });
    }

    private void refuse(String id){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("roomOrderId", id);
        new RxHttp<BaseResult>().send(ApiManager.getService().roomOrderCancel(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"拒绝成功");
                        EventBus.getDefault().post(new RefreshRoomOrder());
                        finish();
                    }
                });
    }
}