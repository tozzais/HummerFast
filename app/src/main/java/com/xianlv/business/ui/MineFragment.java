package com.xianlv.business.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.weight.CircleImageView;
import com.xianlv.business.R;
import com.xianlv.business.bean.MineInfo;
import com.xianlv.business.global.Constant;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.BottomDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseFragment  {

    @BindView(R.id.ci_avatar)
    CircleImageView ci_avatar;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.ll_message)
    LinearLayoutCompat ll_message;


    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


    }

    @Override
    public void loadData() {
        new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().getMineInfo(),
                new Response<BaseResult<MineInfo>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<MineInfo> result) {
                        if (result.isSuccess()){
                            MineInfo data = result.data;
                            ImageUtil.loadFullAddress(mActivity,ci_avatar, data.logo);
                            tv_nickname.setText(data.nickname);
                            tv_phone.setText(data.phone);
                            MineInfo.Message message = data.message;
                            if (message != null && !TextUtils.isEmpty(message.text)){
                                ll_message.setVisibility(View.VISIBLE);
                                tv_message.setText(message.text);
                            }else {
                                ll_message.setVisibility(View.GONE);
                            }
                        }

                    }

                });

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
