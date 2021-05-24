package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.HistoryCleanAdapter;
import com.xianlv.business.bean.CleanItem;
import com.xianlv.business.bean.request.RequestGiveWay;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.Objects;


public class HistoryCleanFragment extends BaseListFragment<CleanItem> {




    public static HistoryCleanFragment newInstance(int type){
        HistoryCleanFragment cartFragment = new HistoryCleanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new HistoryCleanAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂无打扫历史哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestGiveWay bean = new RequestGiveWay();
        bean.status = "2";
        bean.page = page+"";
        bean.shopId = Objects.requireNonNull(GlobalParam.getLoginBean()).shopId +"";
        new RxHttp<BaseListResult<CleanItem>>().send(ApiManager.getService().clean_list(bean),
                new Response<BaseListResult<CleanItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CleanItem> result) {
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
