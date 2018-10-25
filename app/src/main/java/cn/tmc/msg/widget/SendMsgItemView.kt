package cn.tmc.msg.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.tmc.msg.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_send_message_item.view.*
import java.util.*

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 21
 * version: 1.0
 * description
 */
class SendMsgItemView:RelativeLayout{
    fun bindView(emMessage: EMMessage, showTime: Boolean) {
        if (showTime) {
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
            timestamp.visibility=View.VISIBLE
        }else timestamp.visibility=View.GONE

        if (emMessage.type == EMMessage.Type.TXT){
            sendMessage.text= (emMessage.body as EMTextMessageBody).message
        }else{
            sendMessage.text= context.getString(R.string.no_text_message)
        }

        emMessage.status()?.let {
            when(it) {
                EMMessage.Status.INPROGRESS -> {
                    sendMessageProgress.visibility=View.VISIBLE
                    sendMessageProgress.setImageResource(R.drawable.send_message_progress)
                    val animationDrawable = sendMessageProgress.drawable as AnimationDrawable
                    animationDrawable.start()
                }
                EMMessage.Status.SUCCESS->{
                    sendMessageProgress.visibility=View.GONE
                }
                EMMessage.Status.FAIL->{
                    sendMessageProgress.visibility=View.VISIBLE
                    sendMessageProgress.setImageResource(R.mipmap.msg_error)
                }
            }
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_send_message_item,this)
    }
}