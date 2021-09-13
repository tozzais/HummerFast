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
import com.xianlv.business.adapter.order.ValidityOrderAdapter;
import com.xianlv.business.bean.eventbus.RefreshOrder;
import com.xianlv.business.bean.order.ValidityOrderItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class ValidityOrderFragment extends BaseListFragment<ValidityOrderItem> {



    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.view)
    View view;

    public static ValidityOrderFragment newInstance(int type){
        ValidityOrderFragment cartFragment = new ValidityOrderFragment();
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
        et_search.setHint("输入订单号");
        tv_search.setText("取消");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new ValidityOrderAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时无有效期卡订单哦~");

    }

    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("status","0");
        map.put("page",""+page);
        map.put("orderNo",""+et_search.getText().toString().trim());
        new RxHttp<BaseListResult<ValidityOrderItem>>().send(ApiManager.getService().validityOrder(map),
                new Response<BaseListResult<ValidityOrderItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<ValidityOrderItem> result) {
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
