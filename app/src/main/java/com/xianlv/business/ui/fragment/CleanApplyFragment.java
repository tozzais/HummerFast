package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CleanApplyAdapter;
import com.xianlv.business.bean.CleanItem;
import com.xianlv.business.bean.eventbus.RefreshClean;
import com.xianlv.business.bean.request.RequestGiveWay;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.CleanHistoryActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class CleanApplyFragment extends BaseListFragment<CleanItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    public static CleanApplyFragment newInstance(int type) {
        CleanApplyFragment cartFragment = new CleanApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("打扫历史");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new CleanApplyAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有打扫提醒哦~");



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_bottom_btn;
    }

    @Override
    public void loadData() {
        super.loadData();
        RequestGiveWay bean = new RequestGiveWay();
        bean.status = "1";
        bean.page = page+"";
        bean.shopId = Objects.requireNonNull(GlobalParam.getLoginBean()).shopId +"";
        new RxHttp<BaseListResult<CleanItem>>().send(ApiManager.getService().clean_list(bean),
                new Response<BaseListResult<CleanItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CleanItem> result) {
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


    @OnClick(R.id.btn_bottom)
    public void onClick() {
        CleanHistoryActivity.launch(mActivity);
    }


    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshClean){
            onRefresh();
        }
    }
}
