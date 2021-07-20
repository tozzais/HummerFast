package com.xianlv.business

import android.content.Context
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.SunmiPrinterService
object PrinterKernel {

    var printerService: SunmiPrinterService ? = null

    fun init(context: Context) {
        InnerPrinterManager.getInstance().bindService(context, printerCallback)
    }

    private val printerCallback = object : InnerPrinterCallback() {

        override fun onConnected(service: SunmiPrinterService?) {
            printerService = service
        }

        override fun onDisconnected() {
            printerService = null
        }

    }

    fun setBold(bold: Boolean) {
        val printer = printerService
        if (printer != null) {
            val returnText = ByteArray(3)
            returnText[0] = 0x1B
            returnText[1] = 0x45
            if (bold) {
                returnText[2] = 0x01 // 表示加粗
            } else {
                returnText[2] = 0x00
            }
            printer.sendRAWData(returnText, null)
        }
    }

}