package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.adapter.CollectionRecordAdapter;


public class CollectionRecordFragment extends BaseListFragment<String> {




    public static CollectionRecordFragment newInstance(int type){
        CollectionRecordFragment cartFragment = new CollectionRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollectionRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("您还没有收款记录哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
       setData(DataUtil.getData(8));
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


}
