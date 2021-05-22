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

    public static final int PERSON = 1;
    public static final int TEAM = 2;

    private int type = PERSON;


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, SalesRankActivity.class);
        intent.putExtra("type", type);
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

        type = getIntent().getIntExtra("type", PERSON);
        if (type == TEAM) {
            ivBg.setBackgroundResource(R.mipmap.icon_rank_bg3);
            ivBg1.setBackgroundResource(R.mipmap.icon_rank_bg4);
            tvYear.setBackgroundResource(R.drawable.shape_orange5);
            tvMonth.setTextColor(getResources().getColor(R.color.orange));
            tvMonth.setTextColor(getResources().getColor(R.color.orange));
        }else {
            ivBg.setBackgroundResource(R.mipmap.icon_rank_bg1);
            ivBg1.setBackgroundResource(R.mipmap.icon_rank_ng2);
        }

        selectTab(0);

    }

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    public void loadData() {
        fragmentList.add(RankFragment.newInstance(0,type));
        fragmentList.add(RankFragment.newInstance(1,type));
        fragmentList.add(RankFragment.newInstance(2,type));
        List<String> list = new ArrayList<>();
        list.add("年");
        list.add("月");
        list.add("日");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList, list);
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

    @OnClick({R.id.tv_year, R.id.tv_month, R.id.tv_day, R.id.tv_explain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_year:
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_month:
                viewpager.setCurrentItem(1);
                break;
            case R.id.tv_day:
                viewpager.setCurrentItem(2);
                break;
            case R.id.tv_explain:
                BonusDescriptionActivity.launch(mActivity,type);
                break;
        }
    }

    private void selectTab(int position) {
        int selectRes = type == TEAM ? R.drawable.shape_orange5 : R.drawable.shape_blue5;
        int selectColor = getResources().getColor(R.color.white);
        int defaultRes = R.drawable.shape_line_gray5;
        int defaultColor = type == TEAM ? getResources().getColor(R.color.orange) :
                getResources().getColor(R.color.baseColor);
        tvYear.setBackgroundResource(position == 0 ? selectRes : defaultRes);
        tvYear.setTextColor(position == 0 ? selectColor : defaultColor);
        tvMonth.setBackgroundResource(position == 1 ? selectRes : defaultRes);
        tvMonth.setTextColor(position == 1 ? selectColor : defaultColor);
        tvDay.setBackgroundResource(position == 2 ? selectRes : defaultRes);
        tvDay.setTextColor(position == 2 ? selectColor : defaultColor);


    }

    @Override
    public void initListener() {
        super.initListener();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}