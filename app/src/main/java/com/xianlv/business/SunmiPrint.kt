package com.xianlv.business

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.sunmi.peripheral.printer.InnerResultCallback
import com.xianlv.business.bean.OrderDetail
import com.xianlv.business.bean.ReceiveOrderItem

object SunmiPrint  {


    /**
     *type 0 收款  1退款
     */
    fun printReceipt(context:Context, receiveOrderItem: OrderDetail, type:Int) {
        val printerService = PrinterKernel.printerService
        if (printerService != null) {
            try {
                printerService.printerInit(null)
                printerService.enterPrinterBuffer(true)

                val size = 22f
                val colsAlign = intArrayOf(0, 2)
                val colsWidth = intArrayOf(2, 3)

                PrinterKernel.setBold(true)
                printerService.setAlignment(1, null)
                printerService.printTextWithFont(receiveOrderItem.tenantName+"\n", "", size + 6, null)
                PrinterKernel.setBold(false)

                val s1 = if (type==0) "线下收款单" else "线下退款单"
                printerService.printTextWithFont("$s1\n", "", size, null)
                printerService.setAlignment(0, null)

                printerService.lineWrap(1, null)

                printerService.setFontSize(size, null)
                var colsTextArr = arrayOf("订单号：", receiveOrderItem.orderNo)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款金额：", "¥"+receiveOrderItem.money)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("付款人：", receiveOrderItem.nickname)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款员工：", receiveOrderItem.operuser)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款场景：", receiveOrderItem.purposeStatus)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款时间：", receiveOrderItem.createtime)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                if (type == 1){
                    colsTextArr = arrayOf("退款时间：", receiveOrderItem.checktime)
                    printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)
                    colsTextArr = arrayOf("操作人：", receiveOrderItem.operuserRefund)
                    printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)
                }
                val s = if (type == 0){
                    if (TextUtils.isEmpty(receiveOrderItem.remark)) "" else receiveOrderItem.remark
                }else{
                    if (TextUtils.isEmpty(receiveOrderItem.reason)) "" else receiveOrderItem.reason
                }

                colsTextArr = arrayOf("备注：", s)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                printerService.lineWrap(1, null)

                printerService.setAlignment(1, null)
                printerService.printTextWithFont("------------------------------\n", "", size, null)
                printerService.setAlignment(0, null)

                printerService.setFontSize(size + 4, null)

                if (type == 0){
                    colsTextArr = arrayOf("收款金额：", receiveOrderItem.money)
                    printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)
                }else{
                    colsTextArr = arrayOf("退款金额：", receiveOrderItem.refundMoney)
                    printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)
                }
                printerService.lineWrap(6, null)

                printerService.exitPrinterBufferWithCallback(true, printCallback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "请检查打印服务是否已安装和启动", Toast.LENGTH_SHORT).show()
        }
    }

    private val printCallback = object : InnerResultCallback() {

        override fun onRunResult(isSuccess: Boolean) {
        }

        override fun onReturnString(result: String?) {
        }

        override fun onRaiseException(code: Int, message: String?) {
        }

        override fun onPrintResult(code: Int, message: String?) {
            if (code == 0) {
                // success
            } else {
                // failed
            }
        }

    }

}