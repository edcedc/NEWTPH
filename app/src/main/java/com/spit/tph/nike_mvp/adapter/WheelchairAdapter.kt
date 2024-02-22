package com.csl.ams.nike_mvp.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.recyclerview.ViewHolder
import com.csl.ams.nike_mvp.ui.WheelchairFrg
import com.orhanobut.hawk.Hawk
import com.spit.tph.Entity.Status
import com.spit.tph.InternalStorage
import com.spit.tph.R
import com.yc.reid.adapter.BaseRecyclerviewAdapter
import io.realm.RealmResults


/**
 * User: Nike
 *  2024/1/15 11:03
 */
class WheelchairAdapter(act: Context, listBean: List<WheelchairBean>) :
    BaseRecyclerviewAdapter<WheelchairBean>(act, listBean), Filterable {

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.i_wheelchair, parent, false)
        )
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = mFilterList[position]
        bean.run {
            try {
                viewHolder.setText(R.id.tv_title, assetNo + " | " + name)
                viewHolder.setText(R.id.search_cell_brand_value, brand)
                viewHolder.setText(R.id.search_cell_model_value, model)
                viewHolder.setText(R.id.search_cell_location_value, location)
                viewHolder.setText(R.id.search_cell_category_value, category)
                viewHolder.setText(R.id.search_cell_epc_value, epc)
                viewHolder.setText(R.id.tv_wheelchair, WheelchairNo)
                if (CheckDate != null && CheckDate!!.length > 0){
                    viewHolder.setText(R.id.tv_date, CheckDate?.substring(0, 10))
                }

                var statusColor = ContextCompat.getColor(act, R.color.colorPrimary)
                viewHolder.getView<AppCompatTextView>(R.id.tv_status).visibility = View.VISIBLE
                when (statusid) {
                    2 -> {
                        statusColor = ContextCompat.getColor(act, R.color.colorPrimary)
                    }

                    3, 4 -> {
                        statusColor = ContextCompat.getColor(act, android.R.color.holo_orange_light)
                    }

                    5, 6, 7, 8, 9999 -> {
                        statusColor = ContextCompat.getColor(act, android.R.color.holo_red_dark)
                    }

                    else -> {
                        viewHolder.getView<AppCompatTextView>(R.id.tv_status).visibility = View.GONE
                    }
                }
                viewHolder.getView<AppCompatTextView>(R.id.tv_status)
                    .setBackgroundColor(statusColor)
                viewHolder.setText(
                    R.id.tv_status,
                    Status().getStatus(
                        Hawk.get(InternalStorage.Setting.LANGUAGE, "zh"),
                        "$statusid"
                    )
                )

                if (CheckStatus == 0) {
                    viewHolder.setText(R.id.tv_check_status, "")
                    viewHolder.getView<AppCompatTextView>(R.id.tv_check_status)
                        .setTextColor(ContextCompat.getColor(act, R.color.colorPrimary))
                } else {
                    viewHolder.setText(R.id.tv_check_status, act.getString(R.string.checked_no))
                    viewHolder.getView<AppCompatTextView>(R.id.tv_check_status)
                        .setTextColor(ContextCompat.getColor(act, android.R.color.holo_red_dark))
                }
                when (CheckStatus) {
                    0 -> {
                        viewHolder.setText(
                            R.id.tv_check_status,
                            act.getString(R.string.checked_not)
                        )
                        viewHolder.getView<AppCompatTextView>(R.id.tv_check_status).setTextColor(
                            ContextCompat.getColor(
                                act,
                                android.R.color.holo_red_dark
                            )
                        )
                    }

                    1 -> {
                        viewHolder.setText(
                            R.id.tv_check_status,
                            act.getString(R.string.checked_yes)
                        )
                        viewHolder.getView<AppCompatTextView>(R.id.tv_check_status)
                            .setTextColor(ContextCompat.getColor(act, R.color.colorPrimary))
                    }

                    else -> {
                        viewHolder.setText(
                            R.id.tv_check_status,
                            act.getString(R.string.checked_completed)
                        )
                        viewHolder.getView<AppCompatTextView>(R.id.tv_check_status)
                            .setTextColor(ContextCompat.getColor(act, R.color.colorPrimary))
                    }
                }
            } catch (e: Throwable) {
                Log.e("WheelchairAdapter", "不知道", e)
            }

            viewHolder.setOnItemClickListener(object : OnClickListener {
                override fun onClick(p0: View?) {
                    mItemClickListener?.onItemClick(position, bean, p0!!)
                }
            })
        }
    }


    var mFilterList = ArrayList<WheelchairBean>()

    fun appendList(list: List<WheelchairBean>) {
        listBean = list as MutableList<WheelchairBean>
        //这里需要初始化filterList
        mFilterList = list as ArrayList<WheelchairBean>
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            //执行过滤操作
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = listBean as ArrayList<WheelchairBean>
                } else {
                    val filteredList: MutableList<WheelchairBean> = ArrayList()
                    for (i in listBean.indices) {
                        val bean = listBean[i]
                        val epc = bean.epc
                        val assetNo = bean.assetNo
                        val WheelchairNo = bean.WheelchairNo
                        if (
                            epc?.contains(charString, ignoreCase = true) == true
                            || assetNo?.contains(charString, ignoreCase = true) == true
                            || WheelchairNo?.contains(charString, ignoreCase = true) == true
                        ) {
                            filteredList.add(bean)
                        }
                    }
                    mFilterList = filteredList as ArrayList<WheelchairBean>
                }
                val filterResults = FilterResults()
                filterResults.values = mFilterList
                return filterResults
            }

            //把过滤后的值返回出来
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                mFilterList = filterResults.values as ArrayList<WheelchairBean>
//                notifyDataSetChanged()
                val filteredList = filterResults.values as? ArrayList<WheelchairBean>
                if (filteredList != null) {
                    mFilterList = filteredList
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mFilterList.size
    }

    private var mItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(l: OnItemClickListener?) {
        mItemClickListener = l
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: WheelchairBean, view: View)
    }

    fun removeItem(wheelchairBean: WheelchairBean) {
        val index = mFilterList.indexOfFirst { it.rono == wheelchairBean.rono }
        if (index != -1) {
            val mutableList = mFilterList.toMutableList()
            mutableList.removeAt(index)
            mFilterList = listBean as ArrayList<WheelchairBean>
            notifyItemRemoved(index)
        }
    }

}