package com.xianlv.business.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CityListAdapter;
import com.xianlv.business.adapter.gv.CityAdapter;
import com.xianlv.business.bean.home.CityResult;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.LocationManager;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectCityActivity extends BaseActivity {


    @BindView(R.id.gv_area)
    GridView gv_area;
    @BindView(R.id.rv_city)
    RecyclerView rv_city;
    @BindView(R.id.tv_city)
    TextView tv_city;


    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, SelectCityActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("选择城市");


    }

    @Override
    public void loadData() {
        LocationManager.getInstance().startLocation(mActivity, new LocationManager.OnCallBack() {
            @Override
            public void onSuccess(AMapLocation amapLocation) {
                getCitys(amapLocation);

            }
        });





    }

    @OnClick({R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                break;

        }
    }


    private void  getCitys(AMapLocation amapLocation){
        TreeMap<String,String> map = new TreeMap<>();
        map.put("cityName",amapLocation.getCity());
        map.put("longitude",amapLocation.getLongitude()+"");
        map.put("latitude",amapLocation.getLatitude()+"");
        new RxHttp<BaseResult<CityResult>>().send(ApiManager.getService().getCityList(map),
                new Response<BaseResult<CityResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CityResult> result) {


                        CityAdapter chargeAdapter = new CityAdapter(mActivity, result.data.getHotCityList());
                        gv_area.setAdapter(chargeAdapter);

                        CityListAdapter cityListAdapter = new CityListAdapter();
                        rv_city.setLayoutManager(new LinearLayoutManager(mActivity));
                        rv_city.setAdapter(cityListAdapter);
                        cityListAdapter.setNewData(result.data.getCityList());

                    }
                });
    }




}