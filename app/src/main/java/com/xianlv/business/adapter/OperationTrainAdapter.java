package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.ProblemItem;
import com.xianlv.business.ui.activity.OperationTrainDetailActivity;

public class OperationTrainAdapter extends BaseQuickAdapter<ProblemItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public OperationTrainAdapter(int type) {
        super(R.layout.item_operation_train, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  ProblemItem item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            OperationTrainDetailActivity.launch(getContext(),item,type);
        });

        try {
            helper.setText(R.id.tv_title,item.title)
                    .setText(R.id.tv_time,item.createTime.split(" ")[0]);
        }catch (Exception e){

        }



   }



}
