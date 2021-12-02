package com.xianlv.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.model.Information;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.bean.MainNumberBean;
import com.xianlv.business.bean.MineInfo;
import com.xianlv.business.bean.VersionBean;
import com.xianlv.business.bean.eventbus.RefreshAccount;
import com.xianlv.business.bean.eventbus.RefreshMain;
import com.xianlv.business.bean.request.BaseRequest;
import com.xianlv.business.bean.switchroom.EmployeePermissions;
import com.xianlv.business.bean.weather.WeatherResult;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.goodsmanage.GoodsManageActivity;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.order.activity.ReserveOrderActivity;
import com.xianlv.business.order.activity.StoredValueCardActivity;
import com.xianlv.business.order.activity.ValidityCardActivity;
import com.xianlv.business.ui.activity.BreakfastCouponApplyActivity;
import com.xianlv.business.ui.activity.CallMorningActivity;
import com.xianlv.business.ui.activity.CashPledgeManageActivity;
import com.xianlv.business.ui.activity.CheckInApplyActivity;
import com.xianlv.business.ui.activity.CheckOutApplyActivity;
import com.xianlv.business.ui.activity.CleanApplyActivity;
import com.xianlv.business.ui.activity.CodeActivity;
import com.xianlv.business.ui.activity.CollectionRecordActivity;
import com.xianlv.business.ui.activity.CouponCodeAuthActivity;
import com.xianlv.business.ui.activity.DeliveryReminderActivity;
import com.xianlv.business.ui.activity.DepositInformActivity;
import com.xianlv.business.ui.activity.GiveAwayReminderActivity;
import com.xianlv.business.ui.activity.GoodsOrderActivity;
import com.xianlv.business.ui.activity.MallCouponWriteOffRecordActivity;
import com.xianlv.business.ui.activity.MessageActivity;
import com.xianlv.business.ui.activity.OperationTrainActivity;
import com.xianlv.business.ui.activity.OrderActivity;
import com.xianlv.business.ui.activity.ParkCouponApplyActivity;
import com.xianlv.business.ui.activity.ReceivePayActivity;
import com.xianlv.business.ui.activity.SalesRankActivity;
import com.xianlv.business.ui.activity.StoredValueCardWriteOffActivity;
import com.xianlv.business.ui.activity.VideoListActivity;
import com.xianlv.business.ui.activity.VisitorRecordActivity;
import com.xianlv.business.ui.roommanage.SwitchRoomManageActivity;
import com.xianlv.business.util.BottomDialogUtil;
import com.xianlv.business.util.CenterDialogUtil;
import com.xianlv.business.weight.MarqueeTextView;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.display.HProgressDialogUtils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 接口地址：https://www.apizza.net/project/d8b19ba9902b438ff73489c7110e1b83/browse
 * 这是2.0.0版本
 * 1天 新增消息 和订单处理
 * 2天 打印机和线下订单
 * 2天 所有的订单处理修改 和商品管理页面
 * 2天 开关房需求

 1和2：备注一下：没有获取安装应用列表，ACCESS_WIFI_STATE权限是用来定位获取天气的。相机权限是识别二维码用的
 3：在隐私条款里增加SDK说明。极光用来推送的，高德地图用来定位获取天气的。
  bugly用来统计线上崩溃的，com.tencent.smtt用来加载h5的
  com.bytedance.sdk.openadsdk，com.sensorsdata.analytics没有用到
 4：参考文档改一下（应用必须以清楚明确的方式呈现隐私政策并获取用户同意。）我也不知道啥意思

 5：小程序二维码正式环境应该是正常的吧。验证一下在提交包
 6：提交应用类型的时候 选择【商务】-【效率M
 *
 * 备注一下吧。没有获取安装应用列表
 * ACCESS_WIFI_STATE权限是用来定位的。
 * 
 *
 *
 */
public class MainActivity extends CheckPermissionActivity {

    /**
     * 修复bug
     */

    public static String[] needPermissions = {
            Manifest.permission.CAMERA
    };

