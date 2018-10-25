package cn.tmc.msg.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.tmc.msg.R
import cn.tmc.msg.adapter.ChatAdapter
import cn.tmc.msg.adapter.EMMessageAdapter
import cn.tmc.msg.contract.ChatContract
import cn.tmc.msg.presenter.ChatPresenter
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
class ChatActivity:BaseActivity(),ChatContract.View {
    override fun onMoreMessageLoad(size: Int) {
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scrollToPosition(size)
    }

    override fun onMessageLoad() {
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scrollToPosition(presenter.list.size-1)
    }

    var userName=""
    val presenter by lazy { ChatPresenter(this) }
    private val messageListener= object : EMMessageAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(userName,p0)
            runOnUiThread {
                recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.scrollToPosition(presenter.list.size-1)
            }
        }
    }
    override fun onStartSendMsg() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onSendMsgSuccess() {
        toast(getString(R.string.send_message_success))
        recyclerView.adapter?.notifyDataSetChanged()
        edit.text.clear()
    }

    override fun onSendMsgFail() {
        toast(getString(R.string.send_message_failed))
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun init() {
        userName = intent.getStringExtra("username")
        headerTitle.text=String.format(getString(R.string.chat_title),userName)
        back.visibility= View.VISIBLE

        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=ChatAdapter(context,presenter.list)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState==RecyclerView.SCROLL_STATE_IDLE){
                        val manager = layoutManager
                        if(manager is LinearLayoutManager){
                            val position = manager.findFirstVisibleItemPosition()
                            if(position<=0){
                                presenter.loadMoreMessage(userName)
                            }
                        }
                    }
                }
            })
        }

        presenter.loadMessage(userName)
        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                send.isEnabled = !s.isNullOrEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        back.setOnClickListener { finish() }
        send.setOnClickListener { sendMsg() }
        edit.setOnEditorActionListener { v, actionId, event ->
            sendMsg()
            true
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun sendMsg() {
        hideSofeInput()
        val text = edit.text.trim().toString()
        presenter.sendMessage(userName,text)
        recyclerView.scrollToPosition(presenter.list.size-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

    override fun getLayoutResId()= R.layout.activity_chat
}