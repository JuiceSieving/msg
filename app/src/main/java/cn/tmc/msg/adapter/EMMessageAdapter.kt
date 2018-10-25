package cn.tmc.msg.adapter

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 21
 * version: 1.0
 * description
 */
open class EMMessageAdapter:EMMessageListener {
    override fun onMessageRecalled(p0: MutableList<EMMessage>?) {}

    override fun onMessageChanged(p0: EMMessage?, p1: Any?) {}

    override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {}

    override fun onMessageReceived(p0: MutableList<EMMessage>?) {}

    override fun onMessageDelivered(p0: MutableList<EMMessage>?) {}

    override fun onMessageRead(p0: MutableList<EMMessage>?) {}
}