package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.R;

import butterknife.BindView;

public class BonusDescriptionActivity extends BaseActivity {

    public static final int PERSON = 1;
    public static final int TEAM = 2;

    private int type = PERSON;


    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, BonusDescriptionActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_bg1)
    ImageView ivBg1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());

        type = getIntent().getIntExtra("type", PERSON);
        if (type == PERSON) {
            ivBg.setBackgroundResource(R.mipmap.icon_bouns_des3);
            ivBg1.setBackgroundResource(R.mipmap.icon_bouns_des4);
        }else {
            ivBg.setBackgroundResource(R.mipmap.icon_bouns_des1);
            ivBg1.setBackgroundResource(R.mipmap.icon_bouns_des2);
        }


    }


    @Override
    public void loadData() {


    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_rank_explain;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, ivBg);
        StatusBarUtil.setDarkMode(this);
    }



    @Override
    public void initListener() {
        super.initListener();

    }
}