package com.yyc.stocktake.bean


import java.io.Serializable

/**
 * Created by yc on 2017/8/17.
 */
class DataBean: Serializable {

    var name: String= ""
    var epc: String= ""
    var WheelchairNo: String= ""
    var assetNo: String= ""
    var brand: String= ""
    var model: String= ""
    var category: String= ""
    var location: String= ""
    var CheckDate: String = ""
    var Item: String = ""
    var ItemDetails: String = ""
    var remark: String = ""
    var text: String = ""
    var rono: String = ""
    var statusid: Int = 0
    var status: Int = 0
    var ItemStatus: Int = -1
    var id: Int = 0
    var isChecked: Boolean = false
    var position: Int = 0
    var CheckStatus: Int = 0
    var ItemsDetails = ArrayList<DataBean>()

}