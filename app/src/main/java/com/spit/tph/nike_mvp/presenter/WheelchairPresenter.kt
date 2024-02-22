package com.csl.ams.nike_mvp.presenter

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.csl.ams.nike_mvp.BasePresenter
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import com.csl.ams.nike_mvp.impl.WheelchairContract
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import com.orhanobut.hawk.Hawk
import com.spit.tph.CaptureActivityPortrait
import com.spit.tph.InternalStorage
import com.spit.tph.MainActivity
import com.spit.tph.MainActivity.getAssetsDetailList
import com.spit.tph.MainActivity.wheelchairPosition
import com.spit.tph.R
import com.spit.tph.SystemFragment.Adapter.AssetListAdapter
import com.spit.tph.SystemFragment.AssetsDetailWithTabFragment
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * User: Nike
 *  2024/1/15 09:45
 */
class WheelchairPresenter : BasePresenter<WheelchairContract.View>(), WheelchairContract.Presenter {

    val companyId = Hawk.get(InternalStorage.Setting.COMPANY_ID, "")
    val userid = Hawk.get(InternalStorage.OFFLINE_CACHE.USER_ID, "")

    override fun onListRequest(i: Int) {
        val realm = Realm.getDefaultInstance()
        val list = realm.where(WheelchairBean::class.java)
            .equalTo("companyid", companyId)
            .equalTo("userId", userid)
            .equalTo("CheckStatus", i)
            .findAll()
        val wheelchairList = ArrayList<WheelchairBean>(realm.copyFromRealm(list))
        mRootView?.setListCallback(wheelchairList)
    }

    override fun onWheelchairItems(wheelchairRoNo: String) {
        val realm = Realm.getDefaultInstance()
        var bean = realm.where(WheelchairItemsBean::class.java)
            .equalTo("companyid", companyId)
            .equalTo("userId", userid)
            .equalTo("arono", wheelchairRoNo)
            .findFirst()
        val list = bean?.list
        val arrayType = object : TypeToken<List<WheelchairItemsBean>>() {}.type
        val wheelchairItemsList: List<WheelchairItemsBean> = Gson().fromJson(list, arrayType)
//        val wheelchairList = ArrayList<WheelchairItemsBean>(realm.copyFromRealm(wheelchairItemsList))
        mRootView?.setWheelchairItemsCallback(wheelchairItemsList as ArrayList<WheelchairItemsBean>)
    }

    override fun onWheelchairUpload(
        wheelchairRoNo: String,
        listBean: ArrayList<WheelchairItemsBean>,
        activity: FragmentActivity?
    ) {
        val timeString = getCurrentTimeString()
        val realm = Realm.getDefaultInstance()
        var bean = realm.where(WheelchairItemsBean::class.java)
            .equalTo("companyid", companyId)
            .equalTo("userId", userid)
            .equalTo("arono", wheelchairRoNo)
            .findFirst()
        if (bean == null){
            bean = WheelchairItemsBean()
        }
        realm.beginTransaction()
        bean.checkUser = userid
        bean.userId = userid
        bean.companyid = companyId
        bean.arono = wheelchairRoNo
        bean.checkDate = timeString
        bean.CheckStatus = 1
        bean.uploadStatus = 1
        bean.list = Gson().toJson(listBean)
        var wheelchairBean = realm.where(WheelchairBean::class.java)
            .equalTo("companyid", companyId)
            .equalTo("userId", userid)
            .equalTo("rono", wheelchairRoNo)
            .findFirst()
        wheelchairBean?.CheckDate = timeString
        wheelchairBean?.uploadStatus = 1
        wheelchairBean?.CheckStatus = 1
        realm.insertOrUpdate(bean)
        realm.insertOrUpdate(wheelchairBean)
        realm.commitTransaction()

        (activity as MainActivity).onWheelchairUploadRepairListener.onWheelchairUploadRepair(wheelchairBean)
        activity.onWheelchairUploadNormalListener.onWheelchairUploadNormal(wheelchairBean)
        activity.updateDrawerStatus()
//        ToastUtil.show(activity, activity?.getString(R.string.upload_tips))
        Toast.makeText(activity, activity?.getString(R.string.upload_tips), Toast.LENGTH_SHORT).show()

        /*RetrofitClient.getSPGetWebService().wheelchairUpload(companyId, userid, wheelchairRoNo, timeString, ja.toString())
            .enqueue(object : Callback<WheelchairUploadBean> {
                override fun onResponse(
                    call: Call<WheelchairUploadBean>,
                    response: Response<WheelchairUploadBean>
                ) {
                    val body = response.body()
                    if (body?.code == 200){
                        wheelchairStatus = 1
                        wheelchairUploadTime = timeString
                        ToastUtil.show(activity, activity?.getString(R.string.upload_successful))
                    }
                }

                override fun onFailure(call: Call<WheelchairUploadBean>, t: Throwable) {
                    Log.e("WheelchairPresenter", t.toString())
                }
            })*/
    }

    override fun toAssetsDetailWithTabFragment(
        item: WheelchairBean,
        activity: FragmentActivity?,
        position: Int
    ) {
        AssetsDetailWithTabFragment.ASSET_NO = item.assetNo
        AssetsDetailWithTabFragment.WITH_REMARK = false
        AssetListAdapter.WITH_EPC = true
        val assetsDetail = getAssetsDetailList(AssetsDetailWithTabFragment.ASSET_NO)
//                AssetsDetailWithTabFragment.asset.EPC = "000000231115123901000061"
        if (assetsDetail == null && !(activity as MainActivity).isNetworkAvailable) return
        val assetsDetailWithTabFragment = AssetsDetailWithTabFragment()
        val bundle = Bundle()
        bundle.putInt("pageStatus", 1)
        bundle.putString("wheelchairRoNo", item.rono)
        bundle.putString("wheelchair", item.WheelchairNo)
        assetsDetailWithTabFragment.setArguments(bundle);
        (activity as MainActivity).replaceFragment(assetsDetailWithTabFragment)
    }

    override fun inScrollToPosition(mRecyclerView: RecyclerView?) {
        if (wheelchairPosition != -1) {
            mRecyclerView?.postDelayed(
                { mRecyclerView?.scrollToPosition(wheelchairPosition) },
                300
            )
        }
    }

    override fun toZking(activity: FragmentActivity?) {
        IntentIntegrator(activity)
            .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            .setPrompt("")
            .setCameraId(0)
            .setBeepEnabled(true)
            .setBarcodeImageEnabled(true)
            .setCaptureActivity(CaptureActivityPortrait::class.java)
            .initiateScan()
    }

    fun getCurrentTimeString(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(calendar.time)
    }
}