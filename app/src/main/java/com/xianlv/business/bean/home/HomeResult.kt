package com.xianlv.business.bean.home

data class HomeResult(
    val powerStationList: List<HomePowerItem>?,
    val appBannerList: List<AppBannerItem>?,
    val announcementList: List<HomeNoticeItem>?
)
