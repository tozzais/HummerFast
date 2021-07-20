package com.xianlv.business.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CategoryAdapter;
import com.xianlv.business.adapter.CategorySecondAdapter;
import com.xianlv.business.bean.SceneItem;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseListResult;
import com.xianlv.business.http.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private CategorySecondAdapter mAdapter;
    private List<SceneItem> mDates;

    @Override
    public void loadData() {
        categoryAdapter = new CategoryAdapter();
        mAdapter = new CategorySecondAdapter();
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
        map.put("shopId",GlobalParam.getShopId());
        new RxHttp<BaseListResult<SceneItem>>().send(ApiManager.getService().getPayScenes(map),
                new Response<BaseListResult<SceneItem>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<SceneItem> result) {
                        mDates = result.data;
                        setData();
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    private void setData() {
        if (mDates == null || mDates.size() == 0){
            return;
        }
        mDates.get(0).isCheck = true;
        rv_category.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_category.setAdapter(categoryAdapter);
        categoryAdapter.setNewData(mDates);
        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_list.setAdapter(mAdapter);
        mAdapter.setNewData(mDates.get(0).scanQrcodes);
    }


    @Override
    public void initListener() {
        super.initListener();
        categoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            SceneItem sceneItem = mDates.get(position);
            for (int i = 0; i < mDates.size(); i++) {
                mDates.get(i).isCheck = i==position;
            }
            categoryAdapter.notifyDataSetChanged();
            mAdapter.setNewData(sceneItem.scanQrcodes);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SceneItem.SceneSecondItem sceneSecondItem = mAdapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra("name",sceneSecondItem.qrcodeName);
            intent.putExtra("qrcodeId",sceneSecondItem.typeId);
            setResult(RESULT_OK,intent);
            finish();
        });


    }




}