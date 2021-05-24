package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CashPledgeManageAdapter;
import com.xianlv.business.bean.CashItem;
import com.xianlv.business.bean.eventbus.RefreshReturn;
import com.xianlv.business.bean.request.RequestShopId;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class CashPledgeManageFragment extends BaseListFragment<CashItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;
    @BindView(R.id.ll_bottom)
    RelativeLayout ll_bottom;

    public static CashPledgeManageFragment newInstance(int type) {
        CashPledgeManageFragment cartFragment = new CashPledgeManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("押金历史");
        ll_bottom.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new CashPledgeManageAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有押金历史哦~");



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_bottom_btn;
    }

    @Override
    public void loadData() {
        super.loadData();
        RequestShopId bean = new RequestShopId();
        bean.shopId = Objects.requireNonNull(GlobalParam.getLoginBean()).shopId +"";
        new RxHttp<BaseListResult<CashItem>>().send(ApiManager.getService().cash_list(bean),
                new Response<BaseListResult<CashItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CashItem> result) {
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
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick(R.id.btn_bottom)
    public void onClick() {
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshReturn){
            onRefresh();
        }
    }
}
