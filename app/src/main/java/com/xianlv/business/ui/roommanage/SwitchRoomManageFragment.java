package com.xianlv.business.ui.roommanage;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
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
import com.xianlv.business.util.datapick.DataPickItem;
import com.xianlv.business.util.datapick.DataPickUtil;
import com.xianlv.business.util.datapick.DataRecycleAdapter;
import com.xianlv.business.util.pop.CommonPopupWindow;
import com.xianlv.business.weight.MyRecycleView;

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
    @BindView(R.id.tv_tab_text)
    TextView tv_tab_text;
    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.ll_space)
    View ll_space;

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

        Calendar cale = Calendar.getInstance();
        month = cale.get(Calendar.MONTH) + 1;
        year = cale.get(Calendar.YEAR);
        tv_data.setText(getShowDate(year,month,cale.get(Calendar.DAY_OF_MONTH)));


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
//                        tsg(dataList.get(0).calendarVos.get(0).roomSkuVoList.get(0).price);
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

        setData(true,roomNameList);

        rv_list_data.setLayoutManager(new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false));
        adapter = new SwitchRoomNameManageAdapter(type);
        rv_list_data.setAdapter(adapter);
        adapter.setNewData(detailList);

//        if (adapter != null) {
//            for (RecyclerView recyclerView1:adapter.getList()){
//                recyclerView1.addOnScrollListener(scrollListeners[1]);
//            }
//        }



    }
    private SwitchRoomNameManageAdapter adapter;

