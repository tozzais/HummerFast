package com.xianlv.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.ui.activity.CheckOutApplyActivity;
import com.xianlv.business.ui.activity.CodeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_bg)
    ImageView ivBg;

    public static void launch(Context from, String type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, MainActivity.class);
        intent.putExtra("type", type);
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
                break;
            case R.id.ll_rank_team:
                break;
            case R.id.rl_order1:
                break;
            case R.id.rl_order2:
                break;
            case R.id.rl_write1:
                break;
            case R.id.rl_write2:
                break;
            case R.id.rl_write3:
                break;
            case R.id.rl_write4:
                break;
            case R.id.rl_apply1:
                break;
            case R.id.rl_apply2:
                CheckOutApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply3:
                break;
            case R.id.rl_apply4:
                break;
            case R.id.rl_manage1:
                break;
            case R.id.rl_manage2:
                break;
            case R.id.rl_manage3:
                break;
            case R.id.rl_manage4:
                break;
            case R.id.rl_manage5:
                break;
            case R.id.rl_manage6:
                break;
            case R.id.rl_manage7:
                break;
            case R.id.rl_manage8:
                break;
            case R.id.rl_manage9:
                break;
            case R.id.rl_manage10:
                break;
            case R.id.rl_study1:
                break;
            case R.id.rl_study2:
                break;
            case R.id.rl_study3:
                break;
            case R.id.rl_study4:
                break;
            case R.id.rl_study5:
                break;
        }
    }
}