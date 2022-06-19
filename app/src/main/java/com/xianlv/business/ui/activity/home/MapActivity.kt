package com.xianlv.business.ui.activity.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import butterknife.BindView
import com.amap.api.maps2d.MapView
import com.tozzais.baselibrary.ui.BaseActivity
import com.tozzais.baselibrary.util.ClickUtils
import com.xianlv.business.R
import java.util.*
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.model.MyLocationStyle







/**
 * author : xumingming
 * data : 2022/6/19
 * description ：
 * email : 835683840@qq.com
 */
class MapActivity : BaseActivity<Any>() {

    companion object{
        @JvmStatic
        fun launch(context: Context){
            if (!ClickUtils.isFastClick()){
                return
            }
            val intent = Intent(context, MapActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_map

    override fun initView(savedInstanceState: Bundle?) {
        setBackTitle("地图模式")
        mapView = findViewById(R.id.mapview)
        mapView.onCreate(savedInstanceState)
    }



    private lateinit var mapView: MapView
    private var aMap: AMap? = null
    override fun loadData() {
        //初始化地图控制器对象
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.map
        }

        initLocation()
    }

    fun initLocation() {
        val myLocationStyle = MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        myLocationStyle.showMyLocation(true)
        aMap?.let {
            it.setMyLocationStyle(myLocationStyle)
            it.isMyLocationEnabled = true
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}