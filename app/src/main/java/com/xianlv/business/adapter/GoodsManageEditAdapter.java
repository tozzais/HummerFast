package com.xianlv.business.adapter;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsManageItemSku;

public class GoodsManageEditAdapter extends BaseQuickAdapter<GoodsManageItemSku, BaseViewHolder> implements LoadMoreModule {


    public GoodsManageEditAdapter() {
        super(R.layout.item_goods_manage_edit, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsManageItemSku item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_name,""+item.property)
                .setText(R.id.tv_price,""+item.newPrice)
                .setText(R.id.tv_total,""+item.total);
        EditText tv_total = helper.getView(R.id.tv_total);
        EditText tv_price = helper.getView(R.id.tv_price);
        tv_total.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.total = s.toString().trim();
            }
        });
        tv_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable edt) {
                String temp = tv_price.getText().toString();
                LogUtil.e("temp=="+temp);
                if (temp.equals(".")) {
                    edt.clear();
                    return;
                }
                if (temp.length() > 1) {
                    char c = temp.charAt(temp.length() - 1);
                    if (temp.substring(0, temp.length() - 1).contains(".") && ("" + c).equals(".")) {
                        tv_price.setText(temp.substring(0, temp.length() - 1));
                        tv_price.setSelection(tv_price.getText().toString().length());
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
