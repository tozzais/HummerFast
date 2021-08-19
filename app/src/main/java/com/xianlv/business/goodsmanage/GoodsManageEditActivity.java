package com.xianlv.business.goodsmanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.GoodsManageItemSku;

import java.util.ArrayList;

/**
 * 商品管理
 */
public class GoodsManageEditActivity extends BaseActivity {

    public static void launch(Context from, ArrayList<GoodsManageItemSku> list) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, GoodsManageEditActivity.class);
        intent.putExtra("list",list);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("编辑");

    }

    @Override
    public void loadData() {

        GoodsManageEditFragment fragment = GoodsManageEditFragment.newInstance(getIntent().getParcelableArrayListExtra("list"));

        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}