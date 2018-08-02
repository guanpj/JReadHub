package com.jeez.guanpj.jreadhub.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CircleImageDrawable extends Drawable {

    private Paint mPaint;
    private int mColor;
    private int mRadius;
    private int mWidth;
    private Bitmap mBitmap;
    private boolean mHasInnerRing;

    public CircleImageDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
        mRadius = mWidth / 2;
    }

    public CircleImageDrawable() {
    }

    public CircleImageDrawable(int color, int radius) {
        this(color, radius, false);
    }

    public CircleImageDrawable(int color, int radius, boolean hasInnerRing) {
        mColor = color;
        mRadius = radius;
        mWidth = 2 * mRadius;
        mHasInnerRing = hasInnerRing;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        if (mHasInnerRing) {
            int whiteRingRadius = mRadius - 5;
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
            canvas.drawCircle(mRadius, mRadius, whiteRingRadius, mPaint);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mWidth;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public boolean getHasInnerRing() {
        return mHasInnerRing;
    }

    public void setRadius(boolean hasInnerRing) {
        this.mHasInnerRing = hasInnerRing;
    }
}
