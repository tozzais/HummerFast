package com.xianlv.business.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsManageItem;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.goodsmanage.GoodsManageEditActivity;

public class GoodsManageAdapter extends BaseQuickAdapter<GoodsManageItem, BaseViewHolder> implements LoadMoreModule {


    public GoodsManageAdapter() {
        super(R.layout.item_goods_manage, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  GoodsManageItem item) {
        int position = helper.getAdapterPosition();
        TextView btn_edit = helper.getView(R.id.btn_edit);
        helper.setText(R.id.tv_text1,"序号："+item.productId)
                .setText(R.id.tv_text2,""+item.productName)
                .setText(R.id.tv_text3,"分类："+item.typeName)
                .setText(R.id.tv_text4,"状态："+item.getStatus());
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.logo);

        RecyclerView rv_goods = helper.getView(R.id.rv_list);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        GoodsManageSecondAdapter adapter = new GoodsManageSecondAdapter();
//        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(getContext(), 6));
//        rv_goods.addItemDecoration(girdSpace);
        rv_goods.setAdapter(adapter);
        adapter.setNewData(item.appSkuList);
        btn_edit.setOnClickListener(v -> {
            GoodsManageEditActivity.launch(getContext(),item.appSkuList);
        });

   }



}
