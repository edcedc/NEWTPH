package com.spit.tph.nike_mvp.recyclerview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import com.spit.tph.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * User: Nike
 * 2024/1/15 12:04
 */
public class AutoSwipeRefreshView extends SwipeRefreshLayout {

    public AutoSwipeRefreshView (Context context) {
        super(context);
        setColorSchemeColors(       // 设置刷新时显示的颜色
                ContextCompat.getColor(getContext(), R.color.colorPrimary)
        );
    }

    public AutoSwipeRefreshView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(       // 设置刷新时显示的颜色
                ContextCompat.getColor(getContext(), R.color.colorPrimary)
        );
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/* 优点:封装后代码优雅,不需要手动请求数据 */