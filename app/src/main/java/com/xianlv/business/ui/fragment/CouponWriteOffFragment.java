package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CashPledgeManageAdapter;
import com.xianlv.business.adapter.CouponWriteOffAdapter;

import butterknife.BindView;
import butterknife.OnClick;


public class CouponWriteOffFragment extends BaseListFragment<String> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    public static CouponWriteOffFragment newInstance(int type) {
        CouponWriteOffFragment cartFragment = new CouponWriteOffFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("核销");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new CouponWriteOffAdapter();
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
        setData(DataUtil.getData(9));
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick(R.id.btn_bottom)
    public void onClick() {
    }
}
