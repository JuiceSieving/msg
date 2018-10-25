package cn.tmc.msg.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.tmc.msg.R
import cn.tmc.msg.adapter.ConversationAdapter
import cn.tmc.msg.adapter.EMMessageAdapter
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.fragment_conversation.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class ConversationFragment: BaseFragment() {
    val list= mutableListOf<EMConversation>()
    val messageListener= object : EMMessageAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            loadConversation()
        }
    }
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_conversation,null)
    }

    override fun init() {
        headerTitle.text=getString(R.string.message)
        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter= ConversationAdapter(context,list)
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    override fun onResume() {
        super.onResume()
        loadConversation()
    }

    private fun loadConversation(){
        doAsync {
            list.clear()
            val allConversation=EMClient.getInstance().chatManager().allConversations
            list.addAll(allConversation.values)
            uiThread { recyclerView.adapter?.notifyDataSetChanged() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

}