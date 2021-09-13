package com.xianlv.business.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.R;
import com.xianlv.business.adapter.MallCouponWriteOffAdapter;
import com.xianlv.business.bean.CheckDeductionGoodsItem;
import com.xianlv.business.bean.eventbus.RefreshGoodsWriteOff;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.bean.request.RequestGoodsVerify;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.listener.OnPositionClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MallCouponWriteOffFragment extends BaseListFragment<CheckDeductionGoodsItem> implements OnPositionClickListener {


    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.tv_list_head)
    TextView tv_list_head;
    @BindView(R.id.iv_select_all)
    ImageView iv_select_all;
    @BindView(R.id.ll_header)
    LinearLayout ll_header;
    @BindView(R.id.ll_bottom)
    RelativeLayout ll_bottom;


    /**
     *
     * @param tag 3
     * @param category  1 扫码获取 2 手机号查询  3卡号查询
     * @param key
     * @return
     */
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
    private String key;
    private String  category;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        assert getArguments() != null;
        tag = getArguments().getString("tag");
        category = getArguments().getString("category");
        key = getArguments().getString("key");
        if (category.equals("2")){
            //订单号
            selectTab(0);
        }else {
            //券码
            selectTab(1);
        }
        et_code.setText(key);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MallCouponWriteOffAdapter(this);
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_writeoff_mall;
    }

    @Override
    public void loadData() {
        String key = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(key)){
            return;
        }
        RequestCheckDeduction bean = new RequestCheckDeduction();
        bean.tag = tag;
        bean.category = category;
        bean.key = key;
        bean.page = page+"";
        new RxHttp<BaseListResult<CheckDeductionGoodsItem>>().send(ApiManager.getService().check_deduction1(bean),
                new Response<BaseListResult<CheckDeductionGoodsItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CheckDeductionGoodsItem> result) {
                        setData(result.data);
                        ll_header.setVisibility(View.VISIBLE);
                        if (category.equals("2")){
                            tv_list_head.setText("订单号："+key);
                        }else {
                            tv_list_head.setText("券码："+key);
                        }
                        iv_select_all.setImageResource(R.mipmap.icon_unselect);
                        ll_bottom.setVisibility(View.GONE);
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


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_cancel, R.id.iv_select_all, R.id.btn_bottom})
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
                onRefresh();
                break;
            case R.id.iv_select_all:
                isSelectAll = !isSelectAll;
                ll_bottom.setVisibility(isSelectAll?View.VISIBLE:View.GONE);
                iv_select_all.setImageResource(isSelectAll?R.mipmap.icon_select:R.mipmap.icon_unselect);
                List<CheckDeductionGoodsItem> data = mAdapter.getData();
                for (CheckDeductionGoodsItem item:data){
                    item.isSelect = isSelectAll;
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_bottom:
                RequestGoodsVerify bean = new RequestGoodsVerify();
                bean.verificationId = verificationId();
                new RxHttp<BaseResult>().send(ApiManager.getService().goods_verification(bean),
                        new Response<BaseResult>(mActivity) {
                            @Override
                            public void onSuccess(BaseResult result) {
                                tsg("核销成功");
                                onRefresh();
                            }
                        });
                break;
        }
    }

    boolean isSelectAll = false;
    @Override
    public void onclick(int position) {
        List<CheckDeductionGoodsItem> data = mAdapter.getData();
        boolean isAll = true;//是否全选
        boolean selectNumber = false;//是否选择商品
        for (CheckDeductionGoodsItem item:data){
            if (!(item.status == 1 || item.isSelect)){
                isAll = false;
            }
            if (item.status == 0 && item.isSelect){
                selectNumber = true;

            }
        }
        isSelectAll = isAll;
        iv_select_all.setImageResource(isAll?R.mipmap.icon_select:R.mipmap.icon_unselect);
        ll_bottom.setVisibility(selectNumber?View.VISIBLE:View.GONE);
    }

    private String verificationId(){
        List<CheckDeductionGoodsItem> data = mAdapter.getData();
        String id = "";
        for (CheckDeductionGoodsItem item:data){
            if (item.status == 0 && item.isSelect){
                id = id+","+item.verificationId;
            }
        }
        return id.substring(1);

    }




    private void selectTab(int position) {
        et_code.setText("");
        int selectColor = getResources().getColor(R.color.white);
        int defaultColor = getResources().getColor(R.color.grayText);
        tvBtn1.setBackgroundResource(position == 0 ? R.drawable.shape_blue5 : R.drawable.shape_line_gray5);
        tvBtn1.setTextColor(position == 0 ? selectColor : defaultColor);
        tvBtn2.setBackgroundResource(position == 1 ? R.drawable.shape_blue5 : R.drawable.shape_line_gray5);
        tvBtn2.setTextColor(position == 1 ? selectColor : defaultColor);
        if (position == 0){
            et_code.setHint("请输入订单号");
            category = "2";
        }else {
            et_code.setHint("请输入券码");
            category = "3";
        }
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshGoodsWriteOff){
            onRefresh();
        }
    }
}
