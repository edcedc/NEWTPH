package com.csl.ams.nike_mvp

/**
 * User: Nike
 *  2024/1/15 09:42
 */
interface IPresenter<in V: IBaseView> {

    fun init(mRootView: V)

}