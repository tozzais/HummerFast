package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.RankAdapter;
import com.xianlv.business.bean.RankItem;
import com.xianlv.business.bean.RankResult;
import com.xianlv.business.bean.request.RequestRank;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import butterknife.BindView;


public class RankFragment extends BaseListFragment<RankItem> {


    @BindView(R.id.tv_time)
    TextView tvTime;

    public static RankFragment newInstance(int type, int category) {
        RankFragment cartFragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("category", category);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_rank;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        category = getArguments().getInt("category") + "";
        type = getArguments().getInt("type") + "";
        int defaultColor = category.equals("2") ? getResources().getColor(R.color.orange) :
                getResources().getColor(R.color.baseColor);
        tvTime.setTextColor(defaultColor);
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

    private String category;
    private String type;

    private void getData() {
        RequestRank bean = new RequestRank();
        bean.page = page + "";
        bean.rank = type + "";
        bean.category = category +"";
        new RxHttp<BaseResult<RankResult>>().send(ApiManager.getService().getRank(bean),
                new Response<BaseResult<RankResult>>(mActivity, Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<RankResult> result) {
                        tvTime.setText(result.data.date);
                        setData(result.data.data);
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


}
