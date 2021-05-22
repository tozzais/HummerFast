package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.CheckOutAdapter;
import com.xianlv.business.bean.CheckOutItem;
import com.xianlv.business.bean.request.RequestList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class CheckOutFragment extends BaseListFragment<CheckOutItem> {




    public static CheckOutFragment newInstance(int type){
        CheckOutFragment cartFragment = new CheckOutFragment();
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
        mAdapter = new CheckOutAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有离店申请哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestList bean = new RequestList();
        bean.page = page+"";
        new RxHttp<BaseListResult<CheckOutItem>>().send(ApiManager.getService().checkOutList(bean),
                new Response<BaseListResult<CheckOutItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CheckOutItem> result) {
                        setData(result.data);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


    }


    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


}