//    private  RecyclerView.OnScrollListener[] scrollListeners;

    @Override
    public void initListener() {

//        scrollListeners = new RecyclerView.OnScrollListener[2];
//        scrollListeners[0] = new RecyclerView.OnScrollListener( )
//        {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//            {
//                super.onScrolled(recyclerView, dx, dy);
//                if (adapter != null ) {
//                    for (RecyclerView recyclerView1:adapter.getList()){
//                        recyclerView1.removeOnScrollListener(scrollListeners[1]);
//                        recyclerView1.scrollBy(dx, dy);
//                        recyclerView1.addOnScrollListener(scrollListeners[1]);
//                    }
//                }
//
//            }
//        };
//        scrollListeners[1] = new RecyclerView.OnScrollListener( )
//        {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//            {
//                super.onScrolled(recyclerView, dx, dy);
//                mRecyclerView.removeOnScrollListener(scrollListeners[0]);
//                mRecyclerView.scrollBy(dx, dy);
//                mRecyclerView.addOnScrollListener(scrollListeners[0]);
//            }
//        };
//        mRecyclerView.addOnScrollListener(scrollListeners[0]);



    }

    @Override
    public void onEvent(Object o) {
        if (o instanceof RefreshRoomPrice){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            },500);

        }
    }
    @OnClick({R.id.tv_tab_text,R.id.tv_data})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab_text:
                if (list != null)
                show(list,1);
                break;
            case R.id.tv_data:
                show(list,2);
                break;

        }

    }
    private List<ShopBean> list;
    private void getType(){
        if (list == null){
            Map<String,String> map = new HashMap<>();
            map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
            new RxHttp<BaseListResult<ShopBean>>().send(ApiManager.getService().getShop(map),
                    new Response<BaseListResult<ShopBean>>(mActivity,Response.BOTH) {
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
    }




    private CommonPopupWindow popupWindow;
    private RecyclerView rv_list;
    private LinearLayout ll_calendar;
    public void show(List<ShopBean> list,int type){
        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(ll_space);
            }
            if (type == 1){
                rv_list.setVisibility(View.VISIBLE);
                ll_calendar.setVisibility(View.GONE);
            }else {
                rv_list.setVisibility(View.GONE);
                ll_calendar.setVisibility(View.VISIBLE);
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
        popupWindow.showAsDropDown(ll_space);
        popupWindow.setFocusable(true);

        rv_list =  popupWindow.getContentView().findViewById(R.id.rv_list);
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


        getMonthData();
        View contentView = popupWindow.getContentView();
        ll_calendar =  contentView.findViewById(R.id.ll_calendar);
        TextView tv_data1 =  contentView.findViewById(R.id.tv_current_data);
        ImageView tv_cancel = contentView.findViewById(R.id.tv_cancel);
        ImageView tv_sure = contentView.findViewById(R.id.tv_sure);

        MyRecycleView gl_data = contentView.findViewById(R.id.viewpager);
        gl_data.setLayoutManager(new GridLayoutManager(mActivity,7));
        if (dataRecycleAdapter == null){
            dataRecycleAdapter = new DataRecycleAdapter((date,position) -> {
                dayDate = year+"-"+month+"-"+date;
                tv_data.setText(getShowDate(year,month,date));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_data.setTextColor(getContext().getColor(R.color.baseColor));
                }
                onRefresh();
                popupWindow.dismiss();
            });
        }
        gl_data.setAdapter(dataRecycleAdapter);
        dataRecycleAdapter.setNewData(currentMonthDay);
        tv_sure.setOnClickListener(v -> {
            if (month<12){
                month = month+1;
            }else {
                year++;
                month=1;
            }
            tv_data1.setText(year+"年"+month+"月");
            getMonthData();
            dataRecycleAdapter.notifyDataSetChanged();
        });
        tv_cancel.setOnClickListener(v -> {
            if (month>1){
                month = month-1;
            }else {
                year--;
                month=12;
            }
            tv_data1.setText(year+"年"+month+"月");
            getMonthData();
            dataRecycleAdapter.notifyDataSetChanged();
        });
        tv_data1.setText(year+"年"+month+"月");
        if (type == 1){
            rv_list.setVisibility(View.VISIBLE);
            ll_calendar.setVisibility(View.GONE);
        }else {
            rv_list.setVisibility(View.GONE);
            ll_calendar.setVisibility(View.VISIBLE);
        }
    }

    private  int year,month;
    private DataRecycleAdapter dataRecycleAdapter;
    private List<DataPickItem> currentMonthDay = new ArrayList<>();



    public void getMonthData(){
        currentMonthDay.clear();
        Calendar cale = Calendar.getInstance();
        int curryear = cale.get(Calendar.YEAR);
        int currmonth = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        DataPickUtil instance = DataPickUtil.getInstance();
        int daysOfMonth = instance.getDaysOfMonth(year, month);
        int firstDayInWeek = instance.getFirstDayInWeek(year, month - 1);
        for (int i = 0; i < (firstDayInWeek - 1); i++) {
            currentMonthDay.add(new DataPickItem("", false, false));
        }
        for (int i = 1; i <= daysOfMonth; i++) {
            if (curryear<year){
                currentMonthDay.add(new DataPickItem(i + "", true, false));
            }else if (curryear>year){
                currentMonthDay.add(new DataPickItem(i + "", false, false));
            }else {
                if (currmonth<month){
                    currentMonthDay.add(new DataPickItem(i + "", true, false));
                }else if (currmonth>month){
                    currentMonthDay.add(new DataPickItem(i + "", false, false));
                }else {
                    if (i >day) {
                        currentMonthDay.add(new DataPickItem(i + "", true, false));
                    } else if (i <day) {
                        currentMonthDay.add(new DataPickItem(i + "", false, false));
                    }else {
                        currentMonthDay.add(new DataPickItem(i + "", true, true));
                    }
                }
            }

        }
    }

    private String getDayDate(int i){
        return i<10?"0"+i:i+"";

    }

    private String getShowDate(int year,int month,int day){
        DataPickUtil instance = DataPickUtil.getInstance();
        int daysOfMonth = instance.getDaysOfMonth(year, month);
        if (day > daysOfMonth-6){
            int sub = day - daysOfMonth+6;
            if (month ==12){
                return (getDayDate(month)+"."+getDayDate(day)
                        +"-01.0"+sub);
            }else {
                return (getDayDate(month)+"."+getDayDate(day)
                        +"-"+getDayDate(month+1)+".0"+sub);
            }
        }else {
            return getDayDate(month)+"."+getDayDate(day)
                    +"-"+getDayDate(month)+"."+getDayDate((day+6));
        }

    }

}
