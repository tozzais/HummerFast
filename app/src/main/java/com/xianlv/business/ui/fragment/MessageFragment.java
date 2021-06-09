package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.MessageAdapter;
import com.xianlv.business.bean.eventbus.RefreshCheckIn;


public class MessageFragment extends BaseListFragment<String> {




    public static MessageFragment newInstance(int type){
        MessageFragment cartFragment = new MessageFragment();
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
        mAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有消息哦~");



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
