package com.xianlv.business.bean.switchroom

data class EmployeePermissions(val authorityType:Int){


    /**
     * 是否可以收款
     */
    fun isCanReceivePayment(): Boolean = authorityType == 1 || authorityType == 2


    /**
     * 是否可以退款
     */
    fun isCanRefund(): Boolean = authorityType == 1 || authorityType == 3

}
