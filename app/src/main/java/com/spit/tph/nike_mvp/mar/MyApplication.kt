package com.csl.ams.nike_mvp.mar

import android.app.Application
import android.content.Context


/**
 * User: Nike
 *  2024/1/23 15:24
 */
class MyApplication: Application() {


    companion object {

        private lateinit var instance: MyApplication

        fun get(): MyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}