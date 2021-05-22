package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.SendGoodsReminderAdapter;
import com.xianlv.business.bean.GiveWayItem;
import com.xianlv.business.bean.eventbus.RefreshSendGoods;
import com.xianlv.business.bean.request.RequestGiveWay;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.GiveAwayHistoryActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class DeliveryReminderFragment extends BaseListFragment<GiveWayItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    public static DeliveryReminderFragment newInstance(int type) {
        DeliveryReminderFragment cartFragment = new DeliveryReminderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("送物历史");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new SendGoodsReminderAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂时没有送物历史哦~");



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_bottom_btn;
    }

    @Override
    public void loadData() {
        super.loadData();
        RequestGiveWay bean = new RequestGiveWay();
        bean.status = "0";
        bean.page = page+"";
        bean.shopId = Objects.requireNonNull(GlobalParam.getLoginBean()).shopId +"";
        new RxHttp<BaseListResult<GiveWayItem>>().send(ApiManager.getService().give_way_list(bean),
                new Response<BaseListResult<GiveWayItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<GiveWayItem> result) {
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
        GiveAwayHistoryActivity.launch(mActivity);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshSendGoods){
            onRefresh();
        }
    }
}
