package com.xianlv.business.ui.activity

import android.os.Bundle
import com.tozzais.baselibrary.ui.BaseActivity
import com.xianlv.business.R
import com.xianlv.business.adapter.vp.WelcomeVPAdapter
import com.xianlv.business.databinding.ActivitySplashBinding

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
    }
}