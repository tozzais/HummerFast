package com.xianlv.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.bean.MainNumberBean;
import com.xianlv.business.bean.MineInfo;
import com.xianlv.business.bean.request.BaseRequest;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.BreakfastCouponApplyActivity;
import com.xianlv.business.ui.activity.CallMorningActivity;
import com.xianlv.business.ui.activity.CashPledgeManageActivity;
import com.xianlv.business.ui.activity.CheckInApplyActivity;
import com.xianlv.business.ui.activity.CheckOutApplyActivity;
import com.xianlv.business.ui.activity.CleanApplyActivity;
import com.xianlv.business.ui.activity.CodeActivity;
import com.xianlv.business.ui.activity.CouponCodeAuthActivity;
import com.xianlv.business.ui.activity.DeliveryReminderActivity;
import com.xianlv.business.ui.activity.DepositInformActivity;
import com.xianlv.business.ui.activity.GiveAwayReminderActivity;
import com.xianlv.business.ui.activity.GoodsManageActivity;
import com.xianlv.business.ui.activity.LoginActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffRecordActivity;
import com.xianlv.business.ui.activity.OperationTrainActivity;
import com.xianlv.business.ui.activity.OrderActivity;
import com.xianlv.business.ui.activity.ParkCouponApplyActivity;
import com.xianlv.business.ui.activity.SalesRankActivity;
import com.xianlv.business.ui.activity.StoredValueCardWriteOffActivity;
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
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_hotel_name1)
    TextView tv_hotel_name1;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_number_check_in)
    TextView tvNumberCheckIn;
    @BindView(R.id.tv_number_breakfast)
    TextView tvNumberBreakfast;
    @BindView(R.id.tv_number_park)
    TextView tvNumberPark;
    @BindView(R.id.tv_number_send_goods)
    TextView tvNumberSendGoods;
    @BindView(R.id.tv_number_clean)
    TextView tvNumberClean;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

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
        getData();
        getNumber();
    }

    @OnClick({R.id.iv_switch, R.id.ll_applets, R.id.ll_store, R.id.ll_rank_person, R.id.ll_rank_team,
            R.id.rl_order1, R.id.rl_order2, R.id.rl_write1, R.id.rl_write2, R.id.rl_write3, R.id.rl_write4,
            R.id.rl_apply1, R.id.rl_apply2, R.id.rl_apply3, R.id.rl_apply4, R.id.rl_manage1, R.id.rl_manage2,
            R.id.rl_manage3, R.id.rl_manage4, R.id.rl_manage5, R.id.rl_manage6, R.id.rl_manage7, R.id.rl_manage8,
            R.id.rl_manage9, R.id.rl_manage10, R.id.rl_study1, R.id.rl_study2, R.id.rl_study3, R.id.rl_study4, R.id.rl_study5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                CenterDialogUtil.show(mActivity, "提示", "是否退出当前用户？", s -> {
                    if ("1".equals(s)) {
                        GlobalParam.exitLogin();
                        LoginActivity.launch(mActivity);
                        finish();
                    }
                });
                break;
            case R.id.ll_applets:
                CodeActivity.launch(mActivity, 1);
                break;
            case R.id.ll_store:
                CodeActivity.launch(mActivity, 2);
                break;
            case R.id.ll_rank_person:
                SalesRankActivity.launch(mActivity, SalesRankActivity.PERSON);
                break;
            case R.id.ll_rank_team:
                SalesRankActivity.launch(mActivity, SalesRankActivity.TEAM);
                break;
            case R.id.rl_order1:
                OrderActivity.launch(mActivity);
                break;
            case R.id.rl_order2:
                DeliveryReminderActivity.launch(mActivity);
                break;
            case R.id.rl_write1:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.COUPON);
                break;
            case R.id.rl_write2:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.CARD);
                break;
            case R.id.rl_write3:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.GOODS);
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
                GoodsManageActivity.launch(mActivity);
                break;
            case R.id.rl_manage4:
                CashPledgeManageActivity.launch(mActivity);
                break;
            case R.id.rl_manage5:
                DepositInformActivity.launch(mActivity);
                break;
            case R.id.rl_manage6:
                CenterDialogUtil.showVerify(mActivity, "早餐券验证", 0, s -> {
                    if (s.equals("1")) {
                        type = 0;
                        checkPermissions(needPermissions);
                    }
                });
                break;
            case R.id.rl_manage7:
                CenterDialogUtil.showVerify(mActivity, "停车券验证", 1, s -> {
                    if (s.equals("1")) {
                        type = 1;
                        checkPermissions(needPermissions);
                    }
                });
                break;
            case R.id.rl_manage8:
                tsg("该功能暂未开发，请您耐心等待");
                break;
            case R.id.rl_manage9:
                VisitorRecordActivity.launch(mActivity);
                break;
            case R.id.rl_manage10:
                CallMorningActivity.launch(mActivity);
                break;
            case R.id.rl_study1:
                tsg("该功能暂未开发，请您耐心等待");
