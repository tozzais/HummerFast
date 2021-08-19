package com.xianlv.business.util.pop;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

import com.xianlv.business.R;


public class ExpandAndCollapseAnimPopupWindow extends PopupWindow {
    private Object tag;
    public ExpandAndCollapseAnimPopupWindow(Context context) {
        super(context);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow() {
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(View contentView) {
        super(contentView);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(int width, int height) {
        super(width, height);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        setAnimationStyle(R.style.pop_add);
    }

    public ExpandAndCollapseAnimPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        setAnimationStyle(R.style.pop_add);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24 && isHandlerHeight) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.top;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    public void setHandlerHeight(boolean flag){
        isHandlerHeight=flag;
    }

    boolean isHandlerHeight=true;
}