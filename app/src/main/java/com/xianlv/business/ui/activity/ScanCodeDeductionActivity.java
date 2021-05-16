package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.OnClick;

public class ScanCodeDeductionActivity extends BaseActivity {


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ScanCodeDeductionActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_deduction;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("扫码扣款");

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_bottom:
                CenterDialogUtil.showTwo(mActivity,"取消","确定",s -> {
                    if (s.equals("1")){
                        DeductionResultActivity.launch(mActivity,0);
                    }
                });

                break;
        }
    }
}