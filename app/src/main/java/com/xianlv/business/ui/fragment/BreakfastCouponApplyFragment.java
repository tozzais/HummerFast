package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.BreakfastCouponAdapter;
import com.xianlv.business.bean.CouponItem;
import com.xianlv.business.bean.eventbus.RefreshVoucher;
import com.xianlv.business.bean.request.RequestVoucher;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.BreakfastCouponHistoryActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class BreakfastCouponApplyFragment extends BaseListFragment<CouponItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;

    public static BreakfastCouponApplyFragment newInstance(int type) {
        BreakfastCouponApplyFragment cartFragment = new BreakfastCouponApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    private int type;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        if (type == 1){
            btnBottom.setText("早餐券历史");
        }else {
            btnBottom.setText("停车券历史");
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new BreakfastCouponAdapter(type);
        mRecyclerView.setAdapter(mAdapter);

        if (type == 1){
            setEmptyView("暂时没有早餐券申请哦~");
        }else {
            setEmptyView("暂时没有停车券申请哦~");
        }




    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_bottom_btn;
    }

    @Override
    public void loadData() {
        super.loadData();
        RequestVoucher bean = new RequestVoucher();
        bean.voucherType = type+"";
        bean.page = page+"";
        new RxHttp<BaseListResult<CouponItem>>().send(ApiManager.getService().coupon_list(bean),
                new Response<BaseListResult<CouponItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CouponItem> result) {
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
        BreakfastCouponHistoryActivity.launch(mActivity,type);
    }


    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshVoucher){
            onRefresh();
        }
    }
}
