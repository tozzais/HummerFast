package com.xianlv.business.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xianlv.business.R;
import com.xianlv.business.bean.VisitorUserItem;

public class VisitorRecordAdapter extends BaseQuickAdapter<VisitorUserItem, BaseViewHolder> implements LoadMoreModule {


    public VisitorRecordAdapter() {
        super(R.layout.item_visitor_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  VisitorUserItem item) {
        int position = helper.getAdapterPosition();

        View view = helper.getView(R.id.view1);
        View view2 = helper.getView(R.id.view2);
        view.setVisibility(position == 0?View.INVISIBLE:View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        String[] strings = item.time.split(" ");
        String time = "登记时间";
        for (String s :strings) {
            time+= (s+"\n");
        }
        helper.setText(R.id.tv_text1,time)
                .setText(R.id.tv_text2,"房间号："+item.roomNumber)
                .setText(R.id.tv_text3,"寻访人姓名："+item.xunfangPeople)
                .setText(R.id.tv_text4,"拜访人姓名："+item.baifangPeople)
                .setText(R.id.tv_text5,"手机号："+item.baifangPeoplePhone)
                .setText(R.id.tv_text6,"寻访事由："+item.reason);



   }



}
