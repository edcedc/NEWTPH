package com.csl.ams.nike_mvp.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * User: Nike
 *  2024/1/22 16:04
 */
open class WheelchairBean : RealmObject() {

    var id: Int = 0
    var assetNo: String? = null
    var WheelchairNo: String? = null
    var LastAssetNo: String? = null
    var name: String? = null
    var brand: String? = null
    var model: String? = null
    var category: String? = null
    var companyid: String? = null
    var userId: String? = null
    var location: String? = null
    var epc: String? = null
    var CheckDate: String? = null
    var CheckStatus: Int = 0
    var uploadStatus: Int = 0
    var statusid: Int = 0
    var rono: String? = null
}