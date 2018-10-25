package cn.tmc.msg.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.tmc.msg.widget.ReceiveMsgItemView
import cn.tmc.msg.widget.SendMsgItemView
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 21
 * version: 1.0
 * description
 */
class ChatAdapter(val context: Context,val list: MutableList<EMMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val MSG_SEND=0
        val MSG_RECEIVE=1
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].direct()==EMMessage.Direct.SEND){
            MSG_SEND
        }else MSG_RECEIVE
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return if(type== MSG_SEND){
            SendHolder(SendMsgItemView(context))
        }else{
            ReceiveHolder(ReceiveMsgItemView(context))
        }
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val showTime=isShowTime(position)
        if(getItemViewType(position)== MSG_SEND){
            val itemView = holder.itemView as SendMsgItemView
            itemView.bindView(list[position],showTime)
        }else{
            val itemView = holder.itemView as ReceiveMsgItemView
            itemView.bindView(list[position],showTime)
        }
    }

    private fun isShowTime(position: Int): Boolean {
        var showTime=true
        if(position>0){
            showTime=!DateUtils.isCloseEnough(list[position].msgTime,list[position-1].msgTime)
        }
        return showTime
    }

    class SendHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ReceiveHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}