package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CouponCodeBreakfastAdapter;
import com.xianlv.business.adapter.CouponCodeParkAdapter;
import com.xianlv.business.adapter.CouponWriteOffAdapter;
import com.xianlv.business.ui.activity.AuthResultActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class CouponCodeAuthFragment extends BaseListFragment<String> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    private int type;
    public static CouponCodeAuthFragment newInstance(int type) {
        CouponCodeAuthFragment cartFragment = new CouponCodeAuthFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("验证");
        type = getArguments().getInt("type");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        if (type == 0){
            mAdapter = new CouponCodeBreakfastAdapter();
        }else {
            mAdapter = new CouponCodeParkAdapter();
        }
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView("暂时没有押金历史哦~");



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff;
    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void getData() {
        setData(DataUtil.getData(1));
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick(R.id.btn_bottom)
    public void onClick() {
        AuthResultActivity.launch(mActivity,1);
    }
}
