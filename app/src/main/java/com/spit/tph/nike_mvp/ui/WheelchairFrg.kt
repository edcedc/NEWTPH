package com.csl.ams.nike_mvp.ui
//


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.csl.ams.nike_mvp.SearchEvent
import com.csl.ams.nike_mvp.adapter.PagerAdapter
import com.csl.ams.nike_mvp.bean.WheelchairBean
import com.csl.ams.nike_mvp.bean.WheelchairItemsBean
import com.csl.ams.nike_mvp.impl.WheelchairContract
import com.csl.ams.nike_mvp.presenter.WheelchairPresenter
import com.spit.tph.MainActivity
import com.spit.tph.R
import com.spit.tph.SystemFragment.BaseFragment
import com.yyc.stocktake.bean.DataBean
import org.greenrobot.eventbus.EventBus

/**
 * User: Nike
 *  2024/1/12 16:52
 *   上个安卓写的代码毫无维护性，只能自己新开页面，新开架构
 */
class WheelchairFrg: BaseFragment(), WheelchairContract.View, OnClickListener{

    val mPresenter by lazy { WheelchairPresenter() }

    var mEdittext : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_wheelchair, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.back).visibility = View.INVISIBLE
        (view.findViewById<View>(R.id.toolbar_title) as TextView).text = getString(R.string.wheelchair_maintenance)
        view.findViewById<View>(R.id.menu).setOnClickListener {
            (activity as MainActivity?)!!.updateDrawerStatus()
            (activity as MainActivity?)!!.mDrawerLayout.openDrawer(Gravity.RIGHT)
        }
        mEdittext = view.findViewById(R.id.edittext)
        view.findViewById<ImageView>(R.id.add).visibility = View.GONE
        view.findViewById<ImageView>(R.id.scan).setOnClickListener(this)

        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        var fragments: ArrayList<Fragment> = arrayListOf()
        var bundle = Bundle()
        var frg1 = WheelchairNormalFrg()
        frg1.arguments = bundle
        fragments.add(frg1)
        var frg2 = WheelchairRepairFrg()
        frg2.arguments = bundle
        fragments.add(frg2)
        val tabTitles = listOf(
            requireActivity().getString(R.string.checked_yes),
            requireActivity().getString(R.string.checked_not)
        )
        val pagerAdapter = PagerAdapter(childFragmentManager, fragments, tabTitles)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = tabTitles.size

        mEdittext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                EventBus.getDefault().post(SearchEvent(p0.toString()))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.scan ->{
                mPresenter.toZking(activity)
            }
        }
    }

    override fun setListCallback(list: ArrayList<WheelchairBean>) {
        TODO("Not yet implemented")
    }

    override fun setWheelchairItemsCallback(list: ArrayList<WheelchairItemsBean>) {
        TODO("Not yet implemented")
    }

}