package com.csl.ams.nike_mvp.recyclerview

import android.content.Context

/**
 * User: Nike
 *  2024/1/15 11:13
 */
class DpUtils {

    companion object {
        // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
        fun dip2px(context: Context, dpValue: Float): Int {
            // 获取当前手机的像素密度（1个dp对应几个px）
            val scale: Float = context.getResources().getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt() // 四舍五入取整
        }

        // 根据手机的分辨率从 px(像素) 的单位 转成为 dp
        fun px2dip(context: Context, pxValue: Float): Int {
            // 获取当前手机的像素密度（1个dp对应几个px）
            val scale: Float = context.getResources().getDisplayMetrics().density
            return (pxValue / scale + 0.5f).toInt() // 四舍五入取整
        }
    }

}