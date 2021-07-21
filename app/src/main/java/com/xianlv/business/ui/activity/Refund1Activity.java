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

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.eventbus.RefreshReturn1;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.CenterDialogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class Refund1Activity extends BaseActivity {


    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.btn_explain)
    TextView btnExplain;
    @BindView(R.id.et_money)
    EditText et_money;

    private String scanId;
    public static void launch(Context from, String scanId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, Refund1Activity.class);
        intent.putExtra("scanId", scanId);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund1;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("退款");
        scanId = getIntent().getStringExtra("scanId") + "";



    }

    @Override
    public void loadData() {


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
                    tsg("输入退款金额");
                }else{
                    returnM();
                }
                break;
        }
    }


    private void returnM(){
        String trim = tvExplain.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("scanId", scanId);
        map.put("money",et_money.getText().toString().trim());
        map.put("reason",TextUtils.isEmpty(trim)?"":trim);
        new RxHttp<BaseResult>().send(ApiManager.getService().return_money(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"操作成功");
                        EventBus.getDefault().post(new RefreshReturn1());
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