package com.xianlv.business.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.DataUtil;
import com.xianlv.business.R;
import com.xianlv.business.adapter.HomeAdapter;
import com.xianlv.business.adapter.drop.ListDropDownAdapter;
import com.xianlv.business.weight.MyPopupWindow;
import com.xianlv.business.weight.PopupWindowManager;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment  {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.rl_park_fee)
    RelativeLayout rl_park_fee;
    @BindView(R.id.filter_view)
    View filterview;

    private HomeAdapter homeAdapter;
    private ListDropDownAdapter ageAdapter;
    private String[] ages = {"不限","停车免费","限时免费","停车收费"};

    private ListDropDownAdapter ageAdapter1;
    private String[] ages1 = {"不限","快充","慢充"};

    @OnClick({R.id.rl_park_fee, R.id.rl_charge_method, R.id.rl_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_park_fee:
                openSimplePopUpWindow();
                break;
            case R.id.rl_charge_method:
                showChargePopUpWindow();
                break;
            case R.id.rl_filter:
                if (filterview.getVisibility() == View.GONE){
                    filterview.setVisibility(View.VISIBLE);
                }else {
                    filterview.setVisibility(View.GONE);
                }
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
        homeAdapter.setNewData(DataUtil.getData(2));



    }


    @Override
    public void loadData() {


    }


    private void openSimplePopUpWindow() {
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);
        PopupWindowManager.getInstance().init(ageView).setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindowManager.getInstance().close();
            }
        }).setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PopupWindowManager.getInstance().close();
                return false;
            }
        }).showAsDropDown(rl_park_fee);
    }

    private MyPopupWindow chargeWindow;
    private void showChargePopUpWindow() {
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter1 = new ListDropDownAdapter(getActivity(), Arrays.asList(ages1));
        ageView.setAdapter(ageAdapter1);

        chargeWindow = new MyPopupWindow();
        chargeWindow.setContentView(ageView);
        chargeWindow.setOutsideTouchable(false);
        chargeWindow.setFocusable(true);
        chargeWindow.setElevation(0);
        chargeWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#50000000"));
        chargeWindow.setBackgroundDrawable(dw);
        chargeWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        chargeWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        chargeWindow.showAsDropDown(rl_park_fee);


    }

    @Override
    public void initListener() {
        super.initListener();

    }

}
