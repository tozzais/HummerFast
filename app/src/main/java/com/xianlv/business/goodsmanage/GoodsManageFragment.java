package com.xianlv.business.goodsmanage;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.weight.LinearSpace;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.adapter.GoodsManageAdapter;
import com.xianlv.business.adapter.GoodsTypeAdapter;
import com.xianlv.business.bean.GoodsManageItem;
import com.xianlv.business.bean.GoodsTypeItem;
import com.xianlv.business.bean.eventbus.RefreshGoodsManageList;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

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

    @Override
    public void loadData() {
        super.loadData();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("typeId","");
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
                List<GoodsTypeItem> list = new ArrayList<>();
                list.add(new GoodsTypeItem());
                list.add(new GoodsTypeItem());
                list.add(new GoodsTypeItem());
//                getType();
                showView(list);
                break;
            case R.id.tv_search:
                et_search.setText("");
                break;
        }

    }
    private void getType(){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        new RxHttp<BaseListResult<GoodsTypeItem>>().send(ApiManager.getService().goodsType(map),
                new Response<BaseListResult<GoodsTypeItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<GoodsTypeItem> result) {
                        showView(result.data);
                    }
        });
    }

    private void showView(List<GoodsTypeItem> list){
        View view=View.inflate(mActivity, R.layout.base_fragment_recycleview_norefresh,null);
        RecyclerView rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        GoodsTypeAdapter adapter = new GoodsTypeAdapter();
        rv_list.setAdapter(adapter);
        adapter.setNewData(list);
        //第一个参数为要显示的view，后边为popuwindown的宽和高，也可以是具体数值
        PopupWindow pupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        pupWindow.setFocusable(true);//是否需要获取焦点
        pupWindow.setOutsideTouchable(true);//点击外边是否可以取消
        pupWindow.setBackgroundDrawable(new BitmapDrawable());//设置背景图片

        //在控件V某个位置显示，有LEFT,BOTTOM,TOP。后边是在x方向偏移的距离，和在y方向的偏移的距离
        pupWindow.showAtLocation(ll_tab, Gravity.RIGHT, 0, 0);

        //在控件V正下方显示
        pupWindow.showAsDropDown(ll_tab);//在正下方显示

        //取消
        pupWindow.dismiss();
    }

    private PopupWindow mPopWindow;
    private void showPopupWindow(List<GoodsTypeItem> list) {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.base_fragment_recycleview_norefresh, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setFocusable(false);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setTouchable(true);
        mPopWindow.setContentView(contentView);

        RecyclerView rv_list = contentView.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        GoodsTypeAdapter adapter = new GoodsTypeAdapter();
        rv_list.setAdapter(adapter);
        adapter.setNewData(list);
        int wPixel = this.getResources().getDisplayMetrics().widthPixels;

        int windowWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();

        int xoff = windowWidth/2 - contentView.getWidth()/2;
        //上面contentView.getWidth() 可能为0，因为popupwindow还没有绘制内容
        //设置显示和位置
        mPopWindow.showAsDropDown(contentView, xoff, 100);
    }


}
