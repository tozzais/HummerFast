package com.xianlv.business.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CategoryAdapter;
import com.xianlv.business.bean.CategoryItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScenesActivity extends BaseActivity {


    @BindView(R.id.rv_category)
    RecyclerView rv_category;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;


    public static void launch(Activity from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ScenesActivity.class);
        from.startActivityForResult(intent,101);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_categray;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("选择使用场景");
        setLineVisibility();



    }
    private CategoryAdapter categoryAdapter;
    private CategoryAdapter mAdapter;

    @Override
    public void loadData() {
        List<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        list.add(new CategoryItem("SPA"));
        categoryAdapter = new CategoryAdapter();
        mAdapter = new CategoryAdapter();

        rv_category.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_category.setAdapter(categoryAdapter);
        categoryAdapter.setNewData(list);

        List<CategoryItem> list1 = new ArrayList<>();
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));
        list1.add(new CategoryItem("中餐厅"));

        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_list.setAdapter(mAdapter);
        mAdapter.setNewData(list1);

    }







    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            setResult(RESULT_OK);
            finish();
        });

    }
}