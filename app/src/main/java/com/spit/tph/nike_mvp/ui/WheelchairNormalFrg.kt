package com.csl.ams.nike_mvp.ui
//


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csl.ams.nike_mvp.SearchEvent
import com.csl.ams.nike_mvp.adapter.WheelchairAdapter
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import com.csl.ams.nike_mvp.impl.WheelchairContract
import com.csl.ams.nike_mvp.presenter.WheelchairPresenter
import com.google.zxing.integration.android.IntentIntegrator
import com.orhanobut.hawk.Hawk
import com.spit.tph.InternalStorage
import com.spit.tph.InventoryBarcodeTask
import com.spit.tph.MainActivity
import com.spit.tph.R
import com.spit.tph.SystemFragment.BaseFragment
import com.spit.tph.nike_mvp.recyclerview.AutoSwipeRefreshView
import io.realm.Realm
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * User: Nike
 *  2024/1/12 16:52
 *   上个安卓写的代码毫无维护性，只能自己新开页面，新开架构
 */
class WheelchairNormalFrg: BaseFragment(), WheelchairContract.View, MainActivity.OnActivityResultListener1, MainActivity.onWheelchairUploadNormalListener{

    val mPresenter by lazy { WheelchairPresenter() }

    val listBean = ArrayList<WheelchairBean>()

    val adapter by lazy { WheelchairAdapter(requireContext(), listBean) }

    var mSwipeRefresh : AutoSwipeRefreshView? = null
    var mRecyclerView : RecyclerView? = null

    var inventoryBarcodeTask: InventoryBarcodeTask? = null

    var isCreated = false

    var mSearText: String = ""

    val companyId = Hawk.get(InternalStorage.Setting.COMPANY_ID, "")
    val userid = Hawk.get(InternalStorage.OFFLINE_CACHE.USER_ID, "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.b_not_title_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.init(this)
        (activity as MainActivity).setOnActivityResultListener1(this)
        (activity as MainActivity).setOnWheelchairUploadNormalListener(this)

        mRecyclerView = view.findViewById(R.id.recyclerView)
        mSwipeRefresh = view.findViewById(R.id.swipeRefresh)

        mRecyclerView?.setLayoutManager(LinearLayoutManager(context));
//        mRecyclerView.addItemDecoration(SpaceItemDecoration(DpUtils.dip2px(requireContext(),10f), DpUtils.dip2px(requireContext(),10f)))
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.setNestedScrollingEnabled(true)
        mRecyclerView?.adapter = adapter
        mSwipeRefresh?.isEnabled = false

        if (!isCreated){
            mSwipeRefresh?.post({
                mSwipeRefresh?.autoRefresh()
            })
        }
        mSwipeRefresh?.setOnRefreshListener {
            mPresenter.onListRequest(1)
        }
        adapter?.setOnItemClickListener(object : WheelchairAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, item: WheelchairBean, view: View) {
                mPresenter.toAssetsDetailWithTabFragment(item, activity, if (mSearText != null) -1 else position)
            }
        })

    }

    override fun setListCallback(list: ArrayList<WheelchairBean>) {
        isCreated = true
        mSwipeRefresh?.isRefreshing = false
        listBean.clear()
        listBean.addAll(list)
        adapter?.appendList(list)
        adapter?.notifyDataSetChanged()
    }

    override fun setWheelchairItemsCallback(list: ArrayList<WheelchairItemsBean>) {}

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        var contents = result.contents
        var index = adapter.mFilterList.indexOfFirst { bean ->
            (bean.assetNo.equals(contents, ignoreCase = true) == true)
        }
        if (index != -1){
            Handler().postDelayed({
                mPresenter.toAssetsDetailWithTabFragment(adapter.mFilterList[index], activity, -1)
            }, 1000)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SearchEvent) {
        mSearText = event.text
        adapter?.filter?.filter(mSearText)
    }

    override fun onWheelchairUploadNormal(wheelchairBean: WheelchairBean?) {
        if (wheelchairBean != null){
           /* val index = listBean.indexOfFirst { bean ->
                (bean.rono == wheelchairBean.rono)
            }
            if (index != -1){
                listBean.removeAt(index)
            }
            listBean.add(0, wheelchairBean)
            adapter.notifyItemChanged(0)
            adapter.appendList(listBean)
            */
            mPresenter.onListRequest(1)
            if (mSearText != null){
                adapter!!.filter.filter(mSearText)
            }
        }
    }

}