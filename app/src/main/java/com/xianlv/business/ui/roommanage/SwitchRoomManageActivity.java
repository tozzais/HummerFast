package com.xianlv.business.ui.roommanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

/**
 * 商品管理
 */
public class SwitchRoomManageActivity extends BaseActivity {

    /*
    0 开关房
    1 房价管理
     */
    public static void launch(Context from,int type) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SwitchRoomManageActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    private int type;
    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        if (type == 0){
            setBackTitle("开关房管理");
        }else if (type == 1){
            setBackTitle("房价管理");
        }


    }

    @Override
    public void loadData() {
        SwitchRoomManageFragment fragment = SwitchRoomManageFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}