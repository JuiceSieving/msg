package cn.tmc.msg.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.tmc.msg.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_receive_message_item.view.*
import java.util.*

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 21
 * version: 1.0
 * description
 */
class ReceiveMsgItemView:RelativeLayout {
    fun bindView(emMessage: EMMessage, showTime: Boolean) {
        if(showTime) {
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
            timestamp.visibility=View.VISIBLE
        }else timestamp.visibility=View.GONE

        if(emMessage.type==EMMessage.Type.TXT){
            receiveMessage.text=(emMessage.body as EMTextMessageBody).message
        }else{
            receiveMessage.text=context.getString(R.string.no_text_message)
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_receive_message_item,this)
    }
}