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

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.adapter.HomeAdapter;
import com.xianlv.business.adapter.drop.ListDropDownAdapter;
import com.xianlv.business.bean.home.HomeResult;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.home.MapActivity;
import com.xianlv.business.weight.MyPopupWindow;

import java.util.Arrays;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment  {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.rl_park_fee)
    RelativeLayout rl_park_fee;
    @BindView(R.id.filter_view)
    View filterview;
    @BindView(R.id.view)
    View view;


    private HomeAdapter homeAdapter;
    private ListDropDownAdapter ageAdapter;
    private String[] ages = {"不限","停车免费","限时免费","停车收费"};

    private ListDropDownAdapter ageAdapter1;
    private String[] ages1 = {"不限","快充","慢充"};

    @OnClick({R.id.rl_park_fee, R.id.rl_charge_method, R.id.rl_filter, R.id.rl_map})
    public void onClick(View view) {
        switch (view.getId()) {
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




    }


    @Override
    public void loadData() {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("page","0");
        new RxHttp<BaseResult<HomeResult>>().send(ApiManager.getService().getHomeList(map),
                new Response<BaseResult<HomeResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeResult> result) {
                        homeAdapter.setNewData(result.data.component1());

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
                ageAdapter.setCheckItem(position);
                chargePosition = position;
                chargeWindow.dismiss();
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

    }
}
