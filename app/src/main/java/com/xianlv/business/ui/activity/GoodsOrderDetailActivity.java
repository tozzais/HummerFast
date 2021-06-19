package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.adapter.GoodsDetailSubAdapter;
import com.xianlv.business.bean.GoodsOrderItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;

public class GoodsOrderDetailActivity extends BaseActivity {


    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_money_total)
    TextView tvMoneyTotal;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_note)
    TextView tvNote;

    public static void launch(Context from, String orderId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, GoodsOrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        from.startActivity(intent);
    }

    private String orderId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("订单详情");

        orderId = getIntent().getStringExtra("orderId");


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
        new RxHttp<BaseResult<GoodsOrderItem>>().send(ApiManager.getService().goodsOrderDetail(map),
                new Response<BaseResult<GoodsOrderItem>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GoodsOrderItem> result) {
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

    private void setData(GoodsOrderItem detail) {
        tvMoneyTotal.setText("￥" + detail.amountMoney);
        tvScore.setText("￥" + detail.scoreMoney);
        tvCoupon.setText("￥" + detail.couponMoney);
        tvMoney.setText("￥" + detail.money);
        tvOrderNumber.setText( detail.orderNo);
        tvCreateTime.setText( detail.createtime);
        tvStatus.setText( detail.statusName);
        tvPayWay.setText( detail.payTypeDesc);
        tvName.setText( detail.userName);
        tvPhone.setText( detail.userPhone);
        tvNote.setText( detail.descs);

        GoodsDetailSubAdapter adapter = new GoodsDetailSubAdapter();
        rvGoods.setAdapter(adapter);
        adapter.setNewData(detail.orderDetailTitleList);
    }


}