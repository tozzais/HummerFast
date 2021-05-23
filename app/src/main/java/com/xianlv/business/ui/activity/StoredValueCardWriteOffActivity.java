package com.xianlv.business.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class StoredValueCardWriteOffActivity extends CheckPermissionActivity {




    public static String[] needPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int COUPON = 0; //优惠券核销
    public static final int CARD = 1; //储值卡核销
    public static final int GOODS = 2; //商品核销
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
    @BindView(R.id.ll_bottom)
    RelativeLayout ll_bottom;
    //商品券 3041958924 1551973768
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

        type = getIntent().getIntExtra("type", COUPON);
        if (type == CARD) {
            setBackTitle("储值卡核销");
            tvTip.setText("请客人出示储值卡付款二维码进行扫码");
            tvStep2.setText("请打开小程序“我的”页面找到我的卡包");
            ll_bottom.setVisibility(View.VISIBLE);
        } else if (type == COUPON) {
            setBackTitle("优惠券核销");
            tvTip.setText("请客人出示兑换券二维码进行扫码");
            tvStep2.setText("请打开小程序“我的”页面找到优惠券");
            tvStep3.setText("点击优惠券查看二维码或兑换码");
            tvBtn1.setText("扫码核销");
            tvBtn2.setText("查询核销");
        } else if (type == GOODS) {
            setBackTitle("商品核销");
            tvTip.setText("请客人出示订单详情二维码进行扫码");
            tvStep2.setText("请打开小程序“我的”页面找到我的订单");
            tvStep3.setText("点击订单详情查看二维码或订单号");
            tvBtn1.setText("扫码核销");
            tvBtn2.setText("查询核销");
        }


    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                if (type == COUPON){
                    category = "1";
                    checkPermissions(needPermissions);
                }else if (type == CARD){
                    category = "1";
                    checkPermissions(needPermissions);
                }else if (type == GOODS){
                    category = "1";
                    checkPermissions(needPermissions);
                }
                break;
            case R.id.tv_btn2:
                if (type == COUPON){
                    CouponWriteOffActivity.launch(mActivity,"2","2","");
                }else if (type == CARD){
                    StoredValueCardDeductionActivity.launch(mActivity);
                }else if (type == GOODS){
                    MallCouponWriteOffActivity.launch(mActivity, "3", "2", "");
                }
                break;
            case R.id.btn_bottom:
                if (type == CARD){
                    DeductionDetailActivity.launch(mActivity);
                }
                break;
        }
    }

    private String category;
    private static final int REQUEST_CODE_SCAN = 1001;
    private void scan(){
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
        config.setShake(true);//是否震动  默认为true
        config.setDecodeBarCode(true);//是否扫描条形码 默认为true
        config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
        config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
        config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    if (type == COUPON) {
                        CouponWriteOffActivity.launch(mActivity, "2", category, content.split("\\*")[3]);
                    }else if (type == CARD) {
                        ScanCodeDeductionActivity.launch(mActivity,content.split("\\*")[3],"1","扫码扣款");
                    }else if (type == GOODS) {
                        tsg(content.split("\\*")[3]);
                        MallCouponWriteOffActivity.launch(mActivity, "3", "3", content.split("\\*")[3]);
                    }
                }
                catch (Exception e){
                    tsg("不是系统的二维码");
                }

            }
        }
    }

    @Override
    public void permissionGranted() {
        scan();

    }
}