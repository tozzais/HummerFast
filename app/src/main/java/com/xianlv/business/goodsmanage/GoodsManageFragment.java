package com.xianlv.business.goodsmanage;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.R;
import com.xianlv.business.adapter.GoodsManageAdapter;
import com.xianlv.business.adapter.GoodsTypeAdapter;
import com.xianlv.business.bean.GoodsManageItem;
import com.xianlv.business.bean.GoodsTypeItem;
import com.xianlv.business.bean.eventbus.RefreshGoodsManageList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.pop.CommonPopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsManageFragment extends BaseListFragment<GoodsManageItem> {



    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_tab)
    LinearLayout ll_tab;
    @BindView(R.id.rv_drop)
    RecyclerView rv_drop;
    @BindView(R.id.tv_tab_text)
    TextView tv_tab_text;

    public static GoodsManageFragment newInstance(int type){
        GoodsManageFragment cartFragment = new GoodsManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
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
        mAdapter = new GoodsManageAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有数据哦~");

    }

    private String typeId = "";
    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("typeId",typeId);
        map.put("page",""+page);
        map.put("productName",""+et_search.getText().toString().trim());
        new RxHttp<BaseListResult<GoodsManageItem>>().send(ApiManager.getService().goodsManageList(map),
                new Response<BaseListResult<GoodsManageItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<GoodsManageItem> result) {
                        setData(result.data);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


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
        if (o instanceof RefreshGoodsManageList){
            onRefresh();
        }
    }
    @OnClick({R.id.tv_tab_text,R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab_text:
               getType();
                break;
            case R.id.tv_search:
                et_search.setText("");
                break;
        }

    }
    private List<GoodsTypeItem> list;
    private void getType(){
        if (list == null){
            Map<String,String> map = new HashMap<>();
            map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
            new RxHttp<BaseListResult<GoodsTypeItem>>().send(ApiManager.getService().goodsType(map),
                    new Response<BaseListResult<GoodsTypeItem>>(mActivity) {
                        @Override
                        public void onSuccess(BaseListResult<GoodsTypeItem> result) {
                            list = new ArrayList<>();
                            list.add(new GoodsTypeItem("","全部"));
                            list.addAll(result.data);
                            show(list);
                        }
                    });
        }else {
            show(list);
        }

    }
    private CommonPopupWindow popupWindow;
    public void show(List<GoodsTypeItem> list){
        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(ll_tab);
            }
            return;
        }
        popupWindow = new CommonPopupWindow.Builder(getContext())
                .setView(R.layout.pop_goods_manage_type)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1f)
                .setOutsideTouchable(true)
                .setViewOnclickListener((view, layoutResId) -> {
                    View nullView = view.findViewById(R.id.nullview);
                    nullView.setOnClickListener(v ->{
                        popupWindow.dismiss();
                    });
                }).build();
        popupWindow.showAsDropDown(ll_tab);
        popupWindow.setFocusable(true);
        RecyclerView rv_list =  popupWindow.getContentView().findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        GoodsTypeAdapter adapter = new GoodsTypeAdapter();
        rv_list.setAdapter(adapter);
        adapter.setNewData(list);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            GoodsTypeItem goodsTypeItem = list.get(position);
            tv_tab_text.setText(goodsTypeItem.typeName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_tab_text.setTextColor(getContext().getColor(R.color.baseColor));
            }
            typeId = goodsTypeItem.typeId;
            onRefresh();
            popupWindow.dismiss();
        });
    }


}
