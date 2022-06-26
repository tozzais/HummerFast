package com.xianlv.business.weight;

/**
 * author : xumingming
 * data : 2022/6/26
 * description ：
 * email : 835683840@qq.com
 */


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.xianlv.business.bean.home.HomeNoticeItem;
import com.xianlv.business.ui.AgreementWebViewActivity;

import java.util.List;

/**
 *
 * 公告新闻切换
 *
 * Wetchat : ljphhj
 * Github : https://github.com/xiaoyaomeng
 * Autor : lijiangping
 */
public class CustomTextSwitcher extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;
    private List<HomeNoticeItem> mData;
    private final long DEFAULT_TIME_SWITCH_INTERVAL = 1000;//1s
    private long mTimeInterval = DEFAULT_TIME_SWITCH_INTERVAL;
    private int mCurrentIndex = 0;

    public CustomTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setFactory(this);
    }

    public CustomTextSwitcher setInAnimation(int animationResId){
        Animation animation = AnimationUtils.loadAnimation(this.mContext, animationResId);
        setInAnimation(animation);
        return this;
    }

    public CustomTextSwitcher setOutAnimation(int animationResId){
        Animation animation = AnimationUtils.loadAnimation(this.mContext, animationResId);
        setOutAnimation(animation);
        return this;
    }

    /**
     * 通知/公告数据绑定
     * @return
     */
    public CustomTextSwitcher bindData(List<HomeNoticeItem> announcementList){
        this.mData = announcementList;
        return this;
    }

    public void startSwitch(long timeInterval){
        this.mTimeInterval = timeInterval;
        if (mData != null && mData.size() != 0) {
            mSwitchHandler.sendEmptyMessage(0);
        }else{
            throw new RuntimeException("data is empty");
        }
    }

    /**
     * 工厂类中创建TextView供给TextSwitcher使用
     * @return
     */
    @Override
    public View makeView() {
        TextView view = new TextView(this.mContext);
        return view;
    }

    private Handler mSwitchHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            int index = mCurrentIndex % mData.size();
            mCurrentIndex++;
            setText(mData.get(index).getTitle());
            sendEmptyMessageDelayed(0, mTimeInterval);
        }
    };

    public void setClick() {
        AgreementWebViewActivity.launch(getContext(),mData.get(mCurrentIndex % mData.size()).getTextUrl());
    }
}


