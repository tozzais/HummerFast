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
import com.xianlv.business.ui.fragment.CheckInFragment;
import com.xianlv.business.ui.fragment.RecordCardFragment;
import com.xianlv.business.ui.fragment.RecordCouponFragment;
import com.xianlv.business.ui.fragment.RecordMallFragment;
import com.xianlv.business.weight.CommentTabLayout;
import com.xianlv.business.weight.SpikeTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MallCouponWriteOffRecordActivity extends BaseActivity {


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
        Intent intent = new Intent(activity, MallCouponWriteOffRecordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("核销记录");

    }

    @Override
    public void loadData() {

        fragmentList.add(new RecordCouponFragment());
        fragmentList.add(new RecordCardFragment());
        fragmentList.add(new RecordMallFragment());
        List<String> list = new ArrayList<>();
        list.add("优惠券");
        list.add("储值卡");
        list.add("商城券");
        tablayout.setTitle(list);
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        viewpager.setOffscreenPageLimit(4);

    }
}