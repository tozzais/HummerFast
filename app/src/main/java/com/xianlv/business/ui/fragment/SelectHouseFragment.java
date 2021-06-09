package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.adapter.SelectHouseAdapter;
import com.xianlv.business.bean.eventbus.RefreshCheckIn;


public class SelectHouseFragment extends BaseListFragment<String> {




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
//        RequestList bean = new RequestList();
//        bean.page = page+"";
//        new RxHttp<BaseListResult<CheckInItem>>().send(ApiManager.getService().checkInList(bean),
//                new Response<BaseListResult<CheckInItem>>(mActivity,Response.BOTH) {
//                    @Override
//                    public void onSuccess(BaseListResult<CheckInItem> result) {
                        setData(DataUtil.getData(8));
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        onErrorResult(e);
//                    }
//                    @Override
//                    public void onErrorShow(String s) {
//                        showError(s);
//                    }
//                });


    }


    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshCheckIn){
            onRefresh();
        }
    }
}
