package com.xianlv.business.ui.roommanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.goodsmanage.GoodsManageFragment;

/**
 * 房价管理
 */
public class RoomPriceManageActivity extends BaseActivity {

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, RoomPriceManageActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle(" 房价管理");

    }

    @Override
    public void loadData() {
        GoodsManageFragment fragment = new GoodsManageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}