package com.xianlv.business.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.VideoItem;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.ui.activity.VideoDetailActivity;

public class VideoListAdapter extends BaseQuickAdapter<VideoItem, BaseViewHolder> implements LoadMoreModule {


    public VideoListAdapter() {
        super(R.layout.item_video, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  VideoItem item) {
        int position = helper.getAdapterPosition();
        ImageUtil.loadFullAddress(getContext(),helper.getView(R.id.iv_image),item.pic);
        helper.setText(R.id.tv_duration,item.videoTime)
                .setText(R.id.tv_time,item.createTime)
                .setText(R.id.tv_title,item.title)
                .setText(R.id.tv_views,item.views);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            VideoDetailActivity.launch(getContext(),item.videoId);
        });


   }



}
