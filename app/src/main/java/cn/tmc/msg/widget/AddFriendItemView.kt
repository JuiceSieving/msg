package cn.tmc.msg.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.tmc.msg.R
import cn.tmc.msg.adapter.EMCallbackAdapter
import cn.tmc.msg.module.FriendBean
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
class AddFriendItemView:RelativeLayout {
    fun bindView(friendBean: FriendBean) {
        if(friendBean.isAdd){
            add.text=context.getString(R.string.already_added)
            add.isEnabled=false
        }else{
            add.text=context.getString(R.string.add)
            add.isEnabled=true
        }
        userName.text = friendBean.userName
        timestamp.text = friendBean.createTime
        add.setOnClickListener { addFriend(friendBean.userName) }
    }

    private fun addFriend(userName: String) {
        EMClient.getInstance().contactManager().aysncAddContact(userName,null,object: EMCallbackAdapter(){
            override fun onError(p0: Int, p1: String?) {
                context.runOnUiThread { toast(getString(R.string.send_add_friend_failed)) }
            }

            override fun onSuccess() {
                context.runOnUiThread { toast(getString(R.string.send_add_friend_success)) }
            }
        })
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.view_add_friend_item,this)
    }
}