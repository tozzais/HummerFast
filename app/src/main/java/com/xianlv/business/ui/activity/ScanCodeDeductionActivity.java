package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckDeductionItem;
import com.xianlv.business.bean.request.RequestCardReduce;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanCodeDeductionActivity extends BaseActivity {


    @BindView(R.id.tv_card_name)
    EditText tvCardName;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_level)
    EditText tvLevel;
    @BindView(R.id.tv_balance)
    EditText tvBalance;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.et_remark)
    EditText et_remark;


    @BindView(R.id.et_money)
    EditText et_money;

    public static void launch(Context from, String key, String category,String title) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ScanCodeDeductionActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("category", category);
        intent.putExtra("title", title);
        from.startActivity(intent);
    }

    private String key;
    private String category;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_deduction;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle(getIntent().getStringExtra("title"));
        key = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");

    }

    @Override
    public void loadData() {
        RequestCheckDeduction bean = new RequestCheckDeduction();
        bean.tag = "1";
        bean.category = category;
        bean.key = key;
        bean.page = "0";
        new RxHttp<BaseListResult<CheckDeductionItem>>().send(ApiManager.getService().check_deduction(bean),
                new Response<BaseListResult<CheckDeductionItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<CheckDeductionItem> result) {
                        //默认第一个是有的。没有后续在处理
                        item = result.data.get(0);
                        tvCardName.setText(item.cardName);
                        tvName.setText(item.nickname);
                        tvPhone.setText(item.phone);
                        tvLevel.setText(item.levelName);
                        tvBalance.setText(item.balance+"元");
                        tvAddress.setText(item.shopNames.get(0));
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });

    }
    CheckDeductionItem item;

    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_bottom:
                String money = et_money.getText().toString().trim();
                if (TextUtils.isEmpty(money)){
                    tsg("请输入退款金额");
                    return;
                }
                CenterDialogUtil.showTwo(mActivity, item,money,"取消", "确定", s -> {
                    if (s.equals("1")) {
                        RequestCardReduce bean = new RequestCardReduce();
                        bean.cardUserId = item.cardUserId;
                        bean.money = money;
                        bean.remark = et_remark.getText().toString().trim();
                        new RxHttp<BaseResult<String>>().send(ApiManager.getService().card_reduce(bean),
                                new Response<BaseResult<String>>(mActivity) {
                                    @Override
                                    public void onSuccess(BaseResult<String> result) {
                                        DeductionResultActivity.launch(mActivity, result.data);
                                        finish();
                                    }
                                });

                    }
                });

                break;
        }
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