package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.ui.fragment.OperationTrainFragment;

public class OperationTrainActivity extends BaseActivity {

    public static final int OPERATION_TRAIN = 0;//培训说明
    public static final int DISTRIBUTION_INSTRUCTIONS = 1; //分销说明
    public static final int COMMON_PROBLEM = 2; //常见问题

    private int type = 0;


    public static void launch(Context from,int type) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, OperationTrainActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        type = getIntent().getIntExtra("type",OPERATION_TRAIN);
        setLineVisibility();
        if (type == OPERATION_TRAIN){
            setBackTitle("操作培训");
        }else if (type == DISTRIBUTION_INSTRUCTIONS){
            setBackTitle("分销说明");
        }else if (type == COMMON_PROBLEM){
            setBackTitle("常见问题");
        }

    }

    @Override
    public void loadData() {
        OperationTrainFragment fragment = OperationTrainFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }
}