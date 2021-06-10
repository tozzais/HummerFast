package com.xianlv.business.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.request.RequestEditMessage;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.fragment.MessageFragment;

public class MessageActivity extends BaseActivity {



    public static void launch(Activity from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, MessageActivity.class);
        from.startActivityForResult(intent,1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("新消息提醒");
        setLineVisibility();

    }

    MessageFragment fragment;
    @Override
    public void loadData() {
        fragment = new MessageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    protected void onDestroy() {
        if (fragment != null){
            Intent intent = new Intent();
            intent.putExtra("ids",fragment.getIds());
            setResult(RESULT_OK,intent);
        }
        super.onDestroy();
    }

    @Override
    public void back() {
        if (fragment != null && TextUtils.isEmpty(fragment.getIds())){
            RequestEditMessage bean = new RequestEditMessage();
            bean.jiguangIds = fragment.getIds();
            new RxHttp<BaseResult>().send(ApiManager.getService().messageEdit(bean),
                    new Response<BaseResult>(mActivity,Response.BOTH) {
                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            finish();
                        }
                    });
        }else {
            finish();
        }


    }
}