    public static String[] needPermissions1 = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @BindView(R.id.iv_weather)
    ImageView iv_weather;
    @BindView(R.id.tv_weather)
    TextView tv_weather;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_hotel_name1)
    TextView tv_hotel_name1;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_number_check_in)
    TextView tvNumberCheckIn;
    @BindView(R.id.tv_number_breakfast)
    TextView tvNumberBreakfast;
    @BindView(R.id.tv_number_park)
    TextView tvNumberPark;
    @BindView(R.id.tv_number_send_goods)
    TextView tvNumberSendGoods;
    @BindView(R.id.tv_number_clean)
    TextView tvNumberClean;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_message)
    MarqueeTextView tvMessage;
    @BindView(R.id.tv_message_more)
    TextView tvMessageMore;
    @BindView(R.id.tv_order1)
    TextView tv_order1;
    @BindView(R.id.tv_order2)
    TextView tv_order2;
    @BindView(R.id.tv_order3)
    TextView tv_order3;
    @BindView(R.id.tv_order4)
    TextView tv_order4;
    @BindView(R.id.ll_applets)
    LinearLayout ll_applets;
    @BindView(R.id.ll_store)
    LinearLayout ll_store;

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, MainActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main;
    }


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = aMapLocation -> {
        getWeather(aMapLocation.getCity().replaceAll("市", ""));
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    public void initView(Bundle savedInstanceState) {

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(true);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        checkPermissions(needPermissions1);

        tvMessageMore.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, ivBg);
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    public void loadData() {
        getData();

        getNumber();


    }



    @OnClick({R.id.iv_switch, R.id.ll_applets, R.id.ll_store, R.id.ll_rank_person, R.id.ll_rank_team,
            R.id.rl_order1, R.id.rl_order2,  R.id.rl_order3,R.id.ll_order4, R.id.rl_write1, R.id.rl_write2,
            R.id.rl_write3, R.id.rl_write4,
            R.id.rl_apply1, R.id.rl_apply2, R.id.rl_apply3, R.id.rl_apply4, R.id.rl_manage1, R.id.rl_manage2,
            R.id.rl_manage4, R.id.rl_manage5, R.id.rl_manage6, R.id.rl_manage7, R.id.rl_manage8,
            R.id.rl_manage9, R.id.rl_manage10, R.id.rl_study1, R.id.rl_study2, R.id.rl_study3, R.id.rl_study4,
            R.id.rl_study5, R.id.rl_order5, R.id.rl_order6, R.id.rl_order8, R.id.rl_goods_manage,
            R.id.rl_laundry_call,R.id.rl_switch_room_manage,R.id.rl_house_price_manage,R.id.iv_kefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_kefu:
                LoginBean loginBean = GlobalParam.getLoginBean();
                if (mineInfo == null || loginBean == null){
                    return;
                }
                Information info = new Information();
                // appkey 必填
                info.setApp_key("d8a1bd63850b46ac965acebacd6ad402");
                info.setUser_nick(mineInfo.shopName);
                info.setUser_name(mineInfo.department+" "+ mineInfo.trueName);
                info.setUser_tels(loginBean.phone);
                ZCSobotApi.openZCChat(mActivity, info);
                break;
            case R.id.rl_switch_room_manage:
                //开关房管理
                SwitchRoomManageActivity.launch(mActivity,0);
                break;
            case R.id.rl_house_price_manage:
                //房价管理
                SwitchRoomManageActivity.launch(mActivity,1);
                break;
            case R.id.rl_order5:
                StoredValueCardActivity.launch(mActivity);
                break;
            case R.id.rl_order6:
                ValidityCardActivity.launch(mActivity);
                break;
            case R.id.rl_order8:
                ReserveOrderActivity.launch(mActivity);
                break;
            case R.id.rl_goods_manage:
                GoodsManageActivity.launch(mActivity);
                break;
            case R.id.rl_laundry_call:
                tsg("该功能暂未开放，请您耐心等待");
                break;
            case R.id.iv_switch:
//                CenterDialogUtil.show(mActivity, "提示", "是否退出当前用户？", s -> {
//                    if ("1".equals(s)) {
//                        GlobalParam.exitLogin();
//                        LoginActivity.launch(mActivity);
//                        finish();
//                    }
//                });
                BottomDialogUtil.showSelectDialog(mActivity);
                break;
            case R.id.ll_applets:
                CodeActivity.launch(mActivity, 1,null);
                break;
            case R.id.ll_store:
                Map<String, String> map = new HashMap<>();
                map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
                new RxHttp<BaseResult<EmployeePermissions>>().send(ApiManager.getService().queryAuthority(map)
                        ,new Response<BaseResult<EmployeePermissions>>(mActivity){
                            @Override
                            public void onSuccess(BaseResult<EmployeePermissions> baseResult) {
                                if (baseResult.data.isCanReceivePayment()){
                                    ReceivePayActivity.launch(mActivity);
                                }else {
                                    tsg("暂无权限");
                                }
                            }
                        });


                break;
            case R.id.ll_rank_person:
                SalesRankActivity.launch(mActivity, SalesRankActivity.PERSON);
                break;
            case R.id.ll_rank_team:
                SalesRankActivity.launch(mActivity, SalesRankActivity.TEAM);
                break;
            case R.id.rl_order1:
                OrderActivity.launch(mActivity);
                break;
            case R.id.rl_order2:
                DeliveryReminderActivity.launch(mActivity);
                break;
            case R.id.rl_order3:
                GoodsOrderActivity.launch(mActivity);
                break;
            case R.id.ll_order4:
                CollectionRecordActivity.launch(mActivity);
                break;
            case R.id.rl_write1:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.COUPON);
                break;
            case R.id.rl_write2:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.CARD);
                break;
            case R.id.rl_write3:
                StoredValueCardWriteOffActivity.launch(mActivity, StoredValueCardWriteOffActivity.GOODS);
                break;
            case R.id.rl_write4:
                MallCouponWriteOffRecordActivity.launch(mActivity);
                break;
            case R.id.rl_apply1:
                CheckInApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply2:
                CheckOutApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply3:
                //早餐券申请
                BreakfastCouponApplyActivity.launch(mActivity);
                break;
            case R.id.rl_apply4:
                //停车券申请
                ParkCouponApplyActivity.launch(mActivity);
                break;
            case R.id.rl_manage1:
                GiveAwayReminderActivity.launch(mActivity);
                break;
            case R.id.rl_manage2:
                CleanApplyActivity.launch(mActivity);
                break;
            case R.id.rl_manage4:
                CashPledgeManageActivity.launch(mActivity);
                break;
            case R.id.rl_manage5:
                DepositInformActivity.launch(mActivity);
                break;
            case R.id.rl_manage6:
                CenterDialogUtil.showVerify(mActivity, "早餐券验证", 0, s -> {
                    if (s.equals("1")) {
                        type = 0;
                        checkPermissions(needPermissions);
                    }
                });
                break;
            case R.id.rl_manage7:
                CenterDialogUtil.showVerify(mActivity, "停车券验证", 1, s -> {
                    if (s.equals("1")) {
                        type = 1;
                        checkPermissions(needPermissions);
                    }
                });
                break;
            case R.id.rl_manage8:
                tsg("该功能暂未开发，请您耐心等待");
                break;
            case R.id.rl_manage9:
                VisitorRecordActivity.launch(mActivity);
                break;
            case R.id.rl_manage10:
                CallMorningActivity.launch(mActivity);
                break;
            case R.id.rl_study1:
//                tsg("该功能暂未开发，请您耐心等待");
                VideoListActivity.launch(mActivity);
                break;
            case R.id.rl_study2:
                OperationTrainActivity.launch(mActivity, OperationTrainActivity.OPERATION_TRAIN);
                break;
            case R.id.rl_study3:
                OperationTrainActivity.launch(mActivity, OperationTrainActivity.DISTRIBUTION_INSTRUCTIONS);
                break;
            case R.id.rl_study4:
                OperationTrainActivity.launch(mActivity, OperationTrainActivity.COMMON_PROBLEM);
                break;
            case R.id.rl_study5:
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setOnRefreshListener(this::loadData);




    }

    private void getVersion(String version){
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        hashMap.put("versionNo", version + "");
        new RxHttp<BaseResult<VersionBean>>().send(ApiManager.getService().getVersion(hashMap),
                new Response<BaseResult<VersionBean>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<VersionBean> result) {
                        swipeLayout.setRefreshing(false);

                        showDialog(result.data);
                    }

                });
    }
    private void  showDialog(VersionBean versionBean){
        if (!"1".equals(versionBean.news)){
            //不是新版本
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("版本更新提示");
        builder.setMessage(versionBean.intro);
        builder.setPositiveButton("立即更新", (dialogInterface, i) -> {
//            downFile(versionBean);
            if (!TextUtils.isEmpty(versionBean.url)){
                downFile(versionBean.url);
            }else {
                tsg("下载地址为空");
            }

        });
        if (!"1".equals(versionBean.modify)){
            //不是强制更新
            builder.setNegativeButton("暂不更新", null);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show(); //构建AlertDialog并显示

    }
    private void downFile(String versionBean){
        XUpdate.newBuild(mActivity)
                .apkCacheDir(PathUtils.getExtDownloadsPath())
                .build()
                .download(versionBean, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(mActivity, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        _XUpdate.startInstallApk(mActivity, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
    }

    private static final int REQUEST_CODE_SCAN = 1001;


    @Override
    public void permissionGranted() {
        if (type == -1) {
            if (null != mLocationClient) {
                mLocationClient.setLocationOption(mLocationOption);
                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
            }
            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                getVersion(pi.versionName);
            } catch (Exception e) {
                swipeLayout.setRefreshing(false);
                LogUtil.e(e.toString());
            }
        } else {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }

    }

    private int type = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    CouponCodeAuthActivity.launch(mActivity, type, content.split("\\*")[3]);
                } catch (Exception e) {
                    tsg("不是系统的二维码");
                }

            }
        }

    }

    private MineInfo mineInfo;
    private void getData() {
        new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().mineInfo(new BaseRequest()),
                new Response<BaseResult<MineInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineInfo> result) {
                        mineInfo = result.data;
                        tvHotelName.setText(mineInfo.shopName);
                        tv_hotel_name1.setText(mineInfo.shopName);
                        tv_address.setText(mineInfo.address);
                        tvName.setText(mineInfo.department + " " + mineInfo.trueName);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });
    }


    private void getNumber() {
        new RxHttp<BaseResult<MainNumberBean>>().send(ApiManager.getService().main_number(new BaseRequest()),
                new Response<BaseResult<MainNumberBean>>(mActivity, Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<MainNumberBean> result) {
                        MainNumberBean numberBean = result.data;
                        tvNumberClean.setVisibility(numberBean.sweep > 0 ? View.VISIBLE : View.GONE);
                        tvNumberClean.setText(getNumber(numberBean.sweep));
                        tvNumberPark.setVisibility(numberBean.vouchers2 > 0 ? View.VISIBLE : View.GONE);
                        tvNumberPark.setText(getNumber(numberBean.vouchers2));
                        tvNumberBreakfast.setVisibility(numberBean.vouchers1 > 0 ? View.VISIBLE : View.GONE);
                        tvNumberBreakfast.setText(getNumber(numberBean.vouchers1));
                        tvNumberSendGoods.setVisibility(numberBean.goods > 0 ? View.VISIBLE : View.GONE);
                        tvNumberSendGoods.setText(getNumber(numberBean.goods));
                        tvNumberCheckIn.setVisibility(numberBean.roomWifiConfiguration > 0 ? View.VISIBLE : View.GONE);
                        tvNumberCheckIn.setText(getNumber(numberBean.roomWifiConfiguration));
                        tvNumber.setVisibility(numberBean.jiguangCount > 0 ? View.VISIBLE : View.GONE);
                        tvNumber.setText(getNumber(numberBean.jiguangCount));
                        if (numberBean.jiguangMsg != null && !TextUtils.isEmpty(numberBean.jiguangMsg.content)){
                            tvMessage.setText(numberBean.jiguangMsg.content);
                        }else {
                            tvMessage.setText("暂无新消息");
                        }
                        tv_order1.setVisibility(numberBean.roomOrderNoConfirmCount > 0 ? View.VISIBLE : View.GONE);
                        tv_order1.setText(getNumber(numberBean.roomOrderNoConfirmCount));
                        tv_order2.setVisibility(numberBean.restOrderNoConfirmCount > 0 ? View.VISIBLE : View.GONE);
                        tv_order2.setText(getNumber(numberBean.restOrderNoConfirmCount));
                        tv_order3.setVisibility(numberBean.orderNoConfirmCount > 0 ? View.VISIBLE : View.GONE);
                        tv_order3.setText(getNumber(numberBean.orderNoConfirmCount));

                    }

                });
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

    private void getWeather(String city) {
        new RxHttp<WeatherResult>().send(ApiManager.getService1().getWeather(TextUtils.isEmpty(city) ? "上海" : city),
                new Response<WeatherResult>(mActivity, Response.BOTH) {
                    @Override
                    public void onNext(WeatherResult result) {
                        if ("1".equals(result.success)) {
                            WeatherResult.ResultDTO resultDTO = result.result;
                            ImageUtil.loadFullAddress(mActivity, iv_weather, resultDTO.weather_icon);
                            tv_weather.setText(resultDTO.temp_low + "℃~" + resultDTO.temp_curr + "℃" + "\n" + resultDTO.weather_curr);
                        } else {
                            getWeather("上海");
                        }
                    }

                });
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof RefreshMain) {
            getNumber();
        }if (o instanceof RefreshAccount) {
            RefreshAccount refreshAccount = (RefreshAccount)o;
            Map<String,String> map = new HashMap<>();
            map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0,6));
            map.put("tenantId",refreshAccount.tenantId);
            new RxHttp<BaseResult<LoginBean>>().send(ApiManager.getService().changeHouse(map),
                    new Response<BaseResult<LoginBean>>(mActivity, Response.BOTH) {
                        @Override
                        public void onSuccess(BaseResult<LoginBean> result) {
                            tsg("切换成功");
                            GlobalParam.setLoginBean(result.data);
                            getData();
                            getNumber();
                        }

                    });

        }
    }

    @OnClick(R.id.tv_message_more)
    public void onClick() {
        MessageActivity.launch(mActivity);


    }




    public String getNumber(int num){
        if (num>99){
            return "99+";
        }
        return num+"";
    }



}