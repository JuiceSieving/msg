package cn.tmc.msg.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.tmc.msg.R
import cn.tmc.msg.ui.activity.ChatActivity
import cn.tmc.msg.module.ContactBean
import cn.tmc.msg.widget.ContactItemView
import com.hyphenate.chat.EMClient
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class ContactAdapter(private val context: Context, private val list: MutableList<ContactBean>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return ContactHolder(ContactItemView(context))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder.itemView as ContactItemView
        itemView.bindView(list[position])
        val username=list[position].userName
        itemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to username)
        }
        itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.delete_friend_title))
                    .setMessage(String.format(context.getString(R.string.delete_friend_message),username))
                    .setPositiveButton(context.getString(R.string.confirm)) { dialog, which ->
                        deleteFriend(username)
                    }
                    .setNegativeButton(context.getString(R.string.cancel),null)
                    .show()
            true
        }
    }

    private fun deleteFriend(username: String) {
        EMClient.getInstance().contactManager().aysncDeleteContact(username,object : EMCallbackAdapter(){
            override fun onError(p0: Int, p1: String?) {
                context.runOnUiThread { toast(getString(R.string.delete_friend_failed)) }
            }

            override fun onSuccess() {
                context.runOnUiThread { toast(getString(R.string.delete_friend_success)) }
            }
        })
    }

    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}