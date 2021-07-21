package com.xianlv.business

import android.content.Context
import android.widget.Toast
import com.sunmi.peripheral.printer.InnerResultCallback
import com.xianlv.business.bean.ReceiveOrderItem

object SunmiPrint  {


    fun printReceipt(context:Context,receiveOrderItem: ReceiveOrderItem,type:Int) {
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
                printerService.printTextWithFont("上海皇廷花园大酒店\n", "", size + 6, null)
                PrinterKernel.setBold(false)

                val s1 = if (type==1) "线下收款单" else "线下退款单"
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

                colsTextArr = arrayOf("收款类型：", receiveOrderItem.purposeStatus)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款时间：", receiveOrderItem.payTime)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

//                colsTextArr = arrayOf("操作人：", "王某某 121****2131")
//                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("备注：", receiveOrderItem.remark)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                printerService.lineWrap(1, null)

                printerService.setAlignment(1, null)
                printerService.printTextWithFont("------------------------------\n", "", size, null)
                printerService.setAlignment(0, null)

                printerService.setFontSize(size + 4, null)
                colsTextArr = arrayOf("收款金额：", receiveOrderItem.money)
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

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