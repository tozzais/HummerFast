package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.MessageAdapter;
import com.xianlv.business.bean.MessageItem;
import com.xianlv.business.bean.eventbus.RefreshCheckIn;
import com.xianlv.business.bean.request.RequestList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MessageFragment extends BaseListFragment<MessageItem> {




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
        RequestList bean = new RequestList();
        bean.page = page+"";
        new RxHttp<BaseListResult<MessageItem>>().send(ApiManager.getService().messageList(bean),
                new Response<BaseListResult<MessageItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<MessageItem> result) {
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

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshCheckIn){
            onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String getIds(){
        @NotNull List<MessageItem> data = mAdapter.getData();
        StringBuilder s = new StringBuilder();
        for (MessageItem messageItem:data){
            if ("0".equals(messageItem.status)){
                s.append(messageItem.jiguangId).append(",");
            }
        }
        return new String(s);

    }
}
