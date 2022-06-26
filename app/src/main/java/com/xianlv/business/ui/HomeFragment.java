package com.xianlv.business.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.adapter.HomeAdapter;
import com.xianlv.business.adapter.banner.ImageAdapter;
import com.xianlv.business.adapter.drop.ListDropDownAdapter;
import com.xianlv.business.bean.home.AppBannerItem;
import com.xianlv.business.bean.home.HomeNoticeItem;
import com.xianlv.business.bean.home.HomeResult;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.home.MapActivity;
import com.xianlv.business.ui.activity.home.SelectCityActivity;
import com.xianlv.business.weight.CustomTextSwitcher;
import com.xianlv.business.weight.MyPopupWindow;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.rl_park_fee)
    RelativeLayout rl_park_fee;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_notice)
    CustomTextSwitcher tv_notice;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;


    private HomeAdapter homeAdapter;
    private ListDropDownAdapter ageAdapter;
    private String[] ages = {"不限","停车免费","限时免费","停车收费"};

    private ListDropDownAdapter ageAdapter1;
    private String[] ages1 = {"不限","快充","慢充"};

    @OnClick({R.id.tv_address, R.id.rl_park_fee, R.id.rl_charge_method, R.id.rl_filter, R.id.rl_map,
            R.id.tv_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                SelectCityActivity.launch(mActivity);
                break;
            case R.id.rl_park_fee:
                openSimplePopUpWindow();
                break;
            case R.id.rl_charge_method:
                showChargePopUpWindow();
                break;
            case R.id.rl_filter:
                if (mActivity instanceof MainActivity){
                    ((MainActivity)mActivity).openDrawerView();
                }
//                if (filterview.getVisibility() == View.GONE){
//                    filterview.setVisibility(View.VISIBLE);
//                }else {
//                    filterview.setVisibility(View.GONE);
//                }
                break;
            case R.id.rl_map:
                MapActivity.launch(mActivity);
                break;
            case R.id.tv_detail:
                tv_notice.setClick();
                break;

        }
    }



    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter();
        rv_list.setAdapter(homeAdapter);

        swipeLayout.setOnRefreshListener(this);






    }


    @Override
    public void loadData() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeLayout.setEnabled(true);
                } else {
                    swipeLayout.setEnabled(false);
                }
            }
        });



    }


    private MyPopupWindow parkWindow;
    private int parkPosition = 0;
    private void openSimplePopUpWindow() {
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(ages),parkPosition);
        ageView.setAdapter(ageAdapter);
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                parkPosition = position;
                parkWindow.dismiss();
                if (parkPosition == 0){
                    parkingFeeType = "";
                }else {
                    parkingFeeType = ""+(parkPosition - 1);
                }
                onRefresh();
            }
        });
        parkWindow = new MyPopupWindow();
        parkWindow.setContentView(ageView);
        parkWindow.setOutsideTouchable(true);
        parkWindow.setFocusable(true);
        parkWindow.setElevation(0);
        parkWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#50000000"));
        parkWindow.setBackgroundDrawable(dw);
        parkWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        parkWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        parkWindow.showAsDropDown(view);


    }

    private MyPopupWindow chargeWindow;
    private int chargePosition = 0;
    private void showChargePopUpWindow() {
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(getActivity(), Arrays.asList(ages1),chargePosition);
        ageView.setAdapter(ageAdapter1);
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter1.setCheckItem(position);
                chargePosition = position;
                chargeWindow.dismiss();
                if (position == 0){
                    chargeType = "";
                }else {
                    chargeType = ""+(position - 1);
                }
                onRefresh();
            }
        });
        chargeWindow = new MyPopupWindow();
        chargeWindow.setContentView(ageView);
        chargeWindow.setOutsideTouchable(true);
        chargeWindow.setFocusable(true);
        chargeWindow.setElevation(0);
        chargeWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#50000000"));
        chargeWindow.setBackgroundDrawable(dw);
        chargeWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        chargeWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        chargeWindow.showAsDropDown(view,0,0);


    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @BindView(R.id.tv_address)
    TextView tv_address;
    public void setAddress(String city) {
        tv_address.setText(city);
        tv_address.setVisibility(View.VISIBLE);
        this.city = city;
        getData(page);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private int page = 0;
    private double longitude; //经度
    private double latitude; //维度
    private String city;

    private String parkingFeeType = ""; // 0.停车免费 1.限时收费 2.收费
    private String chargeType = ""; //0.快充 1.慢充
    private String voltageType = ""; // 0.高低压兼容 1.高压 2.低压
    private String distance = ""; // 距离
    private String maxPrice = ""; // 距离
    private void  getData(int page){
        TreeMap<String,String> map = new TreeMap<>();
        map.put("page",page+"");
        map.put("provinceName",city);
        map.put("longitude",longitude+"");
        map.put("latitude",latitude+"");
        map.put("parkingFeeType",parkingFeeType+"");
        map.put("chargeType",chargeType+"");
        map.put("voltageType",voltageType+"");
        map.put("distance",distance+"");
        map.put("maxPrice",maxPrice+"");
        new RxHttp<BaseResult<HomeResult>>().send(ApiManager.getService().getHomeList(map),
                new Response<BaseResult<HomeResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeResult> result) {
                        homeAdapter.setNewData(result.data.component1());
                        setAppBanner(Objects.requireNonNull(result.data.getAppBannerList()));
                        setNotice(result.data.getAnnouncementList());
                    }
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });
    }

    private void setNotice(List<HomeNoticeItem> announcementList){
        announcementList.addAll(announcementList);
        tv_notice.bindData(announcementList)
                .setInAnimation(R.anim.animation_down_up_in_animation)
                .setOutAnimation(R.anim.animation_down_up_out_animation)
                .startSwitch(3000L);
    }

    private void setAppBanner(List<AppBannerItem> appBanner){
        List<String> images = new ArrayList<>();
        for (AppBannerItem appBannerItem:appBanner){
            images.add(appBannerItem.getLogo());
        }
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new ImageAdapter(images))
                .setIndicator(new CircleIndicator(mActivity));
//        banner.setBannerGalleryMZ(60,0.8f);
    }

    @Override
    public void onRefresh() {
        getData(page);

    }

    @Override
    public void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.destroy();
    }

    public void setFilter(String parkingFeeType, String chargeType, String voltageType, String distance, String maxPrice) {
        this.parkingFeeType = parkingFeeType;
        this.chargeType = chargeType;
        this.voltageType = voltageType;
        this.distance = distance;
        this.maxPrice = maxPrice;
        onRefresh();

    }
}
