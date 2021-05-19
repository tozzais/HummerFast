package com.xianlv.business.adapter;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.R;

public class SendFoodReminderAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SendFoodReminderAdapter() {
        super(R.layout.item_send_food_reminder, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        SendFoodReminderSubAdapter adapter = new SendFoodReminderSubAdapter();
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(5));


   }



}
