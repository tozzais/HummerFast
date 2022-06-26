package com.xianlv.business.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.wang.avi.AVLoadingIndicatorView;
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

    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;

    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.et_search)
    EditText et_search;


    private void refresh(boolean isRefresh) {
        loading.setVisibility(isRefresh ? View.VISIBLE : View.GONE);
        tv_location.setVisibility(isRefresh ? View.GONE : View.VISIBLE);
    }


    public static void launch(Activity from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, SelectCityActivity.class);
        from.startActivityForResult(intent,1002);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("选择城市");
        setLineVisibility();
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
            {
                //点击搜索要做的操作
                getCitys(et_search.getText().toString().trim());
                return true;
            }
            return false;
        });



    }

    @Override
    public void loadData() {
        refresh(true);
        LocationManager.getInstance().startLocation(mActivity, new LocationManager.OnCallBack() {
            @Override
            public void onSuccess(AMapLocation amapLocation) {
                tv_city.setText(amapLocation.getCity());
                getCitys(amapLocation);
            }

            @Override
            public void onFail() {
                refresh(false);
            }
        });


    }

    @OnClick({R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                loadData();
                break;

        }
    }


    private void getCitys(AMapLocation amapLocation) {
        getCitys(amapLocation.getCity());
//        TreeMap<String, String> map = new TreeMap<>();
//        map.put("cityName", amapLocation.getCity());
//        map.put("longitude", amapLocation.getLongitude() + "");
//        map.put("latitude", amapLocation.getLatitude() + "");
//        new RxHttp<BaseResult<CityResult>>().send(ApiManager.getService().getCityList(map),
//                new Response<BaseResult<CityResult>>(mActivity,Response.BOTH) {
//                    @Override
//                    public void onSuccess(BaseResult<CityResult> result) {
//                        if (result.data == null){
//                            return;
//                        }
//                        CityAdapter chargeAdapter = new CityAdapter(mActivity, result.data.getHotCityList());
//                        gv_area.setAdapter(chargeAdapter);
//                        CityListAdapter cityListAdapter = new CityListAdapter();
//                        rv_city.setLayoutManager(new LinearLayoutManager(mActivity));
//                        rv_city.setAdapter(cityListAdapter);
//                        cityListAdapter.setNewData(result.data.getCityList());
//                        gv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                setResultFinish(result.data.getHotCityList().get(position).getCityName());
//                            }
//                        });
//                        cityListAdapter.setOnItemClickListener(new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                                setResultFinish(result.data.getCityList().get(position).getCityName());
//                            }
//                        });
//
//                    }
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        refresh(false);
//                    }
//                });
    }

    private void getCitys(String city) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("cityName", city);
        new RxHttp<BaseResult<CityResult>>().send(ApiManager.getService().getCityList(map),
                new Response<BaseResult<CityResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CityResult> result) {
                        if (result.data == null){
                            return;
                        }
                        CityAdapter chargeAdapter = new CityAdapter(mActivity, result.data.getHotCityList());
                        gv_area.setAdapter(chargeAdapter);
                        CityListAdapter cityListAdapter = new CityListAdapter();
                        rv_city.setLayoutManager(new LinearLayoutManager(mActivity));
                        rv_city.setAdapter(cityListAdapter);
                        cityListAdapter.setNewData(result.data.getCityList());
                        gv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                setResultFinish(result.data.getHotCityList().get(position).getCityName());
                            }
                        });
                        cityListAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                                setResultFinish(result.data.getCityList().get(position).getCityName());
                            }
                        });

                    }
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        refresh(false);
                    }
                });
    }


    private void setResultFinish(String city){
        Intent intent = new Intent();
        intent.putExtra("city",city);
        setResult(RESULT_OK,intent);
        finish();


    }


}