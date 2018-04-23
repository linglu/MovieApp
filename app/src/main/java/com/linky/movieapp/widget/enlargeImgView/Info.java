package com.linky.movieapp.widget.enlargeImgView;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by liuheng on 2015/8/19.
 */
public class Info {

    // 内部图片在整个手机界面的位置
    RectF mRect = new RectF();

    // 控件在窗口的位置
    RectF mImgRect = new RectF();

    RectF mWidgetRect = new RectF();

    RectF mBaseRect = new RectF();

    PointF mScreenCenter = new PointF();

    float mScale;

    float mDegrees;

    ImageView.ScaleType mScaleType;

    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("mRect : " + "left : ").append(mRect.left).append(", top :").append(mRect.top).append(", right : ").append(mRect.right).append(", bottom : ").append(mRect.bottom).append("\n");
        sb.append("mImgRect : " + "left : ").append(mImgRect.left).append(", top :").append(mImgRect.top).append(", right : ").append(mImgRect.right).append(", bottom : ").append(mImgRect.bottom).append("\n");
        sb.append("mWidgetRect : " + "left : ").append(mWidgetRect.left).append(", top :").append(mWidgetRect.top).append(", right : ").append(mWidgetRect.right).append(", bottom : ").append(mWidgetRect.bottom).append("\n");
        sb.append("mBaseRect : " + "left : ").append(mBaseRect.left).append(", top :").append(mBaseRect.top).append(", right : ").append(mBaseRect.right).append(", bottom : ").append(mBaseRect.bottom).append("\n");
        return sb.toString();
    }
}
