package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.VisitorRecordAdapter;
import com.xianlv.business.bean.VisitorUserItem;
import com.xianlv.business.bean.request.RequestList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class VisitorRecordFragment extends BaseListFragment<VisitorUserItem> {




    public static VisitorRecordFragment newInstance(int type){
        VisitorRecordFragment cartFragment = new VisitorRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new VisitorRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂时没有访客登记哦~");





    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
        RequestList bean = new RequestList();
        bean.page = page+"";
        new RxHttp<BaseListResult<VisitorUserItem>>().send(ApiManager.getService().visitorList(bean),
                new Response<BaseListResult<VisitorUserItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<VisitorUserItem> result) {
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
