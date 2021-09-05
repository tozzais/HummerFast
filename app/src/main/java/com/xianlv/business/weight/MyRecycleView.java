package com.xianlv.business.weight;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(@NonNull Context context) {
        super(context);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        ViewParent parent = getParent();
//        while ( !(parent instanceof ViewPager)){
//            parent = parent.getParent();
//        }
//        parent.requestDisallowInterceptTouchEvent(false);
//        return super.onTouchEvent(e);
//
//    }
}
