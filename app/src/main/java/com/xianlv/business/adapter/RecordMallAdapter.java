package com.xianlv.business.adapter;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.bean.WriteOffHistoryItem;

public class RecordMallAdapter extends BaseQuickAdapter<WriteOffHistoryItem, BaseViewHolder> implements LoadMoreModule {


    public RecordMallAdapter() {
        super(R.layout.item_record_mall, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  WriteOffHistoryItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_text1,"数量："+item.num)
                .setText(R.id.tv_text2,""+item.createtime)
                .setText(R.id.tv_text3,"姓名："+item.nickname)
                .setText(R.id.tv_text4,"等级："+item.levelName)
                .setText(R.id.tv_text5,"核销员："+item.vername);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        RecordMallGoodsAdapter mallGoodsAdapter = new RecordMallGoodsAdapter();
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(getContext(), 6));
        rv_goods.addItemDecoration(girdSpace);
        rv_goods.setAdapter(mallGoodsAdapter);
        mallGoodsAdapter.setNewData(item.productNameVos);



   }



}
