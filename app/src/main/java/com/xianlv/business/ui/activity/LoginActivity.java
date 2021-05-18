package com.xianlv.business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.xianlv.business.MainActivity;
import com.xianlv.business.R;
import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.bean.request.RequestCode;
import com.xianlv.business.bean.request.RequestLogin;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(1);
        tvTitle.setText("登录");
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());
    }

    @Override
    public void loadData() {

    }

    private static final int REQUEST_CODE_SCAN = 1001;
    @OnClick({R.id.tv_code, R.id.tv_register, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */
                ZxingConfig config = new ZxingConfig();
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
                config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
                config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
//                AuthActivity.launch(mActivity, 0);
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_code:
                getCode();
                break;

        }
    }

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
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入验证码");
            return;
        }
        RequestLogin bean = new RequestLogin();
        bean.phone = phone;
        bean.code = code;
        new RxHttp<BaseResult<LoginBean>>().send(ApiManager.getService().getLogin(bean),
                new Response<BaseResult<LoginBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<LoginBean> result) {
                        tsg("登录成功");
                        GlobalParam.setLoginBean(result.data);
                        MainActivity.launch(mActivity);
                        finish();
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
        RequestCode requestCode = new RequestCode();
        requestCode.phone = phone;
        new RxHttp<BaseResult>().send(ApiManager.getService().getCode(requestCode),
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
                tvCode.setTextColor(getResources().getColor(R.color.red));
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
//                result.setText("扫描结果为：" + content);
                Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
            }
        }
    }


}