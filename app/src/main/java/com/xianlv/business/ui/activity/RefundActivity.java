package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.R;
import com.xianlv.business.adapter.RefundDetailAdapter;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RefundActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.btn_explain)
    TextView btnExplain;

    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, RefundActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("退款");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        RefundDetailAdapter mAdapter = new RefundDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(DataUtil.getData(5));

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.btn_explain, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_explain:
                CenterDialogUtil.showExplain(mActivity,tvExplain.getText().toString().trim(),s -> {
                    tvExplain.setText(s);
                    if (TextUtils.isEmpty(s)){
                        btnExplain.setText("添加说明");
                    }else {
                        btnExplain.setText("修改");
                    }
                });
                break;
            case R.id.tv_sure:
                CenterDialogUtil.showRefundDetail(mActivity);
                break;
        }
    }
}