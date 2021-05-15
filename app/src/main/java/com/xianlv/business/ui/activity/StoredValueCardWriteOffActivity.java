package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

import butterknife.BindView;
import butterknife.OnClick;

public class StoredValueCardWriteOffActivity extends BaseActivity {


    public static final int CARD = 0;
    public static final int GOODS = 1;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_step1)
    TextView tvStep1;
    @BindView(R.id.tv_step2)
    TextView tvStep2;
    @BindView(R.id.tv_step3)
    TextView tvStep3;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    private int type = 0;


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, StoredValueCardWriteOffActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_writeoff_stored;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", CARD);
        if (type == CARD) {
            setBackTitle("储值卡核销");
        } else if (type == GOODS) {
            setBackTitle("商品核销");
            tvTip.setText("请客人出示订单详情二维码进行扫码");
            tvStep2.setText("请打开小程序“我的”页面找到我的订单");
            tvStep3.setText("点击订单详情查看二维码或订单号");
            tvBtn1.setText("扫码核销");
        }


    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                if (type == CARD){
                    ScanCodeDeductionActivity.launch(mActivity,0);
                }
                break;
            case R.id.tv_btn2:
                if (type == CARD){
                    StoredValueCardDeductionActivity.launch(mActivity);
                }

                break;
            case R.id.btn_bottom:
                if (type == CARD){
                    DeductionDetailActivity.launch(mActivity);
                }
                break;
        }
    }
}