package com.xianlv.business.order.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.tozzais.baselibrary.adapter.GoodsDetailPagerAdapter;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.xianlv.business.R;
import com.xianlv.business.weight.SpikeTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReserveOrderFragment extends BaseFragment {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SpikeTabLayout tablayout;


    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();


    private int type = 0;
    public static ReserveOrderFragment newInstance(int type){
        ReserveOrderFragment cartFragment = new ReserveOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }



    @Override
    public void loadData() {
        type = getArguments().getInt("type");
        fragmentList.add(CommonReserveFragment.newInstance(type));
        fragmentList.add(GoodsReserveFragment.newInstance(type));
        List<String> list = new ArrayList<>();
        list.add("普通预约");
        list.add("商品预约");
        tablayout.setTitle(list);
        adapter = new GoodsDetailPagerAdapter(getChildFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

    }
}