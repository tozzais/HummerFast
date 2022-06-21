package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.home.CityItem;

public class CityListAdapter extends BaseQuickAdapter<CityItem, BaseViewHolder> implements LoadMoreModule {


    public CityListAdapter() {
        super(R.layout.item_city_select_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  CityItem item) {

        helper.setText(R.id.tv_text,item.getProvinceName());
//                .setText(R.id.tv_text2,item.time)
//                .setText(R.id.tv_text3,"入住人手机号："+item.phone);
//        helper.getView(R.id.tv_refuse).setOnClickListener(view -> {
//            refuse(item.id);
//        });
//        helper.getView(R.id.tv_pass).setOnClickListener(view -> {
//            pass(item.id);
//        });

   }



}
