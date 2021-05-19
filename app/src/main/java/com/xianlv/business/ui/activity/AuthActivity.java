package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.CommonUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.ShopInfo;
import com.xianlv.business.bean.ShopResult;
import com.xianlv.business.bean.request.RequestCode;
import com.xianlv.business.bean.request.RequestRegister;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.util.DepartmentDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthActivity extends BaseActivity {


    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_part)
    TextView etPart;

    public static void launch(Context from, ShopResult shopResult) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, AuthActivity.class);
        intent.putExtra("shopResult", shopResult);
        from.startActivity(intent);
    }

    private ShopResult shopResult;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("认证");
        shopResult = getIntent().getParcelableExtra("shopResult");
        ShopInfo appShop = shopResult.appShop;
        tvHotelName.setText(appShop.shopName);
        tvAddress.setText(appShop.address);
        tvPhone.setText(appShop.shopPhone);

    }

    @Override
    public void loadData() {
    }

    @OnClick({R.id.tv_code, R.id.btn_bottom, R.id.et_part})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getCode();
                break;
            case R.id.btn_bottom:
                register();
                break;
            case R.id.et_part:
                DepartmentDialogUtil.showSelectDialog(mContext, shopResult.apiDepartmentList, bean -> {
                    etPart.setText(bean.department);
                    etPart.setTag(bean.departmentId);
                });
                break;
        }
    }

    private void register() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        String part = etPart.getText().toString();
        if (TextUtils.isEmpty(name)){
            tsg("请输入姓名");
            return;
        }if (TextUtils.isEmpty(phone)){
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }if (TextUtils.isEmpty(code)){
            tsg("请输入验证码");
            return;
        }if (TextUtils.isEmpty(part)){
            tsg("请选择部门");
            return;
        }
        RequestRegister bean = new RequestRegister();
        bean.phone = phone;
        bean.code = code;
        bean.shopId = shopResult.appShop.shopId;
        bean.truename = name;
        bean.departmentId = (int) etPart.getTag()+"";
        new RxHttp<BaseResult>().send(ApiManager.getService().register(bean),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("认证成功");
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


}