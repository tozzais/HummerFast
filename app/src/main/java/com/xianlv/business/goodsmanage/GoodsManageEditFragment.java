package com.xianlv.business.goodsmanage;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.GoodsManageEditAdapter;
import com.xianlv.business.bean.GoodsManageItemSku;
import com.xianlv.business.bean.eventbus.RefreshGoodsManageList;
import com.xianlv.business.bean.eventbus.RefreshOrder;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsManageEditFragment extends BaseListFragment<GoodsManageItemSku> {



    @BindView(R.id.btn_bottom)
    TextView btn_bottom;

    public static GoodsManageEditFragment newInstance(ArrayList<GoodsManageItemSku> list){
        GoodsManageEditFragment cartFragment = new GoodsManageEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",list);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_bottom_btn_large;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        btn_bottom.setText("保存");

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
        //刷新
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshOrder){
            onRefresh();
        }
    }
    @OnClick(R.id.btn_bottom)
    public void onClick() {
        List<GoodsManageItemSku> list = mAdapter.getData();
        List<Map<String,String>> uploadList = new ArrayList<>();
        for (GoodsManageItemSku sku:list){
            if (TextUtils.isEmpty(sku.total)){
                tsg("请输入库存");
                return;
            }
            if (TextUtils.isEmpty(sku.newPrice)){
                tsg("请输入价格");
                return;
            }else {
                String temp = sku.newPrice;
                char c = temp.charAt(temp.length() - 1);
                if (("" + c).equals(".")) {
                    sku.newPrice = (temp.substring(0, temp.length() - 1));
                }
                Map<String,String> map = new HashMap<>();
                map.put("newPrice",sku.newPrice);
                map.put("skuId",sku.skuId);
                map.put("total",sku.total);
                uploadList.add(map);
            }
        }


        Map<String,Object> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("data",uploadList);
        new RxHttp<BaseResult>().send(ApiManager.getService().goodsManageEdit(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("修改成功");
                        EventBus.getDefault().post(new RefreshGoodsManageList());
                        mActivity.finish();
                    }

                });

    }
}
