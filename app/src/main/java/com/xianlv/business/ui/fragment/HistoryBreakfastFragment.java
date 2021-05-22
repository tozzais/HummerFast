package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.adapter.HistoryBreakfastAdapter;
import com.xianlv.business.bean.CouponHistoryItem;
import com.xianlv.business.bean.request.RequestVoucher;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;


public class HistoryBreakfastFragment extends BaseListFragment<CouponHistoryItem> {




    public static HistoryBreakfastFragment newInstance(int type){
        HistoryBreakfastFragment cartFragment = new HistoryBreakfastFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    private int type;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new HistoryBreakfastAdapter(type);
        mRecyclerView.setAdapter(mAdapter);


        if (type == 1){
            setEmptyView("暂时没有早餐券历史哦~");
        }else {
            setEmptyView("暂时没有停车券券历史哦~");
        }




    }

    @Override
    public void loadData() {
        super.loadData();
        RequestVoucher bean = new RequestVoucher();
        bean.voucherType = type+"";
        bean.page = page+"";
        new RxHttp<BaseListResult<CouponHistoryItem>>().send(ApiManager.getService().coupon_history(bean),
                new Response<BaseListResult<CouponHistoryItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<CouponHistoryItem> result) {
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


}
