package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.CardReduceDetail;
import com.xianlv.business.bean.request.RequestCardReduceDetail;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import butterknife.BindView;
import butterknife.OnClick;

public class DeductionResultActivity extends BaseActivity {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_money1)
    TextView tvMoney1;
    @BindView(R.id.tv_money2)
    TextView tvMoney2;
    @BindView(R.id.tv_money3)
    TextView tvMoney3;

    public static void launch(Context from, String cardBalanceId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, DeductionResultActivity.class);
        intent.putExtra("cardBalanceId", cardBalanceId);
        from.startActivity(intent);
    }

    public String cardBalanceId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dedution_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("储值卡扣款");
        cardBalanceId = getIntent().getStringExtra("cardBalanceId");

    }

    @Override
    public void loadData() {

        RequestCardReduceDetail bean = new RequestCardReduceDetail();
        bean.cardBalanceId = cardBalanceId;
        bean.page = "0";
        new RxHttp<BaseListResult<CardReduceDetail>>().send(ApiManager.getService().card_reduce_detail(bean),
                new Response<BaseListResult<CardReduceDetail>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CardReduceDetail> result) {
                        CardReduceDetail detail = result.data.get(0);
                        tvName.setText(detail.nickname);
                        tvTime.setText(detail.createtime);
                        tvLevel.setText(detail.cardName);
                        tvMoney.setText("￥"+detail.balance);
                        tvAddress.setText("营业点："+detail.department);
                        tvRemark.setText("备注："+detail.remark);
                        tvCardNumber.setText("卡号："+detail.cardUserNo);
                        tvMoney1.setText("￥"+detail.rechargeAmount);
                        tvMoney2.setText("￥"+detail.givenAmount);
                        tvMoney3.setText("￥"+detail.balance);
                    }
                });

    }

    @OnClick(R.id.btn_bottom)
    public void onClick() {
        finish();
    }
}