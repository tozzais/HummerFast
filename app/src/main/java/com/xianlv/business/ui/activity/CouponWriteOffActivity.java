package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.CouponWriteOffFragment;

public class CouponWriteOffActivity extends BaseActivity {


    /**
     *
     * @param from
     * @param tag 1 储值卡 2优惠券 3商品
     * @param category 1 扫码获取 2 手机号查询  3卡号查询
     * @param key 查询的内容
     */
    public static void launch(Context from,String tag,String category,String key) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, CouponWriteOffActivity.class);
        intent.putExtra("tag",tag);
        intent.putExtra("category",category);
        intent.putExtra("key",key);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("优惠券核销");

    }

    @Override
    public void loadData() {
        String tag = getIntent().getStringExtra("tag");
        String category = getIntent().getStringExtra("category");
        String key = getIntent().getStringExtra("key");
        CouponWriteOffFragment fragment = CouponWriteOffFragment.newInstance(tag,category,key);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}