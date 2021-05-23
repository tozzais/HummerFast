package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.RecordCouponAdapter;
import com.xianlv.business.bean.WriteOffHistoryItem;
import com.xianlv.business.bean.request.RequestCategory;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class RecordCouponFragment extends BaseListFragment<WriteOffHistoryItem> {




    public static RecordCouponFragment newInstance(int type){
        RecordCouponFragment cartFragment = new RecordCouponFragment();
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

        mAdapter = new RecordCouponAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有优惠券核销记录~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestCategory bean = new RequestCategory();
        bean.category = "3";
        new RxHttp<BaseListResult<WriteOffHistoryItem>>().send(ApiManager.getService().ver_history(bean),
                new Response<BaseListResult<WriteOffHistoryItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<WriteOffHistoryItem> result) {
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
