package com.csl.ams.nike_mvp.utils


import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.spit.tph.R
import com.spit.tph.nike_mvp.utils.ScreenUtils


/**
 * User: Nike
 *  2024/1/17 10:45
 */
class PopupWindowTool(private val context: Context) {

    private var pop: PopupWindow? = null

    var status = 0

    fun showPop() {
        if (pop != null && pop!!.isShowing) {
            pop!!.dismiss()
        }
        pop = PopupWindow(context)
        val popView = LayoutInflater.from(context).inflate(R.layout.p_edit, null, false)
        pop?.setContentView(popView);
        var width = ScreenUtils.getWidth(context);
//        pop?.setWidth(width * 3 / 4);
        pop?.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop?.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        pop?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        pop?.setFocusable(true);
        pop?.setOutsideTouchable(true);
        //点击其他地方消失
        popView.setOnTouchListener { v, event ->
            closePopupWindow()
            false
        }

        val etRemark = popView.findViewById<AppCompatEditText>(R.id.et_remark)
        //居中显示，第一个参数的控件只要是这个popwindow里面的随便一个控件就行
        pop?.showAtLocation(etRemark, Gravity.CENTER, 0, 0);
        val ivNormal = popView.findViewById<AppCompatImageView>(R.id.iv_normal)
        val ivRepair = popView.findViewById<AppCompatImageView>(R.id.iv_repair)
        //根据控件定点显示，后面的两个参数是偏移量
        pop?.showAtLocation(etRemark, Gravity.CENTER, 0, 0)
        //设置默认状态
        ivNormal.background = ContextCompat.getDrawable(context, R.drawable.icon_1)
        ivRepair.background = ContextCompat.getDrawable(context, R.drawable.icon_2)
        popView.findViewById<View>(R.id.ly_normal).setOnClickListener {
            status = 0
            ivNormal.background = ContextCompat.getDrawable(context, R.drawable.icon_1)
            ivRepair.background = ContextCompat.getDrawable(context, R.drawable.icon_2)
        }

        popView.findViewById<View>(R.id.ly_repair).setOnClickListener {
            status = 1
            ivRepair.background = ContextCompat.getDrawable(context, R.drawable.icon_1)
            ivNormal.background = ContextCompat.getDrawable(context, R.drawable.icon_2)
        }

        popView.findViewById<AppCompatTextView>(R.id.tv_cancel).setOnClickListener {
            closePopupWindow()
        }
        popView.findViewById<AppCompatTextView>(R.id.tv_submit).setOnClickListener {
            val s = etRemark.text.toString()
            mListener?.onText(status, s)
            closePopupWindow()
        }
    }

    /**
     * 关闭窗口
     */
    private fun closePopupWindow() {
        if (pop != null && pop?.isShowing == true) {
            pop?.dismiss()
            pop = null
        }
    }


    private var mListener: OnClickListener? = null

    fun setOnClickListener(l: OnClickListener) {
        mListener = l
    }

    interface OnClickListener {
        fun onText(status: Int, text: String)
    }
}

