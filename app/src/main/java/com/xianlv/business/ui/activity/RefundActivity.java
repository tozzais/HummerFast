package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.adapter.RefundDetailAdapter;
import com.xianlv.business.bean.CashDetail;
import com.xianlv.business.bean.eventbus.RefreshReturn;
import com.xianlv.business.bean.request.RequestCashId;
import com.xianlv.business.bean.request.RequestCashUpdate;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.CenterDialogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RefundActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.btn_explain)
    TextView btnExplain;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;
    @BindView(R.id.et_money)
    EditText et_money;

    private String cashId;
    public static void launch(Context from, String cashId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, RefundActivity.class);
        intent.putExtra("cashId", cashId);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund;
    }

    RefundDetailAdapter mAdapter;
    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("退款");
        cashId = getIntent().getStringExtra("cashId") + "";

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RefundDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void loadData() {
        RequestCashId bean = new RequestCashId();
        bean.cashId = cashId;
        new RxHttp<BaseResult<CashDetail>>().send(ApiManager.getService().cash_detail(bean),
                new Response<BaseResult<CashDetail>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CashDetail> result) {
                        setData(result.data);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    CashDetail cashDetail;
    private void setData(CashDetail cashDetail) {
        this.cashDetail = cashDetail;
        tvPayMoney.setText("￥"+cashDetail.payMoney);
        tvRemainMoney.setText("￥"+cashDetail.money);
        mAdapter.setNewData(cashDetail.accountRecords);

    }


    @OnClick({R.id.btn_explain, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_explain:
                CenterDialogUtil.showExplain(mActivity, tvExplain.getText().toString().trim(), s -> {
                    tvExplain.setText(s);
                    if (TextUtils.isEmpty(s)) {
                        btnExplain.setText("添加说明");
                    } else {
                        btnExplain.setText("修改");
                    }
                });
                break;
            case R.id.tv_sure:
                String money = et_money.getText().toString().trim();
                if (TextUtils.isEmpty(money) || "0".equals(money)){
                    getNewData();
                }else{
                    returnMoney();
                }
                break;
        }
    }

    private void returnMoney(){
        RequestCashUpdate bean = new RequestCashUpdate();
        bean.cashId = cashId;
        String s = tvExplain.getText().toString();
        bean.payment = TextUtils.isEmpty(s)?"":s;
        bean.money = et_money.getText().toString().trim();
        new RxHttp<BaseResult<CashDetail>>().send(ApiManager.getService().cash_update(bean),
                new Response<BaseResult<CashDetail>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CashDetail> result) {
                        loadData();
                        getNewData();
                    }
                });
    }

    private void getNewData(){
        RequestCashId bean = new RequestCashId();
        bean.cashId = getIntent().getStringExtra("cashId") + "";
        new RxHttp<BaseResult<CashDetail>>().send(ApiManager.getService().cash_detail(bean),
                new Response<BaseResult<CashDetail>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CashDetail> result) {
                        CenterDialogUtil.showRefundDetail(mActivity,result.data,()->{
                            returnM();
                        });
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    private void returnM(){
        RequestCashUpdate bean = new RequestCashUpdate();
        bean.cashId = cashId;
        new RxHttp<BaseResult>().send(ApiManager.getService().cash_return(bean),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"操作成功");
                        EventBus.getDefault().post(new RefreshReturn());
                        finish();
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable edt) {
                String temp = et_money.getText().toString();
                if (temp.equals(".")) {
                    edt.clear();
                    return;
                }
                if (temp.length() > 1) {
                    char c = temp.charAt(temp.length() - 1);
                    if (temp.substring(0, temp.length() - 1).contains(".") && ("" + c).equals(".")) {
                        et_money.setText(temp.substring(0, temp.length() - 1));
                        et_money.setSelection(et_money.getText().toString().length());
                    }
                }
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }
}