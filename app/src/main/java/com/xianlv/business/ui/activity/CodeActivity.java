package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

import butterknife.BindView;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_code_name)
    TextView tvCodeName;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_tip1)
    TextView tvTip1;
    @BindView(R.id.tv_tip2)
    TextView tvTip2;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private int type;

    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, CodeActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(1);
        type = getIntent().getIntExtra("type", 1);
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());
        if (type == 1) {
            tvTitle.setText("小程序二维码");
        } else if (type == 2) {
            tvTitle.setText("门店收款码");
            llRoot.setBackgroundColor(getResources().getColor(R.color.yellowText));
            tvCodeName.setText("收款服务");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_receive_pay);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCodeName.setCompoundDrawables(drawable, null, null, null);
            tvCodeName.setTextColor(getResources().getColor(R.color.yellowText));

            tvCode.setText("收款记录");
            tvCode.setTextColor(getResources().getColor(R.color.yellowText));
            tvTip1.setText("安全提示");
            tvTip2.setText("为了你的资金安全，请保管好收款码，防止泄露");

        }


    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.iv_code, R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_code:
                break;
            case R.id.tv_code:
                if (type == 2){
                    CollectionRecordActivity.launch(mActivity);
                }
                break;
        }
    }
}