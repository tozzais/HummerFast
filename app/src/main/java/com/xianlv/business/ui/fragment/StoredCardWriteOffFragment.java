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
import com.xianlv.business.adapter.StoreCardWriteAdapter;
import com.xianlv.business.bean.CheckDeductionCardItem;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 储值卡扣款列表页
 */
public class StoredCardWriteOffFragment extends BaseListFragment<CheckDeductionCardItem> {


    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;

    public static StoredCardWriteOffFragment newInstance(int type) {
        StoredCardWriteOffFragment cartFragment = new StoredCardWriteOffFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new StoreCardWriteAdapter();
        mRecyclerView.setAdapter(mAdapter);

        selectTab(0);


    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff_card;
    }

    @Override
    public void loadData() {
        String key = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            swipeLayout.setRefreshing(false);
            return;
        }
        RequestCheckDeduction bean = new RequestCheckDeduction();
        bean.tag = "1";
        bean.category = category;
        bean.key = key;
        bean.page = page+"";
        new RxHttp<BaseListResult<CheckDeductionCardItem>>().send(ApiManager.getService().check_deduction2(bean),
                new Response<BaseListResult<CheckDeductionCardItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CheckDeductionCardItem> result) {
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


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                selectTab(0);
                break;
            case R.id.tv_btn2:
                selectTab(1);
                break;
            case R.id.tv_cancel:
                et_code.setText("");
                break;
        }
    }


    private String  category;
    private void selectTab(int position) {
        et_code.setText("");
        int selectColor = getResources().getColor(R.color.white);
        int defaultColor = getResources().getColor(R.color.grayText);
        tvBtn1.setBackgroundResource(position == 0 ? R.drawable.shape_blue5 : R.drawable.shape_line_gray5);
        tvBtn1.setTextColor(position == 0 ? selectColor : defaultColor);
        tvBtn2.setBackgroundResource(position == 1 ? R.drawable.shape_blue5 : R.drawable.shape_line_gray5);
        tvBtn2.setTextColor(position == 1 ? selectColor : defaultColor);
        if (position == 0){
            et_code.setHint("请输入手机号");
            category = "2";
        }else {
            et_code.setHint("请输入卡号");
            category = "3";
        }
    }

}
