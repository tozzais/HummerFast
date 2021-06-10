package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.SelectHouseAdapter;
import com.xianlv.business.bean.HouseItem;
import com.xianlv.business.bean.HouseResult;
import com.xianlv.business.bean.request.RequestPhone;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import java.util.Objects;


public class SelectHouseFragment extends BaseListFragment<HouseItem> {




    public static SelectHouseFragment newInstance(int type){
        SelectHouseFragment cartFragment = new SelectHouseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SelectHouseAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有酒店哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        RequestPhone bean = new RequestPhone();
        bean.phone = Objects.requireNonNull(GlobalParam.getLoginBean()).phone;
        new RxHttp<BaseResult<HouseResult>>().send(ApiManager.getService().houseList(bean),
                new Response<BaseResult<HouseResult>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<HouseResult> result) {
                        setData(result.data.tenantList);
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
        //刷新
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


}
