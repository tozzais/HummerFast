package com.xianlv.business.adapter.vp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.xianlv.business.MainActivity

/**
 * author : xumingming
 * data : 2022/6/18
 * description ：
 * email : 835683840@qq.com
 */
class WelcomeVPAdapter(val context: Context,var list: List<Int>) : PagerAdapter() {

    override fun getCount() = list.size

    override fun isViewFromObject(view: View, any : Any) =  view == any

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewAt(position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(list[position])
        imageView.setOnClickListener {
            if (position == list.size-1){
                MainActivity.launch(context)
            }
        }
        container.addView(imageView)
        return imageView
    }
}