package com.csl.ams.nike_mvp.bean

import java.io.Serializable


class BaseResponseBean<T>: Serializable {

    var msg: String = ""
    var code: Int = 0

    var data: T? = null

}
