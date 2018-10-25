package cn.tmc.msg.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.tmc.msg.module.FriendBean
import cn.tmc.msg.widget.AddFriendItemView

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
class AddFriendAdapter(val context: Context,val list: MutableList<FriendBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return AddFriendHolder(AddFriendItemView(context))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder.itemView as AddFriendItemView
        itemView.bindView(list[position])
    }

    class AddFriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}