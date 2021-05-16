package com.xianlv.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.ui.activity.BreakfastCouponApplyActivity;
import com.xianlv.business.ui.activity.CashPledgeManageActivity;
import com.xianlv.business.ui.activity.CheckInApplyActivity;
import com.xianlv.business.ui.activity.CheckOutApplyActivity;
import com.xianlv.business.ui.activity.CleanApplyActivity;
import com.xianlv.business.ui.activity.CodeActivity;
import com.xianlv.business.ui.activity.CouponWriteOffActivity;
import com.xianlv.business.ui.activity.DepositInformActivity;
import com.xianlv.business.ui.activity.GiveAwayReminderActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffRecordActivity;
import com.xianlv.business.ui.activity.OperationTrainActivity;
import com.xianlv.business.ui.activity.ParkCouponApplyActivity;
import com.xianlv.business.ui.activity.SalesRankActivity;
import com.xianlv.business.ui.activity.StoredValueCardWriteOffActivity;
import com.xianlv.business.ui.activity.VideoListActivity;
import com.xianlv.business.ui.activity.VisitorRecordActivity;
import com.xianlv.business.util.CenterDialogUtil;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户
 */
public class MainActivity extends CheckPermissionActivity {


    public static String[] needPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @BindView(R.id.iv_bg)
    ImageView ivBg;

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, MainActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, ivBg);
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.iv_switch, R.id.ll_applets, R.id.ll_store, R.id.ll_rank_person, R.id.ll_rank_team,
            R.id.rl_order1, R.id.rl_order2, R.id.rl_write1, R.id.rl_write2, R.id.rl_write3, R.id.rl_write4,
            R.id.rl_apply1, R.id.rl_apply2, R.id.rl_apply3, R.id.rl_apply4, R.id.rl_manage1, R.id.rl_manage2,
            R.id.rl_manage3, R.id.rl_manage4, R.id.rl_manage5, R.id.rl_manage6, R.id.rl_manage7, R.id.rl_manage8,
            R.id.rl_manage9, R.id.rl_manage10, R.id.rl_study1, R.id.rl_study2, R.id.rl_study3, R.id.rl_study4, R.id.rl_study5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                break;
            case R.id.ll_applets:
                CodeActivity.launch(mActivity,1);
                break;
            case R.id.ll_store:
                CodeActivity.launch(mActivity,2);
                break;
            case R.id.ll_rank_person:
                SalesRankActivity.launch(mActivity,SalesRankActivity.PERSON);
                break;
            case R.id.ll_rank_team:
                SalesRankActivity.launch(mActivity,SalesRankActivity.TEAM);
                break;
            case R.id.rl_order1:
                break;
            case R.id.rl_order2:
                break;
            case R.id.rl_write1:
                CouponWriteOffActivity.launch(mActivity);
                break;
            case R.id.rl_write2:
                StoredValueCardWriteOffActivity.launch(mActivity,0);
                break;
            case R.id.rl_write3:
                MallCouponWriteOffActivity.launch(mActivity);
//                StoredValueCardWriteOffActivity.launch(mActivity,1);
                break;
            case R.id.rl_write4:
                MallCouponWriteOffRecordActivity.launch(mActivity);
                break;
            case R.id.rl_apply1:
                CheckInApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply2:
                CheckOutApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply3:
                BreakfastCouponApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply4:
                ParkCouponApplyActivity.launch(mActivity);
                break;
            case R.id.rl_manage1:
                GiveAwayReminderActivity.launch(mActivity);
                break;
            case R.id.rl_manage2:
                CleanApplyActivity.launch(mActivity);
                break;
            case R.id.rl_manage3:
                break;
            case R.id.rl_manage4:
                CashPledgeManageActivity.launch(mActivity);
                break;
            case R.id.rl_manage5:
                DepositInformActivity.launch(mActivity);
                break;
            case R.id.rl_manage6:
                CenterDialogUtil.showVerify(mActivity,"早餐券验证",0,s -> {
                    if (s.equals("1")){
                        checkPermissions(needPermissions);
                    }
                });
                break;
            case R.id.rl_manage7:
                CenterDialogUtil.showVerify(mActivity,"停车券验证",1,s -> {

                });
                break;
            case R.id.rl_manage8:
                break;
            case R.id.rl_manage9:
                VisitorRecordActivity.launch(mActivity);
                break;
            case R.id.rl_manage10:
                break;
            case R.id.rl_study1:
                VideoListActivity.launch(mActivity);
                break;
            case R.id.rl_study2:
                OperationTrainActivity.launch(mActivity,OperationTrainActivity.OPERATION_TRAIN);
                break;
            case R.id.rl_study3:
                OperationTrainActivity.launch(mActivity,OperationTrainActivity.DISTRIBUTION_INSTRUCTIONS);
                break;
            case R.id.rl_study4:
                OperationTrainActivity.launch(mActivity,OperationTrainActivity.COMMON_PROBLEM);
                break;
            case R.id.rl_study5:
                break;
        }
    }

    private static final int REQUEST_CODE_SCAN = 1001;
    @Override
    public void permissionGranted() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */
        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true);//是否播放扫描声音 默认为true
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
//                result.setText("扫描结果为：" + content);
                Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
            }
        }
    }
}