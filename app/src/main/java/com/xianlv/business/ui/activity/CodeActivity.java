package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.R;
import com.xianlv.business.bean.CodeBean;
import com.xianlv.business.bean.CodePayResult;
import com.xianlv.business.bean.PayCode;
import com.xianlv.business.bean.request.BaseRequest;
import com.xianlv.business.bean.request.RequestShopId;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CodeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_code_name)
    TextView tvCodeName;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_tip1)
    TextView tvTip1;
    @BindView(R.id.tv_tip2)
    TextView tvTip2;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_money1)
    TextView tv_money1;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private int type;

    public static void launch(Context from, int type, PayCode data) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, CodeActivity.class);
        intent.putExtra("PayCode",data);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }
    private PayCode data;

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBar(1);
        data = (PayCode) getIntent().getSerializableExtra("PayCode");
        type = getIntent().getIntExtra("type", 1);
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(view -> back());
        if (type == 1) {
            tvTitle.setText("小程序二维码");
            tvCodeName.setText("小程序二维码");
            getAppletCode();
            llRoot.setBackgroundColor(getResources().getColor(R.color.yellowText));
//            Drawable drawable = getResources().getDrawable(R.mipmap.icon_receive_pay);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            tvCodeName.setCompoundDrawables(drawable, null, null, null);
            tvCodeName.setTextColor(getResources().getColor(R.color.yellowText));
            tv_money1.setVisibility(View.GONE);
            tv_money.setVisibility(View.GONE);
            tvCode.setText("扫码跳转小程序");
            tvCode.setTextColor(getResources().getColor(R.color.yellowText));
            tvTip1.setText("温馨提示");
            tvTip2.setText("如无法打开小程序，请再次扫描");
        } else if (type == 2) {
            tvTitle.setText("门店收款码");
//            llRoot.setBackgroundColor(getResources().getColor(R.color.yellowText));
            tvCodeName.setText("收款服务");
//            Drawable drawable = getResources().getDrawable(R.mipmap.icon_receive_pay);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            tvCodeName.setCompoundDrawables(drawable, null, null, null);
//            tvCodeName.setTextColor(getResources().getColor(R.color.yellowText));

            tvCode.setText("备注："+data.remark);
            tvCode.setTextColor(getResources().getColor(R.color.grayText));
            tvTip1.setText("安全提示");
            tvTip2.setText("为了你的资金安全，请保管好收款码，防止泄露");
            tv_money.setText("￥"+data.money);
            ImageUtil.loadFullAddress(mActivity,ivCode,data.qrCode);

            test();
        }


    }

    @Override
    public void loadData() {
//        if (type == 1) {
//            getAppletCode();
//        }else {
//            getReceiveCode();
//        }

    }


    @OnClick({R.id.iv_code, R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_code:
                break;
            case R.id.tv_code:
                if (type == 2){
//                    ReceivePayResultActivity.launch(mActivity,2);
                }
                break;
        }
    }

    private void getAppletCode(){
        new RxHttp<BaseResult<CodeBean>>().send(ApiManager.getService()
                        .code_applets(new BaseRequest()),
                new Response<BaseResult<CodeBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CodeBean> result) {
                        ImageUtil.loadFullAddress(mActivity,ivCode,result.data.code);
                    }
                });
    }

    private void getReceiveCode(){
        RequestShopId bean = new RequestShopId();
        bean.shopId = GlobalParam.getLoginBean().shopId;
        new RxHttp<BaseResult<CodeBean>>().send(ApiManager.getService().code_receive(bean),
                new Response<BaseResult<CodeBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CodeBean> result) {
                        ImageUtil.loadFullAddress(mActivity,ivCode,result.data.qrCode);

                    }
                });
    }

    private void test(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("wss://shanghaixianlv.com/webSocket")
                .build();
        LogUtil.e("请求连接");
        WebSocket mWebSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {

            //创建线程池，
            ExecutorService writeExecutor = Executors.newSingleThreadExecutor();
            WebSocket webSocket = null;
            @Override
            public void onOpen(@NotNull WebSocket webSocket, okhttp3.@NotNull Response response) {
                super.onOpen(webSocket, response);
                this.webSocket = webSocket;
                LogUtil.e("连接成功");
                //建立连接成功后，发送消息给服务器端
                writeExecutor.execute(() -> {
                    //socket 发送信息到服务器
                    HashMap<String,String> map = new HashMap<>();
                    map.put("uid",GlobalParam.getUid());
                    String json = new Gson().toJson(map);
                    LogUtil.e("连接成功,发送消息："+json);
                    webSocket.send(json);
                });
            }

            @Override
            public void onMessage(final WebSocket webSocket, String text) {
                LogUtil.e("连接成功 接受消息"+text);
                CodePayResult codePayResult = new Gson().fromJson(text, CodePayResult.class);
                if ("2".equals(codePayResult.category) && "2".equals(codePayResult.payStatus)){
                    ReceivePayResultActivity.launch(mActivity,0,codePayResult.scanId);
                    finish();

                }
                //获取到服务器发送过来的信息，然后通过handler进行UI线程的操作
//                Message message = Message.obtain();
//                message.what = READ_BYSOKET;
//                message.obj = text;
//                mHandler.sendMessage(message);
            }

            //webSocket关闭时，关闭线程池
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                LogUtil.e("关闭成功");
                writeExecutor.shutdown();
            }
        });
    }

}