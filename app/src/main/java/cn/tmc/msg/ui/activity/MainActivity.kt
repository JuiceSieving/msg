package cn.tmc.msg.ui.activity

import cn.tmc.msg.R
import cn.tmc.msg.adapter.EMMessageAdapter
import cn.tmc.msg.util.FragmentFactory
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    val messageListener=object : EMMessageAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            runOnUiThread { updateMsgCount() }
        }
    }
    override fun init() {
        bottomBar.setOnTabSelectListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, FragmentFactory.fragmentFactory.getFragment(it),it.toString()).commit()
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)

        //多设备登录
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener{
            override fun onConnected() {}

            override fun onDisconnected(p0: Int) {
                if(p0==EMError.USER_LOGIN_ANOTHER_DEVICE){
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        })
    }

    private fun updateMsgCount() {
        val tab = bottomBar.getTabWithId(R.id.tab_conversation)
        tab.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

    override fun getLayoutResId()= R.layout.activity_main
}
