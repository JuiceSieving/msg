package cn.tmc.msg.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.tmc.msg.R
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_conversation_item.view.*
import java.util.*

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/25 09
 * version: 1.0
 * description
 */
class ConversationItemView:RelativeLayout {
    fun bindView(emConversation: EMConversation) {
        userName.text=emConversation.conversationId()
        val message = emConversation.lastMessage
        if(message.type==EMMessage.Type.TXT){
            lastMessage.text=(message.body as EMTextMessageBody).message
        }else{
            lastMessage.text=context.getString(R.string.no_text_message)
        }
        timestamp.text=DateUtils.getTimestampString(Date(emConversation.lastMessage.msgTime))
        if(emConversation.unreadMsgCount>0){
            unreadCount.visibility=View.VISIBLE
            unreadCount.text=emConversation.unreadMsgCount.toString()
        }else unreadCount.visibility=View.GONE
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_conversation_item,this)
    }
}