package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.ui.activity.OperationTrainDetailActivity;

public class OperationTrainAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public OperationTrainAdapter() {
        super(R.layout.item_operation_train, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            OperationTrainDetailActivity.launch(getContext(),0);
        });


   }



}
