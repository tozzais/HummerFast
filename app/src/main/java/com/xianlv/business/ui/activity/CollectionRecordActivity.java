package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.tozzais.baselibrary.adapter.GoodsDetailPagerAdapter;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.ReceivePayOrderFragment;
import com.xianlv.business.weight.SpikeTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectionRecordActivity extends BaseActivity {



    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SpikeTabLayout tablayout;


    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Context activity) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, CollectionRecordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("收款记录");

    }

    @Override
    public void loadData() {

        fragmentList.add(ReceivePayOrderFragment.newInstance(1));
        fragmentList.add(ReceivePayOrderFragment.newInstance(2));
        List<String> list = new ArrayList<>();
        list.add("收款订单");
        list.add("退款订单");
        tablayout.setTitle(list);
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));

    }
}