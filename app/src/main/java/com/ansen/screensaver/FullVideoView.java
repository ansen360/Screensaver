package com.ansen.screensaver;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Ansen on 2018/6/11 17:44.
 *
 * @E-mail: ansen360@126.com
 * @Blog: "http://blog.csdn.net/qq_25804863"
 * @Github: "https://github.com/ansen360"
 * @PROJECT_NAME: Screensaver
 * @PACKAGE_NAME: com.ansen.screensaver
 * @Description: TODO
 */
public class FullVideoView extends VideoView {

    public FullVideoView(Context context) {
        super(context);
    }

    public FullVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = getDefaultSize(0, widthMeasureSpec);
//        int height = getDefaultSize(0, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(width, heightMeasureSpec);
    }
}
