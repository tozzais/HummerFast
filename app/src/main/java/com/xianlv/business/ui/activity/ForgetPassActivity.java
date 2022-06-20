package com.xianlv.business.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.CommonUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.ShopResult;
import com.xianlv.business.bean.request.RequestShopInfo;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.yzq.zxinglibrary.common.Constant;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 尹晓冬 账号：15729271332
 */
public class ForgetPassActivity extends CheckPermissionActivity {



    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.ll_code)
    LinearLayout ll_code;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.checkbox)
    ImageView checkbox;

    public static void launch(Activity from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, ForgetPassActivity.class);
        from.startActivityForResult(intent,102);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(0);
//        toolbar.setNavigationIcon(R.mipmap.back_white);
//        toolbar.setNavigationOnClickListener(view -> back());
    }

    @Override
    public void loadData() {


    }


    private static final int REQUEST_CODE_SCAN = 1001;
    @OnClick({R.id.tv_code, R.id.tv_register, R.id.tv_login, R.id.checkbox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                finish();
//                BottomDialogUtil.showSelectDialog(mActivity);
                break;
            case R.id.tv_login:
                login();
//
                break;
            case R.id.tv_code:
//                MessageActivity.launch(mActivity);
                getCode();
                break;
            case R.id.checkbox:
                if (ck_agreement){
                    checkbox.setBackgroundResource(R.mipmap.icon_agreement_unselect);
                }else {
                    checkbox.setBackgroundResource(R.mipmap.icon_agreement_select);
                }
                ck_agreement = !ck_agreement;
                break;

        }
    }
    private boolean ck_agreement = false;

    private void login() {

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            tsg("请输入验证码");
            return;
        }
        String pass = et_pass.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            tsg("请输入您的密码");
            return;
        }


//        RequestLogin bean = new RequestLogin();
//        bean.phone = phone;
//        bean.code = code;
        TreeMap<String,String> map = new TreeMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("password",pass);
        new RxHttp<BaseResult<Integer>>().send(ApiManager.getService().forgetPass(map),
                new Response<BaseResult<Integer>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<Integer> result) {
                        if (result.data == 1){
                            tsg("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("phone",phone);
                            intent.putExtra("password",pass);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            tsg(result.msg);
                        }

                    }
                });
    }


    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        TreeMap<String,String> map = new TreeMap<>();
        map.put("phone",phone);
        map.put("type","3");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCode(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("验证码发送成功");
                        mHandler.sendEmptyMessage(1);
                    }
                });
    }

    private int time = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (time > 0) {
                time--;
                tvCode.setText("还剩" + time + "秒");
                tvCode.setTextColor(getResources().getColor(R.color.grayText));
                mHandler.sendEmptyMessageDelayed(1, 1000);
                tvCode.setEnabled(false);
            } else {
                time = 60;
                tvCode.setTextColor(getResources().getColor(R.color.baseColor));
                tvCode.setText("获取验证码");
                tvCode.setEnabled(true);
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(1);
        }
        mHandler = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    getHotelInfo(content.split("\\*")[3]);
                }catch (Exception e){
                    tsg("不是系统的二维码");
                }
            }
        }
    }

    private void getHotelInfo(String shopId) {
        RequestShopInfo bean = new RequestShopInfo();
        bean.shopId = shopId;
        new RxHttp<BaseResult<ShopResult>>().send(ApiManager.getService().shopInfo(bean),
                new Response<BaseResult<ShopResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ShopResult> result) {
                        ShopResult data = result.data;
                        AuthActivity.launch(mActivity,data);
                    }
                });
    }

    private void setData(){

    }


    @Override
    public void permissionGranted() {
//        Intent intent = new Intent(mActivity, CaptureActivity.class);
//        ZxingConfig config = new ZxingConfig();
//        config.setShake(true);//是否震动  默认为true
//        config.setDecodeBarCode(true);//是否扫描条形码 默认为true
//        config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
//        config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
//        config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
//        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
//        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
//        startActivityForResult(intent, REQUEST_CODE_SCAN);


    }


    private int way = 1;
    private int login_pass = 1;
    private int login_code = 2;
    private void changeLoginWay(){
        if (way == login_pass){
            way = login_code;
            ll_code.setVisibility(View.GONE);
            et_pass.setVisibility(View.VISIBLE);
        }else if (way == login_code){
            way = login_pass;
            ll_code.setVisibility(View.VISIBLE);
            et_pass.setVisibility(View.GONE);
        }


    }
}