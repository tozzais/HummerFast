package com.xianlv.business

import android.content.Context
import android.widget.Toast
import com.sunmi.peripheral.printer.InnerResultCallback

object SunmiPrint  {


    fun printReceipt(context:Context) {
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

                printerService.printTextWithFont("线下退款单\n", "", size, null)
                printerService.setAlignment(0, null)

                printerService.lineWrap(1, null)

                printerService.setFontSize(size, null)
                var colsTextArr = arrayOf("订单号：", "2141414141712634")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款金额：", "¥1000.00")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款人：", "许明明")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款员工：", "王某某 121****2131")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款场景：", "餐饮服务-大堂吧")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("收款时间：", "2021-06-25 13:12:22")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("操作人：", "王某某 121****2131")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                colsTextArr = arrayOf("备注：", "多付款导致退款")
                printerService.printColumnsString(colsTextArr, colsWidth, colsAlign, null)

                printerService.lineWrap(1, null)

                printerService.setAlignment(1, null)
                printerService.printTextWithFont("------------------------------\n", "", size, null)
                printerService.setAlignment(0, null)

                printerService.setFontSize(size + 4, null)
                colsTextArr = arrayOf("退款金额：", "¥1000.00")
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