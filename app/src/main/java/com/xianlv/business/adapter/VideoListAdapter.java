package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.ui.activity.VideoDetailActivity;

public class VideoListAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public VideoListAdapter() {
        super(R.layout.item_video, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            VideoDetailActivity.launch(getContext());
        });


   }



}
