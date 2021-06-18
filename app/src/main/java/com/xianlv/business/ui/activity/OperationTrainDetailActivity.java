package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.ProblemItem;

import butterknife.BindView;

public class OperationTrainDetailActivity extends BaseActivity {


    public static final int OPERATION_TRAIN = 0;//培训说明
    public static final int DISTRIBUTION_INSTRUCTIONS = 1; //分销说明
    public static final int COMMON_PROBLEM = 2; //常见问题
    @BindView(R.id.tv_content_title)
    TextView tv_content_title;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.web_view)
    WebView web_view;


    private ProblemItem problemItem;

    public static void launch(Context from, ProblemItem problemItem, int type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, OperationTrainDetailActivity.class);
        intent.putExtra("problemItem", problemItem);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_operation_explain;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        problemItem = (ProblemItem) getIntent().getSerializableExtra("problemItem");
        int type = getIntent().getIntExtra("type",0);
        if (type == OPERATION_TRAIN) {
            setBackTitle("操作培训详情");
        } else if (type == DISTRIBUTION_INSTRUCTIONS) {
            setBackTitle("分销说明详情");
        } else if (type == COMMON_PROBLEM) {
            setBackTitle("常见问题详情");
        }

    }

    @Override
    public void loadData() {
        tv_content_title.setText(problemItem.title);
        tvTime.setText(problemItem.createTime);
        setData(problemItem.content);

    }

    public void setData(String content){
        String varjs = "<style>p{margin:0;}</style> <script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        web_view.loadDataWithBaseURL("", varjs + content, "text/html", "UTF-8", null);
//        web_view.loadData(content, "text/html", "UTF-8");
    }
}