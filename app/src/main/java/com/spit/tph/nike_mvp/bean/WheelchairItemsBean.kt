package com.csl.ams.nike_mvp.bean

import io.realm.RealmObject

/**
 * User: Nike
 *  2024/1/22 17:59
 */
open class WheelchairItemsBean: RealmObject() {

    var id: Int = 0
    var ItemStatus: Int = -1
    var CheckStatus: Int = -1
    var uploadStatus: Int = 0
    var isChecked: Boolean = false
    var Item: String? = null
    var ItemDetails: String? = null
    var Remarks: String? = null
    var companyid: String? = null
    var arono: String? = null//轮椅id
    var userId: String? = null
    var checkUser: String? = null
    var checkDate: String? = null
    var list: String? = null

}