package com.xianlv.business.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.adapter.OperationTrainAdapter;
import com.xianlv.business.bean.ProblemItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.OperationTrainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import rx.Observable;


public class OperationTrainFragment extends BaseListFragment<ProblemItem> {




    public static OperationTrainFragment newInstance(int type){
        OperationTrainFragment cartFragment = new OperationTrainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type= getArguments().getInt("type");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new OperationTrainAdapter(type);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有操作培训哦~");


    }

    private int type;

    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("page",""+page);
        Observable<BaseListResult<ProblemItem>> observable = null;
        if (type == OperationTrainActivity.OPERATION_TRAIN){
            observable = ApiManager.getService().trainList(map);
        }else if (type == OperationTrainActivity.DISTRIBUTION_INSTRUCTIONS){
            observable = ApiManager.getService().explainList(map);
        }else if (type == OperationTrainActivity.COMMON_PROBLEM){
            observable = ApiManager.getService().problemList(map);
        }
        set(observable);

    }

    private void  set(Observable<BaseListResult<ProblemItem>> observable){

        new RxHttp<BaseListResult<ProblemItem>>().send(observable,
                new Response<BaseListResult<ProblemItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<ProblemItem> result) {
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
