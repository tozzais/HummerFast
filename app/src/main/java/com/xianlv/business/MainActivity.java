package com.xianlv.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.ui.activity.BreakfastCouponApplyActivity;
import com.xianlv.business.ui.activity.CashPledgeManageActivity;
import com.xianlv.business.ui.activity.CheckInApplyActivity;
import com.xianlv.business.ui.activity.CheckOutApplyActivity;
import com.xianlv.business.ui.activity.CleanApplyActivity;
import com.xianlv.business.ui.activity.CodeActivity;
import com.xianlv.business.ui.activity.CouponWriteOffActivity;
import com.xianlv.business.ui.activity.GiveAwayReminderActivity;
import com.xianlv.business.ui.activity.DepositInformActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffRecordActivity;
import com.xianlv.business.ui.activity.OperationTrainActivity;
import com.xianlv.business.ui.activity.ParkCouponApplyActivity;
import com.xianlv.business.ui.activity.SalesRankActivity;
import com.xianlv.business.ui.activity.StoredValueCardWriteOffActivity;
import com.xianlv.business.ui.activity.VideoListActivity;
import com.xianlv.business.ui.activity.VisitorRecordActivity;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户
 */
public class MainActivity extends BaseActivity {


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
                CenterDialogUtil.showVerify(mActivity,"早餐券验证",0);
                break;
            case R.id.rl_manage7:
                CenterDialogUtil.showVerify(mActivity,"停车券验证",1);
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
}