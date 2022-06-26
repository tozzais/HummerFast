package com.xianlv.business.ui.activity

import android.app.ApplicationErrorReport
import android.os.Bundle
import com.blankj.utilcode.util.CrashUtils
import com.tozzais.baselibrary.ui.BaseActivity
import com.xianlv.business.MainActivity
import com.xianlv.business.R
import com.xianlv.business.adapter.vp.WelcomeVPAdapter
import com.xianlv.business.databinding.ActivitySplashBinding
import com.xianlv.business.global.GlobalParam
import com.xianlv.business.toast.OnDialogClickListener
import com.xianlv.business.toast.PrivacyUtil
import com.xuexiang.xutil.app.AppUtils

/**
 * author : xumingming
 * data : 2022/6/18
 * description ：
 * email : 835683840@qq.com
 */
class WelActivity : BaseActivity<Any>(){

    private lateinit var mBinding: ActivitySplashBinding

    override fun getLayoutId() = R.layout.activity_splash



    override fun initView(savedInstanceState: Bundle?) {


        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }

    override fun loadData() {
        val listOf : List<Int> = listOf(R.drawable.splash2, R.drawable.splash3, R.drawable.splash4)
        val welcomeVPAdapter = WelcomeVPAdapter(this, listOf)
        mBinding.viewPager.adapter = welcomeVPAdapter
        mBinding.viewPager.offscreenPageLimit = 2



        val firstUse = GlobalParam.getFirstUse()
        if (!firstUse) {
//            PrivacyUtil.showTwo(mActivity, object : OnDialogClickListener {
//                override fun onSure() {
//                    GlobalParam.setFirstUse(true)
//                    if (GlobalParam.getUserLogin()) {
//                        MainActivity.launch(mActivity)
//                        finish()
//                    }
//                }
//
//                override fun onCancel() {
//                    if (!isFinishing) finish()
//                }
//            })
        } else {
//            if (GlobalParam.getUserLogin()) {
                MainActivity.launch(mActivity)
                finish()
//            }
        }
    }
}