//                VideoListActivity.launch(mActivity);
                break;
            case R.id.rl_study2:
                tsg("该功能暂未开发，请您耐心等待");
//                OperationTrainActivity.launch(mActivity, OperationTrainActivity.OPERATION_TRAIN);
                break;
            case R.id.rl_study3:
                tsg("该功能暂未开发，请您耐心等待");
//                OperationTrainActivity.launch(mActivity, OperationTrainActivity.DISTRIBUTION_INSTRUCTIONS);
                break;
            case R.id.rl_study4:
                OperationTrainActivity.launch(mActivity, OperationTrainActivity.COMMON_PROBLEM);
                break;
            case R.id.rl_study5:
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setOnRefreshListener(() -> {
            getData();
            getNumber();
        });
    }

    private static final int REQUEST_CODE_SCAN = 1001;

    @Override
    public void permissionGranted() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
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

    private int type = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    CouponCodeAuthActivity.launch(mActivity, type, content.split("\\*")[3]);
                } catch (Exception e) {
                    tsg("不是系统的二维码");
                }

            }
        }
    }

    private void getData() {
        new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().mineInfo(new BaseRequest()),
                new Response<BaseResult<MineInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineInfo> result) {
                        MineInfo mineInfo = result.data;
                        tvHotelName.setText(mineInfo.shopName);
                        tv_hotel_name1.setText(mineInfo.shopName);
                        tv_address.setText(mineInfo.address);
                        tvName.setText(mineInfo.department + " " + mineInfo.trueName);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });
    }


    private void getNumber() {
        new RxHttp<BaseResult<MainNumberBean>>().send(ApiManager.getService().main_number(new BaseRequest()),
                new Response<BaseResult<MainNumberBean>>(mActivity, Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<MainNumberBean> result) {
                        MainNumberBean numberBean = result.data;
                        tvNumberClean.setVisibility(numberBean.sweep > 0 ? View.VISIBLE : View.GONE);
                        tvNumberClean.setText(numberBean.sweep + "");
                        tvNumberPark.setVisibility(numberBean.vouchers2 > 0 ? View.VISIBLE : View.GONE);
                        tvNumberPark.setText(numberBean.vouchers2 + "");
                        tvNumberBreakfast.setVisibility(numberBean.vouchers1 > 0 ? View.VISIBLE : View.GONE);
                        tvNumberBreakfast.setText(numberBean.vouchers1 + "");
                        tvNumberSendGoods.setVisibility(numberBean.goods > 0 ? View.VISIBLE : View.GONE);
                        tvNumberSendGoods.setText(numberBean.goods + "");
                        tvNumberCheckIn.setVisibility(numberBean.roomWifiConfiguration > 0 ? View.VISIBLE : View.GONE);
                        tvNumberCheckIn.setText(numberBean.roomWifiConfiguration + "");
                    }

                });
    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                tsg("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}