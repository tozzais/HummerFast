package com.xianlv.business.goodsmanage;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.GoodsManageEditAdapter;
import com.xianlv.business.bean.GoodsManageItemSku;
import com.xianlv.business.bean.eventbus.RefreshOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsManageEditFragment extends BaseListFragment<GoodsManageItemSku> {



    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;

    public static GoodsManageEditFragment newInstance(ArrayList<GoodsManageItemSku> list){
        GoodsManageEditFragment cartFragment = new GoodsManageEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",list);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_goods_manage;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        et_search.setHint("搜索关键字");
        tv_search.setText("取消");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new GoodsManageEditAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有数据哦~");

    }

    @Override
    public void loadData() {
        if (getArguments() != null){
            ArrayList<GoodsManageItemSku> list = getArguments().getParcelableArrayList("list");
            setData(list);
        }




    }


    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                //完成自己的事件
                onRefresh();
            }
            return false;
        });

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshOrder){
            onRefresh();
        }
    }
    @OnClick(R.id.tv_search)
    public void onClick() {
        et_search.setText("");
    }
}
