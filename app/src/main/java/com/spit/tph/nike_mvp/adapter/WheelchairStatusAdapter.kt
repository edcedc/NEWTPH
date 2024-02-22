package com.csl.ams.nike_mvp.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import com.csl.ams.nike_mvp.recyclerview.ViewHolder
import com.spit.tph.R
import com.yc.reid.adapter.BaseRecyclerviewAdapter

/**
 * User: Nike
 *  2024/1/15 11:03
 */
class WheelchairStatusAdapter(act: Context, listBean: List<WheelchairItemsBean>) : BaseRecyclerviewAdapter<WheelchairItemsBean>(act, listBean){

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_wheelchair_item, parent, false))
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        bean.run{
            val etRemark = viewHolder.getView<AppCompatEditText>(R.id.et_remark)
            val tvNormal = viewHolder.getView<AppCompatTextView>(R.id.tv_normal)
            val tvRepair = viewHolder.getView<AppCompatTextView>(R.id.tv_repair)
            viewHolder.setText(R.id.tv_title, Item)
            viewHolder.setText(R.id.tv_desc, ItemDetails)

            when(ItemStatus){
                0 ->{
                    isChecked = true
                    tvNormal.setBackgroundColor(ContextCompat.getColor(act, R.color.colorPrimary))
                    tvRepair.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
                }
                1 ->{
                    isChecked = true
                    tvRepair.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_red_dark))
                    tvNormal.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
                }
                else ->{
                    isChecked = false
                    tvRepair.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
                    tvNormal.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
                }
            }

            tvNormal.setOnClickListener {
                ItemStatus = 0
                isChecked = true
                tvNormal.setBackgroundColor(ContextCompat.getColor(act, R.color.colorPrimary))
                tvRepair.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
            }
            tvRepair.setOnClickListener {
                ItemStatus = 1
                isChecked = true
                tvRepair.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_red_dark))
                tvNormal.setBackgroundColor(ContextCompat.getColor(act, R.color.grey_dcdcdd))
            }

            //备注
            if (Remarks != null){
                etRemark.setText(Remarks)
            }
            etRemark.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Remarks = etRemark.text.toString()
                    val inputMethodManager = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(etRemark.windowToken, 0)
                }
                false
            }
            val textWatcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,  after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    Remarks = s.toString()
                }
            }
            if (etRemark.getTag() is TextWatcher) {
                etRemark.removeTextChangedListener(etRemark.getTag() as TextWatcher)
            }
            etRemark.setText(Remarks)
            etRemark.addTextChangedListener(textWatcher)
            etRemark.setTag(textWatcher)

            viewHolder.setOnItemClickListener(object :OnClickListener{
                override fun onClick(p0: View?) {
                    mItemClickListener?.onItemClick(position, bean)
                }
            })
        }
    }

    private var mItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(l: OnItemClickListener?) {
        mItemClickListener = l
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: WheelchairItemsBean)
    }

    private var mItemEditorActionListener: OnEditorActionListener? = null

    fun setOnEditorActionListener(l: OnEditorActionListener?) {
        mItemEditorActionListener = l
    }

    interface OnEditorActionListener {
        fun onItemClick(position: Int, item: WheelchairItemsBean)
    }

}