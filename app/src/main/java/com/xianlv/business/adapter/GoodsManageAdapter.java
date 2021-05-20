package com.xianlv.business.adapter;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;

public class GoodsManageAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public GoodsManageAdapter() {
        super(R.layout.item_goods_manage, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        TextView btn_edit = helper.getView(R.id.btn_edit);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_number = helper.getView(R.id.tv_number);
        EditText et_price = helper.getView(R.id.et_price);
        EditText et_number = helper.getView(R.id.et_number);
        btn_edit.setOnClickListener(view -> {
            if (btn_edit.getText().toString().equals("编辑")){
                et_price.setVisibility(View.VISIBLE);
                et_number.setVisibility(View.VISIBLE);
                tv_price.setVisibility(View.GONE);
                tv_number.setVisibility(View.GONE);
                btn_edit.setText("保存");
            }else {
                et_price.setVisibility(View.GONE);
                et_number.setVisibility(View.GONE);
                tv_price.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.VISIBLE);
                btn_edit.setText("编辑");
            }
        });

//        et_price.setOnEditorActionListener((v, actionId, event) -> true);
//        et_number.setOnEditorActionListener((v, actionId, event) -> true);

   }



}
