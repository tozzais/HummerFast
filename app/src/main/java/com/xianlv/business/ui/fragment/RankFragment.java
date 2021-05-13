package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.CheckOutAdapter;
import com.xianlv.business.adapter.RankAdapter;


public class RankFragment extends BaseListFragment<String> {




    public static RankFragment newInstance(int type){
        RankFragment cartFragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new RankAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有排行哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
       setData(DataUtil.getData(9));
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


}
