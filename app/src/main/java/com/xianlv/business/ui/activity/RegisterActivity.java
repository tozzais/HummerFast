package com.xianlv.business.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.bean.ShopResult;
import com.xianlv.business.bean.VersionBean;
import com.xianlv.business.bean.request.RequestCode;
import com.xianlv.business.bean.request.RequestLogin;
import com.xianlv.business.bean.request.RequestShopInfo;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.toast.OnDialogClickListener;
import com.xianlv.business.toast.PrivacyUtil;
import com.xianlv.business.ui.AgreementWebViewActivity;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.display.HProgressDialogUtils;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ????????? ?????????15729271332
 */
public class RegisterActivity extends CheckPermissionActivity {



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

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, RegisterActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(0);
//        toolbar.setNavigationIcon(R.mipmap.back_white);
//        toolbar.setNavigationOnClickListener(view -> back());
    }

    @Override
    public void loadData() {
        boolean firstUse = GlobalParam.getFirstUse();
        if (!firstUse) {
            PrivacyUtil.showTwo(mActivity, new OnDialogClickListener() {
                @Override
                public void onSure() {
                    GlobalParam.setFirstUse(true);
                    if (GlobalParam.getUserLogin()){
                        MainActivity.launch(mActivity);
                        finish();
                    }
                }
                @Override
                public void onCancel() {
                    if (!isFinishing())
                        finish();
                }
            });
        }else {
            if (GlobalParam.getUserLogin()){
            MainActivity.launch(mActivity);
            finish();
            }
        }


        String str = "????????????????????????????????????????????????????????????";
        SpannableString string = new SpannableString(str);
        string.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.baseColor));       //??????????????????
                ds.setUnderlineText(false);      //???????????????
            }
            @Override
            public void onClick(View view) {
                AgreementWebViewActivity.launch(mActivity,
                        "http://www.mofan.store/mf/profile/biz/html/xianlvyhxy.html","????????????");
            }
        },str.indexOf("??????????????????"), str.indexOf("??????????????????")+"??????????????????".length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        string.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.baseColor));       //??????????????????
                ds.setUnderlineText(false);      //???????????????
            }
            @Override
            public void onClick(View view) {
                AgreementWebViewActivity.launch(mActivity,
                        "http://www.mofan.store/mf/profile/biz/html/xianlvxy.html","????????????");
            }
        },str.indexOf("??????????????????"), str.indexOf("??????????????????")+"??????????????????".length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tvAgreement.setText(string);
        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());//????????????????????????

        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            getVersion(pi.versionName);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

    }

    private void getVersion(String version){
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        hashMap.put("versionNo", version + "");
        new RxHttp<BaseResult<VersionBean>>().send(ApiManager.getService().getVersion(hashMap),
                new Response<BaseResult<VersionBean>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<VersionBean> result) {
                        showDialog(result.data);
                    }

                });
    }
    private void  showDialog(VersionBean versionBean){
        if (isFinishing()){
            return;
        }
        if (!"1".equals(versionBean.news)){
            //???????????????
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("??????????????????");
        builder.setMessage(versionBean.intro);
        builder.setPositiveButton("????????????", (dialogInterface, i) -> {
//            downFile(versionBean);
            if (!TextUtils.isEmpty(versionBean.url)){
                downFile(versionBean.url);
            }else {
                tsg("??????????????????");
            }

        });
        if (!"1".equals(versionBean.modify)){
            //??????????????????
            builder.setNegativeButton("????????????", null);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show(); //??????AlertDialog?????????

    }
    private void downFile(String versionBean){
        XUpdate.newBuild(mActivity)
                .apkCacheDir(PathUtils.getExtDownloadsPath())
                .build()
                .download(versionBean, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(mActivity, "????????????", false);
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
            tsg("??????????????????");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("???????????????????????????");
            return;
        }
        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            tsg("??????????????????");
            return;
        }
        String pass = et_pass.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            tsg("?????????????????????");
            return;
        }
        if (!ck_agreement){
            tsg("?????????????????????????????????????????????????????????");
            return;
        }

//        RequestLogin bean = new RequestLogin();
//        bean.phone = phone;
//        bean.code = code;
        TreeMap<String,String> map = new TreeMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("password",pass);
        map.put("registerType","1");

        new RxHttp<BaseResult<LoginBean>>().send(ApiManager.getService().register(map),
                new Response<BaseResult<LoginBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<LoginBean> result) {
                        tsg("????????????");
                        GlobalParam.setLoginBean(result.data);
                        MainActivity.launch(mActivity);
                        finish();
                    }
                });
    }


    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("??????????????????");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("???????????????????????????");
            return;
        }
        TreeMap<String,String> map = new TreeMap<>();
        map.put("phone",phone);
        map.put("type","2");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCode(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("?????????????????????");
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
                tvCode.setText("??????" + time + "???");
                tvCode.setTextColor(getResources().getColor(R.color.grayText));
                mHandler.sendEmptyMessageDelayed(1, 1000);
                tvCode.setEnabled(false);
            } else {
                time = 60;
                tvCode.setTextColor(getResources().getColor(R.color.baseColor));
                tvCode.setText("???????????????");
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
        // ???????????????/????????????
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    getHotelInfo(content.split("\\*")[3]);
                }catch (Exception e){
                    tsg("????????????????????????");
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
//        config.setShake(true);//????????????  ?????????true
//        config.setDecodeBarCode(true);//????????????????????? ?????????true
//        config.setReactColor(R.color.baseColor);//????????????????????????????????? ???????????????
//        config.setFrameLineColor(R.color.white);//??????????????????????????? ????????????
//        config.setScanLineColor(R.color.white);//???????????????????????? ????????????
//        config.setFullScreenScan(false);//??????????????????  ?????????true  ??????false??????????????????????????????
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