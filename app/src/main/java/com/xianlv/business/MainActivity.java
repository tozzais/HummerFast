package com.xianlv.business;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.xianlv.business.ui.BalanceFragment;
import com.xianlv.business.ui.HomeFragment;
import com.xianlv.business.ui.MineFragment;
import com.xianlv.business.ui.WelfareFragment;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends CheckPermissionActivity {


    /**
     * 从各个登录地方进去的方法
     * 登录界面  绑定界面 快捷登录
     *
     * @param context
     */
    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();

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
    private HomeFragment mineCollectShopFragment;
    private WelfareFragment welfareFragment;
    private MineFragment mineFragment;



    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_content)
    LinearLayout  drawer_content;



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
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void permissionGranted() {
//        CityDataBaseFileUtil.savaData(this);
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
                    balanceFragment = new BalanceFragment();
                    transaction.add(R.id.fl_container, balanceFragment, TAG_CATEGROY);
                } else {
                    transaction.show(balanceFragment);
                }
                break;
            case COMMUNITY:
                if (mineCollectShopFragment == null) {
                    mineCollectShopFragment = new HomeFragment();
                    transaction.add(R.id.fl_container, mineCollectShopFragment, TAG_COMMUNITY);
                } else {
                    transaction.show(mineCollectShopFragment);
                }
                break;

            case CART:
                if (welfareFragment == null) {
                    welfareFragment = new WelfareFragment();
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
        if (mineCollectShopFragment != null) {
            transaction.hide(mineCollectShopFragment);
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
            mineCollectShopFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_COMMUNITY);
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

    @OnClick({R.id.ll_home, R.id.ll_category, R.id.ll_community, R.id.ll_cart, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                mDrawerLayout.openDrawer(drawer_content);
//                selectFragment(HOME);
                break;
            case R.id.ll_category:
                mDrawerLayout.closeDrawers();
//                selectFragment(CATEGORY);
                break;
            case R.id.ll_community:
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
                config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
                config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.ll_cart:
                selectFragment(CART);
                break;
            case R.id.ll_mine:
//                WebViewActivity.launch(mActivity,"年度额度", Constant.H5_QUOTA);
                selectFragment(MINE);
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
                    tsg(content.split("\\*")[3]);
                }catch (Exception e){
                    tsg("不是系统的二维码");
                }
            }
        }
    }

    @BindView(R.id.gv_area)
    GridView gv_area;
    private String[] s = {"停车免费","限时免费","停车收费"};
    private void setDrawerView(){
        gv_area.setAdapter(new ArrayAdapter<String>(this,
                R.layout.item_filter,
                R.id.tv_text, s));
        gv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.tv_text);
            }
        });
    }


}
