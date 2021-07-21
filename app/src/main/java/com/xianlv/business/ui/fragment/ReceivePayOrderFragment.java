package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.ReceivePayOrderAdapter;
import com.xianlv.business.bean.ReceiveOrderItem;
import com.xianlv.business.bean.eventbus.RefreshOrder;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;


public class ReceivePayOrderFragment extends BaseListFragment<ReceiveOrderItem> {


    @BindView(R.id.et_search)
    EditText et_search;
    private int type = 0;
    public static ReceivePayOrderFragment newInstance(int type){
        ReceivePayOrderFragment cartFragment = new ReceivePayOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new ReceivePayOrderAdapter(type);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有收款记录哦~");

    }

    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("type",(type == 1?0:1)+"");
        map.put("page",""+page);
        map.put("keyword",""+et_search.getText().toString().trim());
        new RxHttp<BaseListResult<ReceiveOrderItem>>().send(ApiManager.getService().getReceiveList(map),
                new Response<BaseListResult<ReceiveOrderItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<ReceiveOrderItem> result) {
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
        if (o instanceof RefreshOrder){
            onRefresh();
        }
    }
}
