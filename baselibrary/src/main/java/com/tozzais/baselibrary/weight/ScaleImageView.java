package com.tozzais.baselibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tozzais.baselibrary.R;
import com.tozzais.baselibrary.util.DpUtil;


public class ScaleImageView extends androidx.appcompat.widget.AppCompatImageView {

    /**
     * 高 宽 比
     */
    private float scale;


    //圆角大小，默认为10
    private int mBorderRadius = 10;

    private Paint mPaint;

    // 3x3 矩阵，主要用于缩小放大
    private Matrix mMatrix;

    //渲染图像，使用图像为绘制图形着色
    private BitmapShader mBitmapShader;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        scale = ta.getFloat(R.styleable.ScaleImageView_scale,0);
        ta.recycle();

        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBorderRadius = DpUtil.dip2px(context,5);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mBorderRadius = DpUtil.dip2px(context,5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        LogUtil.e(width+"=="+ (int) (width*scale));
        setMeasuredDimension(width, (int) (width*scale));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        Bitmap bitmap = drawableToBitamp(getDrawable());
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight()))
        {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
                    getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mBitmapShader);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()), mBorderRadius, mBorderRadius,
                mPaint);
    }


    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
