package com.xianlv.business.adapter;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.util.DataUtil;
import com.tozzais.baselibrary.util.DpUtil;
import com.xianlv.business.R;
import com.xianlv.business.ui.activity.DeliveryDetailActivity;

public class SendFoodReminderAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SendFoodReminderAdapter() {
        super(R.layout.item_send_food_reminder, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        TextView tv_open = helper.getView(R.id.tv_open);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        SendFoodReminderSubAdapter adapter = new SendFoodReminderSubAdapter();
        rv_goods.setAdapter(adapter);



        int count = 3;
        if (position == 0){
            count = 5;
            tv_open.setVisibility(View.VISIBLE);
        }else {
            tv_open.setVisibility(View.GONE);
        }
        adapter.setNewData(DataUtil.getData(count));

        int itemHeight = DpUtil.dip2px(getContext(),60);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv_goods.getLayoutParams();
        params.height = itemHeight*3;
        rv_goods.setLayoutParams(params);
        tv_open.setOnClickListener(view -> {


            if (tv_open.getText().toString().equals("展开(共5件)")){
                params.height = itemHeight*5;
                rv_goods.setLayoutParams(params);
                tv_open.setText("收起(共5件)");
                 Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_up_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(null, null, drawable, null);
            }else {
                params.height = itemHeight*3;
                rv_goods.setLayoutParams(params);
                tv_open.setText("展开(共5件)");
                Drawable drawable = getContext().getResources().getDrawable(R.mipmap.arrow_down_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(null, null, drawable, null);
            }
        });
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            DeliveryDetailActivity.launch(getContext());
        });

    }
}
