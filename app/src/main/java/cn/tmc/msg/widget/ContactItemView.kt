package cn.tmc.msg.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.tmc.msg.R
import cn.tmc.msg.module.ContactBean
import kotlinx.android.synthetic.main.view_contact_item.view.*

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class ContactItemView:RelativeLayout {
    fun bindView(contactBean: ContactBean) {
        if(contactBean.isShowLetter) {
            firstLetter.visibility = View.VISIBLE
            firstLetter.text = contactBean.firstLetter.toString()
        }else firstLetter.visibility=View.GONE
        userName.text = contactBean.userName
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_contact_item,this)
    }
}