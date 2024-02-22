package com.csl.ams.nike_mvp.impl

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import com.csl.ams.nike_mvp.IBaseView
import com.csl.ams.nike_mvp.IPresenter
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import kotlin.collections.ArrayList

/**
 * User: Nike
 *  2024/1/15 09:40
 */
interface WheelchairContract {

    interface View : IBaseView {

        fun setListCallback(list: ArrayList<WheelchairBean>)
        fun setWheelchairItemsCallback(list: ArrayList<WheelchairItemsBean>)

    }

    interface Presenter: IPresenter<View> {

        fun onListRequest(i: Int)
        fun onWheelchairItems(wheelchairRoNo: String)
        fun onWheelchairUpload(wheelchairRoNo: String, ja: ArrayList<WheelchairItemsBean>, activity: FragmentActivity?)
        fun toAssetsDetailWithTabFragment(
            item: WheelchairBean,
            activity: FragmentActivity?,
            position: Int
        )

        fun inScrollToPosition(mRecyclerView: RecyclerView?)
        fun toZking(activity: FragmentActivity?)

    }

}