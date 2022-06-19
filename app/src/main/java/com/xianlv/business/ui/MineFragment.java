package com.xianlv.business.ui;

import android.os.Bundle;
import android.view.View;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.R;
import com.xianlv.business.global.Constant;
import com.xianlv.business.util.BottomDialogUtil;

import butterknife.OnClick;


public class MineFragment extends BaseFragment  {


    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        initHeadView();

    }

    private void initHeadView() {



    }

    @Override
    public void loadData() {


    }


    @Override
    public void initListener() {
        super.initListener();

    }

    @OnClick({R.id.ll_mine_info, R.id.ll_message, R.id.rl_order1, R.id.rl_order2,
            R.id.rl_order3, R.id.rl_order4, R.id.rl_order5, R.id.rl_order6, R.id.rl_order7,
            R.id.rl_order8, R.id.rl_order9, R.id.rl_order10})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mine_info:
                LogUtil.e("调用H5加载了");
                AgreementWebViewActivity.launch(mActivity, Constant.mine_info_url);
                break;
            case R.id.ll_message:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_message_url);
                break;
            case R.id.rl_order1:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_car_url);
                break;
            case R.id.rl_order2:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_order_url);
                break;
            case R.id.rl_order3:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_invoice_url);
                break;
            case R.id.rl_order4:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_wallet_url);
                break;
            case R.id.rl_order5:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_coupon_url);
                break;
            case R.id.rl_order6:
                AgreementWebViewActivity.launch(mActivity, Constant.mine_collect_url);
                break;
            case R.id.rl_order7:
                AgreementWebViewActivity.launch(mActivity, Constant.message_url);
                break;
            case R.id.rl_order8:
                AgreementWebViewActivity.launch(mActivity, Constant.about_us_url);
                break;
            case R.id.rl_order9:
                AgreementWebViewActivity.launch(mActivity, Constant.about_us_url1);
                break;
            case R.id.rl_order10:
                BottomDialogUtil.showSelectDialog(mActivity);

//                AgreementWebViewActivity.launch(mActivity, Constant.about_us_url2);
                break;



        }
    }


}
