package com.xianlv.business.ui;

import android.os.Bundle;
import android.view.View;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.R;
import com.xianlv.business.ui.activity.LoginActivity;

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

    @OnClick({R.id.rl_order9, R.id.rl_order8, R.id.rl_order10})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order8:
                break;
            case R.id.rl_order9:
                break;
            case R.id.rl_order10:
                LoginActivity.launch(mActivity);

                break;

        }
    }


}
