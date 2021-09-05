package com.xianlv.business.ui.roommanage;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.xianlv.business.R;
import com.xianlv.business.adapter.switchroom.DropStoreAdapter;
import com.xianlv.business.adapter.switchroom.SwitchRoomNameAdapter;
import com.xianlv.business.adapter.switchroom.SwitchRoomNameManageAdapter;
import com.xianlv.business.bean.eventbus.RefreshRoomPrice;
import com.xianlv.business.bean.switchroom.DateBean;
import com.xianlv.business.bean.switchroom.PromotionRoom;
import com.xianlv.business.bean.switchroom.ShopBean;
import com.xianlv.business.bean.switchroom.SwitchDetailItem;
import com.xianlv.business.bean.switchroom.SwitchRoomItem;
import com.xianlv.business.bean.switchroom.SwitchRoomShowData;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.ListResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.pop.CommonPopupWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class SwitchRoomManageFragment extends BaseListFragment<String> {



    @BindView(R.id.ll_tab)
    LinearLayout ll_tab;
    @BindView(R.id.rv_drop)
    RecyclerView rv_drop;
    @BindView(R.id.tv_tab_text)
    TextView tv_tab_text;

    @BindView(R.id.rv_list_data)
    RecyclerView rv_list_data;

    private int type;
    public static SwitchRoomManageFragment newInstance(int type){
        SwitchRoomManageFragment cartFragment = new SwitchRoomManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_switch_room_manage;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SwitchRoomNameAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂时没有数据哦~");

    }

    private String shopId = "";
    private String dayDate = "";
    @Override
    public void loadData() {
        super.loadData();
        if (list == null){
            Calendar instance = Calendar.getInstance();
            dayDate = instance.get(Calendar.YEAR)+"-"+(instance.get(Calendar.MONTH)+1)+"-"+instance.get(Calendar.DAY_OF_MONTH);
            getType();
            return;
        }
        getData();



    }
    private void getData(){
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("shopId", shopId);
        map.put("dayDate",""+dayDate);
        new RxHttp<BaseResult<ListResult<SwitchRoomItem>>>().send(ApiManager.getService().getSwitchRoomList(map),
                new Response<BaseResult<ListResult<SwitchRoomItem>>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<SwitchRoomItem>> result) {
                        dataList = result.data.list;
                        setData();
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

    private List<SwitchRoomItem> dataList;
    private void setData(){
        if (dataList == null || dataList.size() == 0){
            showContent();
            tsg("暂无数据");
            return;
        }

        //房型的集合
        List<String> roomNameList = new ArrayList<>();
        //房型数据的集合
        List<DateBean> skuVoList = dataList.get(0).skuVoList;
        List<SwitchRoomShowData> detailList = new ArrayList<>();
        for (DateBean dateBean:skuVoList){
            SwitchRoomShowData switchRoomShowData = new SwitchRoomShowData();
            switchRoomShowData.data = dateBean;
            detailList.add(switchRoomShowData);
        }
        for (int i = 0; i < dataList.size(); i++) {
            //房型
            SwitchRoomItem switchRoomItem = dataList.get(i);
            List<PromotionRoom> calendarVos = switchRoomItem.calendarVos;
            for (int j = 0; j < calendarVos.size(); j++) {
                //促销方案
                PromotionRoom promotionRoom = calendarVos.get(j);
                roomNameList.add(promotionRoom.roomName+"-"+promotionRoom.promotionName);
                //详情
                List<SwitchDetailItem> roomSkuVoList = promotionRoom.roomSkuVoList;

                for (int k = 0; k < roomSkuVoList.size(); k++) {
                    SwitchDetailItem switchDetailItem = roomSkuVoList.get(k);
                    switchDetailItem.roomName = "【"+promotionRoom.roomName+"-"+promotionRoom.promotionName+"】";
                    SwitchRoomShowData switchRoomShowData = detailList.get(k);
                    switchRoomShowData.list.add(switchDetailItem);
                }
            }
        }

        setData(roomNameList);



        rv_list_data.setLayoutManager(new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false));
        SwitchRoomNameManageAdapter adapter = new SwitchRoomNameManageAdapter(type);
        rv_list_data.setAdapter(adapter);
        adapter.setNewData(detailList);


    }


    @Override
    public void initListener() {
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshRoomPrice){
            onRefresh();
        }
    }
    @OnClick({R.id.tv_tab_text})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab_text:
                if (list != null)
                show(list);
                break;

        }

    }
    private List<ShopBean> list;
    private void getType(){
        if (list == null){
            Map<String,String> map = new HashMap<>();
            map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
            new RxHttp<BaseListResult<ShopBean>>().send(ApiManager.getService().getShop(map),
                    new Response<BaseListResult<ShopBean>>(mActivity) {
                        @Override
                        public void onSuccess(BaseListResult<ShopBean> result) {
                            list = new ArrayList<>();
                            list.addAll(result.data);
                            if (list == null || list.size() == 0){
                                return;
                            }
                            ShopBean goodsTypeItem = list.get(0);
                            tv_tab_text.setText(goodsTypeItem.shopName);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv_tab_text.setTextColor(getContext().getColor(R.color.baseColor));
                            }
                            shopId = goodsTypeItem.shopId;
                            getData();
                        }
                    });
        }
    }




    private CommonPopupWindow popupWindow;
    public void show(List<ShopBean> list){
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
        DropStoreAdapter adapter = new DropStoreAdapter();
        rv_list.setAdapter(adapter);
        adapter.setNewData(list);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            ShopBean goodsTypeItem = list.get(position);
            tv_tab_text.setText(goodsTypeItem.shopName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_tab_text.setTextColor(getContext().getColor(R.color.baseColor));
            }
            shopId = goodsTypeItem.shopId;
            onRefresh();
            popupWindow.dismiss();
        });
    }




}
