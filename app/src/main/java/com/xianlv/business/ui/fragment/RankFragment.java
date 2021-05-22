package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.RankAdapter;
import com.xianlv.business.bean.RankItem;
import com.xianlv.business.bean.request.RequestRank;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class RankFragment extends BaseListFragment<RankItem> {




    public static RankFragment newInstance(int type,int category){
        RankFragment cartFragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putInt("category",category);
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
        RequestRank bean = new RequestRank();
        bean.page = page+"";
        bean.category = getArguments().getInt("category")+"";
        new RxHttp<BaseListResult<RankItem>>().send(ApiManager.getService().getRank(bean),
                new Response<BaseListResult<RankItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<RankItem> result) {
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
