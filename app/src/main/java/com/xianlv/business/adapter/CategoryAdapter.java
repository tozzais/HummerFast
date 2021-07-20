package com.xianlv.business.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.SceneItem;

public class CategoryAdapter extends BaseQuickAdapter<SceneItem, BaseViewHolder> implements LoadMoreModule {


    public CategoryAdapter() {
        super(R.layout.item_category, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  SceneItem item) {
        int position = helper.getAdapterPosition();

        TextView tv_name =  helper.getView(R.id.tv_name);
        tv_name.setText(item.typeName);
        if (item.isCheck){
            tv_name.setBackgroundColor(getContext().getResources().getColor(R.color.baseColor));
            tv_name.setTextColor(getContext().getResources().getColor(R.color.white));
        }else {
            tv_name.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tv_name.setTextColor(getContext().getResources().getColor(R.color.black_title_color));
        }



   }





}
