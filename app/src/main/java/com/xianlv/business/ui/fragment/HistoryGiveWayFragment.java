package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.HistoryGiveWayAdapter;
import com.xianlv.business.bean.GiveWayItem;
import com.xianlv.business.bean.request.RequestGiveWay;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.Objects;


public class HistoryGiveWayFragment extends BaseListFragment<GiveWayItem> {




    public static HistoryGiveWayFragment newInstance(int type){
        HistoryGiveWayFragment cartFragment = new HistoryGiveWayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new HistoryGiveWayAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂无送物历史~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestGiveWay bean = new RequestGiveWay();
        bean.status = "1";
        bean.page = page+"";
        bean.shopId = Objects.requireNonNull(GlobalParam.getLoginBean()).shopId +"";
        new RxHttp<BaseListResult<GiveWayItem>>().send(ApiManager.getService().give_way_list(bean),
                new Response<BaseListResult<GiveWayItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<GiveWayItem> result) {
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
