package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.tozzais.baselibrary.weight.TopAndBottomSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.MallCouponWriteOffAdapter;
import com.xianlv.business.adapter.StoreCardWriteAdapter;

import butterknife.BindView;
import butterknife.OnClick;


public class MallCouponWriteOffFragment extends BaseListFragment<String> {


    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    public static MallCouponWriteOffFragment newInstance(int type) {
        MallCouponWriteOffFragment cartFragment = new MallCouponWriteOffFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        TopAndBottomSpace girdSpace = new TopAndBottomSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new MallCouponWriteOffAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView("暂时没有押金历史哦~");


    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff_mall;
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


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                break;
            case R.id.tv_btn2:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
