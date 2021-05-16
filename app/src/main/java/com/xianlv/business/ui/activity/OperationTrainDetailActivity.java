package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

import butterknife.BindView;

public class OperationTrainDetailActivity extends BaseActivity {


    public static final int OPERATION_TRAIN = 0;//培训说明
    public static final int DISTRIBUTION_INSTRUCTIONS = 1; //分销说明
    public static final int COMMON_PROBLEM = 2; //常见问题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;


    private int type;

    public static void launch(Context from, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, OperationTrainDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_operation_explain;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", OPERATION_TRAIN);
        if (type == OPERATION_TRAIN) {
            setBackTitle("操作培训");
            tvTitle.setText("操作培训说明");
        } else if (type == DISTRIBUTION_INSTRUCTIONS) {
            setBackTitle("分销说明");
            tvTitle.setText("分销培训说明");
        } else if (type == COMMON_PROBLEM) {
            setBackTitle("常见问题");
            tvTitle.setText("会员卡怎么核销");
        }


    }

    @Override
    public void loadData() {

    }
}