package com.csl.ams.nike_mvp

/**
 * User: Nike
 *  2024/1/15 09:44
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set

    override fun init(mRootView: T) {
        this.mRootView = mRootView
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

}