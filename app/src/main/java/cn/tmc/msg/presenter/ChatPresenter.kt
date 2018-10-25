package cn.tmc.msg.presenter

import cn.tmc.msg.adapter.EMCallbackAdapter
import cn.tmc.msg.contract.ChatContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 18
 * version: 1.0
 * description
 */
class ChatPresenter(val view:ChatContract.View):ChatContract.Presenter {
    companion object {
        val MSG_SIZE=10
    }
    override fun loadMoreMessage(userName: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(userName)
            val msgId = list[0].msgId
            val messages = conversation.loadMoreMsgFromDB(msgId, MSG_SIZE)
            list.addAll(0,messages)
            uiThread { view.onMoreMessageLoad(messages.size) }
        }
    }

    override fun loadMessage(userName: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(userName)
            conversation.markAllMessagesAsRead()
            list.addAll(conversation.allMessages)
            uiThread { view.onMessageLoad() }
        }
    }

    override fun addMessage(userName: String, p0: MutableList<EMMessage>?) {
        p0?.let { list.addAll(it) }
        val conversation = EMClient.getInstance().chatManager().getConversation(userName)
        conversation.markAllMessagesAsRead()
    }

    val list= mutableListOf<EMMessage>()
    override fun sendMessage(userName: String, msg: String) {
        val message = EMMessage.createTxtSendMessage(msg, userName)
        message.setMessageStatusCallback(object : EMCallbackAdapter(){
            override fun onError(p0: Int, p1: String?) {
                runOnMainThread { view.onSendMsgFail() }
            }

            override fun onSuccess() {
                runOnMainThread { view.onSendMsgSuccess() }
            }
        })
        list.add(message)
        view.onStartSendMsg()
        EMClient.getInstance().chatManager().sendMessage(message)
    }
}