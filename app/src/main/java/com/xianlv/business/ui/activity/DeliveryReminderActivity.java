package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.adapter.GoodsDetailPagerAdapter;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.DeliverReminderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeliveryReminderActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_tab)
    TabLayout tablayout;


    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Context activity) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, DeliveryReminderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        activity.startActivity(intent);
    }


//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_tab_layout;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(com.tozzais.baselibrary.R.mipmap.back);
        toolbar.setNavigationOnClickListener(view -> back());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected int getToolbarLayout() {
        return com.tozzais.baselibrary.R.layout.base_toolbar_tablayout;
    }

    @Override
    public void loadData() {

        fragmentList.add(new DeliveryReminderFragment());
        fragmentList.add(DeliverReminderFragment.newInstance(0));
        List<String> list = new ArrayList<>();
        list.add("待处理");
        list.add("历史订单");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));

    }
}