package com.xianlv.business.order.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.order.StoreCardOrderAdapter;
import com.xianlv.business.bean.eventbus.RefreshOrder;
import com.xianlv.business.bean.order.StoreCardOrderItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class StoredValueCardOrderFragment extends BaseListFragment<StoreCardOrderItem> {



    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.view)
    View view;

    public static StoredValueCardOrderFragment newInstance(int type){
        StoredValueCardOrderFragment cartFragment = new StoredValueCardOrderFragment();
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

        view.setVisibility(View.GONE);
        et_search.setHint("请输入储值卡号");
        tv_search.setText("取消");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new StoreCardOrderAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有储值卡订单哦~");

    }

    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("status","0");
        map.put("page",""+page);
        map.put("orderNo",""+et_search.getText().toString().trim());
        new RxHttp<BaseListResult<StoreCardOrderItem>>().send(ApiManager.getService().storeCardOrder(map),
                new Response<BaseListResult<StoreCardOrderItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<StoreCardOrderItem> result) {
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
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                //完成自己的事件
                onRefresh();
            }
            return false;
        });

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshOrder){
            onRefresh();
        }
    }
    @OnClick(R.id.tv_search)
    public void onClick() {
        et_search.setText("");
        onRefresh();
    }
}
