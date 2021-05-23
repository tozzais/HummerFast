package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.DeductionDetailAdapter;
import com.xianlv.business.bean.CardReduceDetail;
import com.xianlv.business.bean.request.RequestCardReduceDetail;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class DeductionDetailFragment extends BaseListFragment<CardReduceDetail> {




    public static DeductionDetailFragment newInstance(int type){
        DeductionDetailFragment cartFragment = new DeductionDetailFragment();
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

        mAdapter = new DeductionDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有明细哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestCardReduceDetail bean = new RequestCardReduceDetail();
        bean.cardBalanceId = "0";
        bean.page = ""+page;
        new RxHttp<BaseListResult<CardReduceDetail>>().send(ApiManager.getService().card_reduce_detail(bean),
                new Response<BaseListResult<CardReduceDetail>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CardReduceDetail> result) {
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
