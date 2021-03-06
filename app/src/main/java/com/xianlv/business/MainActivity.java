package com.xianlv.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.adapter.gv.FilterAdapter;
import com.xianlv.business.bean.local.FilterBean;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.ui.AgreementWebViewActivity;
import com.xianlv.business.ui.BalanceFragment;
import com.xianlv.business.ui.ChargeFragment;
import com.xianlv.business.ui.HomeFragment;
import com.xianlv.business.ui.MineFragment;
import com.xianlv.business.ui.WelfareFragment;
import com.xianlv.business.ui.activity.LoginActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends CheckPermissionActivity implements AMapLocationListener {


    /**
     * 从各个登录地方进去的方法
     * 登录界面  绑定界面 快捷登录
     *
     * @param context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

    }


    public static void launch(Activity context, int mPosition) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("position", mPosition);
        context.startActivity(intent);
        context.finish();

    }

    public static final int HOME = 0, CATEGORY = 1, COMMUNITY = 2,
            CART = 3, MINE = 4;
    private static final String TAG_HOME = "tag_home", TAG_CATEGROY = "tag_categroy",
            TAG_COMMUNITY = "tag_community", TAG_CART = "tag_cart", TAG_MINE = "tag_mine";


    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_category)
    ImageView ivCategory;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.iv_community)
    ImageView ivCommunity;
    @BindView(R.id.tv_community)
    TextView tvCommunity;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.tv_cart_number)
    TextView tv_cart_number;
    @BindView(R.id.ll_main)
    public LinearLayout ll_main;
    private int mPosition;//当前选中的底部菜单
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private BalanceFragment balanceFragment;
    private ChargeFragment chargeFragment;
    private WelfareFragment welfareFragment;
    private MineFragment mineFragment;


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_content)
    LinearLayout drawer_content;


    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        selectFragment(HOME);


    }


    @Override
    public void loadData() {
        checkPermissions(needPermissions);
//        if (!GlobalParam.getAgreePrivacy()){
//            //是否同意隐私
//            showPrivacy("");
//        }else {
//            getVersion();
//        }
        setDrawerView();

    }


    public static String[] needPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void permissionGranted() {
        try {
            startLocation();
        }catch (Exception exception){

        }

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
        StatusBarUtil.setTransparentForImageViewInFragment(MainActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int position = intent.getIntExtra("position", -1);
            if (position != -1)
                selectFragment(position);
        }
    }

    public void selectFragment(int position) {
        switch (position) {
            case HOME:
                mPosition = HOME;
                StatusBarUtil.setLightMode(this);
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case CATEGORY:
                StatusBarUtil.setLightMode(this);
                mPosition = CATEGORY;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case COMMUNITY:
                StatusBarUtil.setLightMode(this);
                mPosition = COMMUNITY;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case CART:
//                if (!GlobalParam.getUserLogin()){
////                    SelectLoginWayActivity.launch(mActivity);
//                    return;
//                }
                StatusBarUtil.setLightMode(this);
                mPosition = CART;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case MINE:
                if (!GlobalParam.getUserLogin()){
                    LoginActivity.launch(mActivity);
                    return;
                }
                StatusBarUtil.setLightMode(this);
                mPosition = MINE;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
        }


    }

    /**
     * 设置底部菜单被选中后字体、图片的颜色
     *
     * @param pos
     */
    private void setTabChecked(int pos) {
        ivHome.setImageResource(pos == HOME ? R.mipmap.home_selete : R.mipmap.home);
        tvHome.setTextColor(pos == HOME ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivCategory.setImageResource(pos == CATEGORY ? R.mipmap.category_selete : R.mipmap.category);
        tvCategory.setTextColor(pos == CATEGORY ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivCommunity.setImageResource(pos == COMMUNITY ? R.mipmap.integral_shop_selete : R.mipmap.integral_shop);
        tvCommunity.setTextColor(pos == COMMUNITY ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivCart.setImageResource(pos == CART ? R.mipmap.cart_selete : R.mipmap.cart);
        tvCart.setTextColor(pos == CART ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivMine.setImageResource(pos == MINE ? R.mipmap.mine_selete : R.mipmap.mine);
        tvMine.setTextColor(pos == MINE ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));

    }

    private void setTabSelection(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启Fragment事务
        hideFragments(transaction);//隐藏所有页面（）
        switch (position) {
            case HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_container, homeFragment, TAG_HOME);
                } else {
                    transaction.show(homeFragment);
                }
                break;

            case CATEGORY:
                if (balanceFragment == null) {
                    balanceFragment = BalanceFragment.newInstance(com.xianlv.business.global.Constant.balance_charge_url);
                    transaction.add(R.id.fl_container, balanceFragment, TAG_CATEGROY);
                } else {
                    transaction.show(balanceFragment);
                }
                break;
            case COMMUNITY:
                if (chargeFragment == null) {
                    chargeFragment = ChargeFragment.newInstance(com.xianlv.business.global.Constant.scan_charge_url);
                    transaction.add(R.id.fl_container, chargeFragment, TAG_COMMUNITY);
                } else {
                    transaction.show(chargeFragment);
                }
                break;

            case CART:
                if (welfareFragment == null) {
                    welfareFragment = WelfareFragment.newInstance(com.xianlv.business.global.Constant.welfare_activity_url);
                    transaction.add(R.id.fl_container, welfareFragment, TAG_CART);
                } else {
                    transaction.show(welfareFragment);
                }
                break;
            case MINE:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_container, mineFragment, TAG_MINE);
                } else {
                    transaction.show(mineFragment);
                }

                break;
        }
        // 提交
        transaction.commit();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (balanceFragment != null) {
            transaction.hide(balanceFragment);
        }
        if (chargeFragment != null) {
            transaction.hide(chargeFragment);
        }
        if (welfareFragment != null) {
            transaction.hide(welfareFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentManager = getSupportFragmentManager();
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_HOME);
            balanceFragment = (BalanceFragment) fragmentManager.findFragmentByTag(TAG_CATEGROY);
            chargeFragment = (ChargeFragment) fragmentManager.findFragmentByTag(TAG_COMMUNITY);
            welfareFragment = (WelfareFragment) fragmentManager.findFragmentByTag(TAG_CART);
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag(TAG_MINE);
        }
        mPosition = savedInstanceState.getInt("mPosition");
        selectFragment(mPosition);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mPosition", mPosition);
    }


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                tsg("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ll_home, R.id.ll_category, R.id.ll_community, R.id.ll_cart, R.id.ll_mine
            , R.id.tv_reset, R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
//                mDrawerLayout.openDrawer(drawer_content);
                selectFragment(HOME);
                break;
            case R.id.ll_category:
//                mDrawerLayout.closeDrawers();
                selectFragment(CATEGORY);
                break;
            case R.id.ll_community:
                selectFragment(COMMUNITY);
//
                break;
            case R.id.ll_cart:
                selectFragment(CART);
                break;
            case R.id.ll_mine:
//                WebViewActivity.launch(mActivity,"年度额度", Constant.H5_QUOTA);
                selectFragment(MINE);
                break;
            case R.id.tv_reset:
                reset(feeAdapter, chargeAdapter, voltageAdapter, distanceAdapter);
                break;
            case R.id.tv_finish:
                mDrawerLayout.closeDrawers();
                setFilter();
                break;
        }
    }


    private static final int REQUEST_CODE_SCAN = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    Gson gson = new Gson();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("code",content);
                    AgreementWebViewActivity.launch(mActivity, com.xianlv.business.global.Constant.charge_url+gson.toJson(map));
                } catch (Exception e) {
                    tsg("不是系统的二维码");
                }
            }
        }
        if (requestCode == 1002 && resultCode == RESULT_OK && data != null){
            homeFragment.setAddress(data.getStringExtra("city"));
        }
        if (requestCode == 1003 && resultCode == RESULT_OK && data != null){
            if (balanceFragment != null){
                balanceFragment.onload();
            }
        }
    }

    public void openDrawerView() {
        mDrawerLayout.openDrawer(drawer_content);
    }

    @BindView(R.id.gv_fee)
    GridView gv_fee;
    @BindView(R.id.gv_charge)
    GridView gv_charge;
    @BindView(R.id.gv_voltage)
    GridView gv_voltage;
    @BindView(R.id.gv_distance)
    GridView gv_distance;
    @BindView(R.id.seekbar_voice)
    SeekBar seekbar_voice;
    @BindView(R.id.tv_ele_fee)
    TextView tv_ele_fee;

    private FilterAdapter feeAdapter;
    private FilterAdapter chargeAdapter;
    private FilterAdapter voltageAdapter;
    private FilterAdapter distanceAdapter;

    private void setDrawerView() {

        feeAdapter = new FilterAdapter(this, getFilter(new String[]{"不限","限时免费", "限时免费", "停车收费"}));
        gv_fee.setAdapter(feeAdapter);

        chargeAdapter = new FilterAdapter(this, getFilter(new String[]{"不限","快充", "慢充"}));
        gv_charge.setAdapter(chargeAdapter);

        voltageAdapter = new FilterAdapter(this, getFilter(new String[]{"不限","高压", "低压", "高压兼低压"}));
        gv_voltage.setAdapter(voltageAdapter);

        distanceAdapter = new FilterAdapter(this, getFilter(new String[]{"不限","3公里", "5公里", "10公里", "20公里", "30公里", "50公里"}));
        gv_distance.setAdapter(distanceAdapter);

        addListener();



    }

    private void reset(FilterAdapter... filterAdapters) {
        for (FilterAdapter f :
                filterAdapters) {
            f.reset();
        }
        maxPrice = "";
        seekbar_voice.setProgress(0);


    }

    private String maxPrice = "";
    private void setFilter(){
        String parkingFeeType = "";
        int selectPosition = feeAdapter.getSelectPosition();
        if (selectPosition != 0){
            parkingFeeType = ""+(selectPosition - 1);
        }
        String chargeType = "";
        int p = chargeAdapter.getSelectPosition();
        if (p != 0){
            chargeType = ""+(p - 1);
        }
        String voltageType = "";
        int p1 = voltageAdapter.getSelectPosition();
        if (p1 != 0 && p1 != 3){
            voltageType = ""+(p);
        }else if (p1 ==3){
            voltageType = "0";
        }
        String distance = "";
        int p2 = distanceAdapter.getSelectPosition();
        if (p2 == 1){
            distance = "3";
        }else if (p2 == 2){
            distance = "5";
        }else if (p2 == 3){
            distance = "10";
        }else if (p2 == 4){
            distance = "20";
        }else if (p2 == 5){
            distance = "30";
        }else if (p2 == 6){
            distance = "50";
        }

        homeFragment.setFilter(parkingFeeType,chargeType,voltageType,distance,maxPrice);

    }


    private List<FilterBean> getFilter(String[] s) {
        List<FilterBean> list = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            list.add(new FilterBean(i == 0, s[i]));
        }
        return list;
    }

    private void addListener() {
        seekbar_voice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxPrice = String.format("%.2f", progress / 100.0 * 3.75);
                tv_ele_fee.setText("0~" + (String.format("%.2f", progress / 100.0 * 3.75)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar_voice.setOnTouchListener((v, event) -> {
            mDrawerLayout.requestDisallowInterceptTouchEvent(true);
            return false;
        });
    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void startLocation() throws Exception {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                if (homeFragment != null){
                    homeFragment.setLatitude(amapLocation.getLatitude());
                    homeFragment.setLongitude(amapLocation.getLongitude());
                    //必须在经纬度之后
                    homeFragment.setAddress(amapLocation.getCity());
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
