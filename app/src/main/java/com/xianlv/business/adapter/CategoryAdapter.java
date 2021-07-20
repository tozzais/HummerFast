package com.xianlv.business.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.CategoryItem;

public class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> implements LoadMoreModule {


    public CategoryAdapter() {
        super(R.layout.item_category, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CategoryItem item) {
        int position = helper.getAdapterPosition();

        TextView tv_name =  helper.getView(R.id.tv_name);
        tv_name.setText(item.name);


   }





}
