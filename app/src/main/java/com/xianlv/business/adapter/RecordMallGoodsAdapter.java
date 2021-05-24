package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.WriteOffHistoryItem;

public class RecordMallGoodsAdapter extends BaseQuickAdapter<WriteOffHistoryItem.Product, BaseViewHolder> implements LoadMoreModule {


    public RecordMallGoodsAdapter() {
        super(R.layout.item_record_mall_goods, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  WriteOffHistoryItem.Product item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text1,""+item.name)
                .setText(R.id.tv_text2,"数量*"+item.num);

   }



}
