package cn.tmc.msg.contract

import com.hyphenate.chat.EMMessage

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 16
 * version: 1.0
 * description
 */
interface ChatContract {
    interface Presenter:BasePresenter{
        fun sendMessage(userName:String,msg:String)
        fun addMessage(userName: String, p0: MutableList<EMMessage>?)
        fun loadMessage(userName: String)
        fun loadMoreMessage(userName: String)
    }

    interface View{
        fun onStartSendMsg()
        fun onSendMsgSuccess()
        fun onSendMsgFail()
        fun onMessageLoad()
        fun onMoreMessageLoad(size: Int)
    }
}