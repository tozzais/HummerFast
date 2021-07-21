package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceivePayResultActivity extends BaseActivity {


    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_btn2)
    TextView tv_btn2;

    public static void launch(Context from, int type, String scanId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ReceivePayResultActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("scanId", scanId);
        from.startActivity(intent);
    }


    private int type;
    private String scanId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_pay_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("支付结果");
        type = getIntent().getIntExtra("type", 0);
        scanId = getIntent().getStringExtra("scanId");
        if (type == 0) {
            ivResult.setImageResource(R.mipmap.auth_success);
            tvResult.setText("支付成功");
            tv_btn2.setText("打印小票");
        }else {
            ivResult.setImageResource(R.mipmap.auth_failed);
            tvResult.setText("支付失败");
            tv_btn2.setText("重新收款");
        }

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_home,R.id.tv_btn2})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_home:
                MainActivity.launch(mActivity);
                break;
            case R.id.tv_btn2:
                if (type == 0) {

                }else {
                    finish();
                }
                break;
        }

    }
}