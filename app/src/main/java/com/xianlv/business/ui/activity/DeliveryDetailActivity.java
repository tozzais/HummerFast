package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.R;
import com.xianlv.business.adapter.SendFoodDetailAdapter;
import com.xianlv.business.util.CenterDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class DeliveryDetailActivity extends BaseActivity {


    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, DeliveryDetailActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("订单详情");

    }

    @Override
    public void loadData() {
        rvGoods.setLayoutManager(new LinearLayoutManager(mActivity));
        SendFoodDetailAdapter adapter = new SendFoodDetailAdapter();
        rvGoods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(2));

    }

    @OnClick({R.id.btn_cancel, R.id.btn_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_bottom:
                CenterDialogUtil.show(mActivity,"提示","确认送达？",s -> {

                });
                break;
        }
    }
}