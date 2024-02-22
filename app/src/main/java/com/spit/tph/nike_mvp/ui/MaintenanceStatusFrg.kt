package com.csl.ams.nike_mvp.ui

import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import com.csl.ams.nike_mvp.adapter.WheelchairStatusAdapter
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import com.csl.ams.nike_mvp.impl.WheelchairContract
import com.csl.ams.nike_mvp.presenter.WheelchairPresenter
import com.csl.ams.nike_mvp.recyclerview.DpUtils
import com.csl.ams.nike_mvp.recyclerview.SpaceItemDecoration
import com.spit.tph.R
import com.spit.tph.SystemFragment.BaseFragment

/**
 * User: Nike
 *  2024/1/15 15:50
 */
class MaintenanceStatusFrg: BaseFragment(), WheelchairContract.View, OnClickListener {

    val mPresenter by lazy { WheelchairPresenter() }

    val listBean = ArrayList<WheelchairItemsBean>()

    val adapter by lazy { WheelchairStatusAdapter(requireContext(), listBean) }

    var mRecyclerView : RecyclerView? = null

    var wheelchairRoNo: String = ""
    var wheelchair: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        if (bundle != null) {
            wheelchairRoNo = bundle.getString("wheelchairRoNo")
            wheelchair = bundle.getString("wheelchair")
        }
        return inflater.inflate(R.layout.f_wheelchair_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.init(this)
        view.findViewById<AppCompatTextView>(R.id.tv_title).text = "${requireActivity().getText(R.string.wheelchair_maintenance)}：$wheelchair"
        view.findViewById<AppCompatButton>(R.id.bt_confirm).setOnClickListener(this)

        mRecyclerView = view.findViewById(R.id.recyclerView)

        mRecyclerView?.setLayoutManager(LinearLayoutManager(context));
        mRecyclerView?.addItemDecoration(SpaceItemDecoration(DpUtils.dip2px(requireContext(),10f), DpUtils.dip2px(requireContext(),10f)))
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.setNestedScrollingEnabled(true)
        mRecyclerView?.adapter = adapter
        mPresenter.onWheelchairItems(wheelchairRoNo)
        adapter.setOnItemClickListener(object : WheelchairStatusAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, item: WheelchairItemsBean) {

            }
        })
    }

    override fun setWheelchairItemsCallback(list: java.util.ArrayList<WheelchairItemsBean>) {
        listBean.clear()
        listBean.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.bt_confirm->{
                listBean.forEachIndexed { index, dataBean ->
                    if (!dataBean.isChecked){
                        mRecyclerView?.scrollToPosition(index)
                        Toast.makeText(activity, getText(R.string.toast1), Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                mPresenter.onWheelchairUpload(wheelchairRoNo, listBean, requireActivity())
            }
        }
    }

    override fun setListCallback(list: ArrayList<WheelchairBean>) {
        TODO("Not yet implemented")
    }
}