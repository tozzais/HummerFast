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
import com.xianlv.business.adapter.CouponCodeBreakfastAdapter;
import com.xianlv.business.adapter.CouponCodeParkAdapter;
import com.xianlv.business.bean.CheckDeductionItem;
import com.xianlv.business.bean.request.RequestBreakfastId;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class CouponCodeAuthFragment extends BaseListFragment<CheckDeductionItem> {


    @BindView(R.id.btn_bottom)
    TextView btnBottom;
    @BindView(R.id.et_code)
    EditText et_code;

    private int type;
    private String key;
    public static CouponCodeAuthFragment newInstance(int type, String key) {
        CouponCodeAuthFragment cartFragment = new CouponCodeAuthFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("key", key);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        btnBottom.setText("验证");
        type = getArguments().getInt("type");
        key = getArguments().getString("key");
        et_code.setText(key);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        if (type == 0){
            mAdapter = new CouponCodeBreakfastAdapter();
        }else {
            mAdapter = new CouponCodeParkAdapter();
        }
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff;
    }

    @Override
    public void loadData() {
        String key = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(key)){
            return;
        }
        RequestCheckDeduction bean = new RequestCheckDeduction();
        bean.tag = type == 0?4+"":5+"";
        bean.category = "1";
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
                    tsg("无券码验证");
                    return;
                }
                RequestBreakfastId bean = new RequestBreakfastId();
                bean.breakfastId = data.get(0).breakfastId;
                new RxHttp<BaseResult>().send(ApiManager.getService().write_breakfast_park(bean),
                        new Response<BaseResult>(mActivity) {
                            @Override
                            public void onSuccess(BaseResult result) {
                                tsg("验证成功");
                                mAdapter.remove(0);
                            }
                        });
                break;
        }


    }
}
