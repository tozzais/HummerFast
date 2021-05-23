package com.xianlv.business.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.TopAndBottomSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.MallCouponWriteOffAdapter;
import com.xianlv.business.bean.CheckDeductionItem;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import butterknife.BindView;
import butterknife.OnClick;


public class MallCouponWriteOffFragment extends BaseListFragment<CheckDeductionItem> {


    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    @BindView(R.id.et_code)
    EditText et_code;

    public static MallCouponWriteOffFragment newInstance(String tag,String category,String key) {
        MallCouponWriteOffFragment cartFragment = new MallCouponWriteOffFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        bundle.putString("category", category);
        bundle.putString("key", key);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    private String tag;
    private String category;
    private String key;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        tag = getArguments().getString("tag");
        category = getArguments().getString("category");
        key = getArguments().getString("key");
        et_code.setText(key);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        TopAndBottomSpace girdSpace = new TopAndBottomSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new MallCouponWriteOffAdapter();
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff_mall;
    }

    @Override
    public void loadData() {
        super.loadData();
        String key = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(key)){
            return;
        }
        RequestCheckDeduction bean = new RequestCheckDeduction();
        bean.tag = tag;
        bean.category = category;
        bean.key = key;
        bean.page = page+"";
        new RxHttp<BaseListResult<CheckDeductionItem>>().send(ApiManager.getService().check_deduction(bean),
                new Response<BaseListResult<CheckDeductionItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CheckDeductionItem> result) {
                        setData(result.data);
                    }
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });


    }


    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                break;
            case R.id.tv_btn2:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
