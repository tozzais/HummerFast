package com.xianlv.business.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CouponWriteOffAdapter;
import com.xianlv.business.bean.CheckDeductionItem;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.bean.request.RequestMyCouponId;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class CouponWriteOffFragment extends BaseListFragment<CheckDeductionItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;
    @BindView(R.id.et_code)
    EditText et_code;

    public static CouponWriteOffFragment newInstance(String tag,String category,String key) {
        CouponWriteOffFragment cartFragment = new CouponWriteOffFragment();
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
        btnBottom.setText("核销");
        tag = getArguments().getString("tag");
        category = getArguments().getString("category");
        key = getArguments().getString("key");
        et_code.setText(key);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new CouponWriteOffAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView("暂时没有押金历史哦~");



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff;
    }

    @Override
    public void loadData() {
        String key = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(key)){
            swipeLayout.setRefreshing(false);
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
        et_code.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 当按了搜索之后关闭软键盘
                ((InputMethodManager) et_code.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        mActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                onRefresh();
                return true;
            }
            return false;
        });

    }


    @OnClick({R.id.tv_cancel,R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                et_code.setText("");
                break;
            case R.id.btn_bottom:
                List<CheckDeductionItem> data = mAdapter.getData();
                if (data.size() == 0){
                    tsg("无可核销优惠券");
                    return;
                }
                RequestMyCouponId bean = new RequestMyCouponId();
                bean.myCouponId = data.get(0).myCouponId;
                new RxHttp<BaseResult>().send(ApiManager.getService().coupon_verification(bean),
                        new Response<BaseResult>(mActivity) {
                            @Override
                            public void onSuccess(BaseResult result) {
                                tsg("核销成功");
                                mAdapter.remove(0);
                            }
                        });
                break;
        }


    }
}
