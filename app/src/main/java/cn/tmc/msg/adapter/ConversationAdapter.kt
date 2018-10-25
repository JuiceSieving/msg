package cn.tmc.msg.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.tmc.msg.ui.activity.ChatActivity
import cn.tmc.msg.widget.ConversationItemView
import com.hyphenate.chat.EMConversation
import org.jetbrains.anko.startActivity

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/25 09
 * version: 1.0
 * description
 */
class ConversationAdapter(val context: Context,val list: MutableList<EMConversation>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ConversationHolder(ConversationItemView(context))
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder.itemView as ConversationItemView
        itemView.bindView(list[position])
        itemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to list[position].conversationId())
        }
    }

    class ConversationHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}