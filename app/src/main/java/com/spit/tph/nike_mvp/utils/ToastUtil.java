package com.spit.tph.nike_mvp.utils;


import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ABC on 2019/11/1.
 */
public class ToastUtil {
    private static Toast mToast;
    /**
     * 传入文字
     * */
    public static void show(Context context , String text){
        if (mToast == null){
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.BOTTOM , 0 , 30);

// 获取Toast的布局
        View toastView = mToast.getView();
// 创建ShapeDrawable并设置圆角
        GradientDrawable shapeDrawable = new GradientDrawable();
// 设置圆角半径，根据需要进行调整
        shapeDrawable.setCornerRadius(30);
// 设置背景颜色
        shapeDrawable.setColor(Color.BLACK);

// 设置ShapeDrawable作为背景
        toastView.setBackground(shapeDrawable);

// 获取文本视图
        TextView toastText = toastView.findViewById(android.R.id.message);
// 修改文本颜色
        toastText.setTextColor(Color.WHITE);

        mToast.show();
    }
    /**
     * 传入资源文件
     * */
    public static void show(Context context, int resId){
        if (mToast == null){
            mToast = Toast.makeText( context, resId , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(resId);
        }
        mToast.show();
    }
    /**
     * 传入文字,在中间显示
     * */
    public static void showCenter( Context context , String text){

        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER , 0 , 0);
        mToast.show();
    }
    /**
     * 传入文字，带图片
     * */
    public static void showImg( Context context , String text , int resImg){

        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_SHORT);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        //添加图片的操作,这里没有设置图片和文字显示在一行的操作呢...
        LinearLayout view = (LinearLayout) mToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resImg);
        view.addView(imageView);

        mToast.show();
    }
}
