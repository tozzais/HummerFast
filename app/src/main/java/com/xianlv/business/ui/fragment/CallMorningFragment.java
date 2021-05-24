package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.CallMorningAdapter;
import com.xianlv.business.bean.CallMorningItem;
import com.xianlv.business.bean.request.RequestList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class CallMorningFragment extends BaseListFragment<CallMorningItem> {


    public static CallMorningFragment newInstance(int type){
        CallMorningFragment cartFragment = new CallMorningFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CallMorningAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂无叫早服务~");



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();

    }

    private void  getData(){
        RequestList bean = new RequestList();
        bean.page = page+"";
        new RxHttp<BaseListResult<CallMorningItem>>().send(ApiManager.getService().callMorning(bean),
                new Response<BaseListResult<CallMorningItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CallMorningItem> result) {
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
