package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceivePayActivity extends BaseActivity {


    @BindView(R.id.tv_receive_pay_record)
    TextView tv_receive_pay_record;
    @BindView(R.id.btn_explain)
    TextView btnExplain;
    @BindView(R.id.tv_explain)
    TextView tv_explain;
    @BindView(R.id.tv_scenes)
    TextView tv_scenes;
    @BindView(R.id.et_money)
    EditText et_money;

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ReceivePayActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_pay;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("门店收款码");
        tv_receive_pay_record.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_receive_pay_record.getPaint().setAntiAlias(true);//抗锯齿



    }

    @Override
    public void loadData() {


    }



    @OnClick({R.id.btn_explain, R.id.tv_sure, R.id.tv_receive_pay_record, R.id.ll_scenes})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_explain:
                CenterDialogUtil.showExplain(mActivity, tv_explain.getText().toString().trim(), s -> {
                    tv_explain.setText(s);
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
                    tsg("请输入金额");
                }else{
                    CodeActivity.launch(mActivity, 2);
                }
                break;
            case R.id.tv_receive_pay_record:
                CollectionRecordActivity.launch(mActivity);
                break;
            case R.id.ll_scenes:
                ScenesActivity.launch(mActivity);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            tv_scenes.setText("中餐厅");
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