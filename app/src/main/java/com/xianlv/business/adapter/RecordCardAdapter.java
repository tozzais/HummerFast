package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.WriteOffHistoryItem;

public class RecordCardAdapter extends BaseQuickAdapter<WriteOffHistoryItem, BaseViewHolder> implements LoadMoreModule {


    public RecordCardAdapter() {
        super(R.layout.item_record_card, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  WriteOffHistoryItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text1,"核销金额：￥"+item.recordsMoney)
                .setText(R.id.tv_text2,""+item.createtime)
                .setText(R.id.tv_text3,"姓名："+item.nickname)
                .setText(R.id.tv_text4,"等级："+item.levelName)
                .setText(R.id.tv_text5,"核销员："+item.vername)
                .setText(R.id.tv_text6,"备注："+item.remark);



   }



}
