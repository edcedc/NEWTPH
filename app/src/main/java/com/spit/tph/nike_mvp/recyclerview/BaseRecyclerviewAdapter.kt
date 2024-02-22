package com.yc.reid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.csl.ams.nike_mvp.recyclerview.ViewHolder

abstract class BaseRecyclerviewAdapter<T>(var act: Context, var listBean: List<T>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = listBean.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return onCreateViewHolde(parent, viewType)
    }

    abstract fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        onBindViewHolde(viewHolder, position)
    }

    abstract fun onBindViewHolde(viewHolder: ViewHolder, position: Int)

    override fun getItemViewType(position: Int): Int {
        return position
    }
}