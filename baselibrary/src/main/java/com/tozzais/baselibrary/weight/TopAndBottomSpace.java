package com.tozzais.baselibrary.weight;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 垂直方向的间隔
 */
public class TopAndBottomSpace extends RecyclerView.ItemDecoration {
    private int space ;

    public TopAndBottomSpace(int space) {
        this.space = space;
    }
    public TopAndBottomSpace() {
        this.space = 8;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        int pos = parent.getChildLayoutPosition(view);
        int count = state.getItemCount();
//        LogUtil.e(count+""+pos);
        if (pos == count-1){
            outRect.bottom = space;
        }
        if (pos == 0){
            outRect.top = space;
        }

    }
}

