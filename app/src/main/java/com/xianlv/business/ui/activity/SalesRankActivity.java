package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.tozzais.baselibrary.adapter.GoodsDetailPagerAdapter;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.RankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SalesRankActivity extends BaseActivity {

    public static final int PERSON = 0;
    public static final int TEAM = 1;

    private int type = 0;


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SalesRankActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }




    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_bg1)
    ImageView ivBg1;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());

    }

    @Override
    public void loadData() {
        fragmentList.add(RankFragment.newInstance(0));
        fragmentList.add(RankFragment.newInstance(1));
        fragmentList.add(RankFragment.newInstance(2));
        List<String> list = new ArrayList<>();
        list.add("年");
        list.add("月");
        list.add("日");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);

    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_rank;
    }



    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, ivBg);
        StatusBarUtil.setDarkMode(this);
    }

    @OnClick({R.id.tv_year, R.id.tv_month, R.id.tv_day})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_year:
                break;
            case R.id.tv_month:
                break;
            case R.id.tv_day:
                break;
        }
    }

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();
}