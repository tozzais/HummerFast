package com.xianlv.business.bean.home

data class HomePowerItem(
    val powerStationId:String?,
    val logo:String?,
    val powerStationName:String?,
    val price:String?,
    val fastChargeNum:String?,
    val fastUsableNum:String?,
    val trickleChargeNum:String?,
    val trickUsableNum:String?,
    val distance:String?,
    var collect:Int?,
)
