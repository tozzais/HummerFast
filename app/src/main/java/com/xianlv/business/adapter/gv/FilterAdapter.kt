package com.xianlv.business.adapter.gv

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.xianlv.business.R
import com.xianlv.business.bean.local.FilterBean

/**
 * author : xumingming
 * data : 2022/6/18
 * description ：
 * email : 835683840@qq.com
 */
class FilterAdapter(val context: Context,val list: List<FilterBean>) : BaseAdapter(){


    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflate = View.inflate(context, R.layout.item_filter, null)
        val  tv_text = inflate.findViewById<TextView>(R.id.tv_text)

        val item = getItem(position)
        if (item.isCheck){
            tv_text.setBackgroundResource(R.drawable.shape_blue5)
            tv_text.setTextColor(ContextCompat.getColor(context,R.color.white))
        }else{
            tv_text.setBackgroundResource(R.drawable.shape_gray5)
            tv_text.setTextColor(ContextCompat.getColor(context,R.color.black_title_color))
        }
        tv_text.text = item.name
        tv_text.setOnClickListener { select(position) }
        return inflate

    }

    var selectPosition : Int = 0

    fun getSelect(): Int {
        return selectPosition
    }

    fun select(position : Int){
        this.selectPosition = position
        for (index in list.indices){
            list[index].isCheck = index == position
        }
        notifyDataSetChanged()

    }

    fun reset(){
        select(0)
    }
}