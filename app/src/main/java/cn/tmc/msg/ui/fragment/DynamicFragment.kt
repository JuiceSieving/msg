package cn.tmc.msg.ui.fragment

import android.view.View
import cn.tmc.msg.R
import cn.tmc.msg.ui.activity.LoginActivity
import cn.tmc.msg.adapter.EMCallbackAdapter
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class DynamicFragment: BaseFragment() {
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_dynamic,null)
    }

    override fun init() {
        headerTitle.text=getString(R.string.dynamic)
        logout.text= String.format(getString(R.string.logout),EMClient.getInstance().currentUser)
        logout.setOnClickListener {
            EMClient.getInstance().logout(true,object : EMCallbackAdapter(){
                override fun onError(p0: Int, p1: String?) {
                    runOnUiThread { toast(getString(R.string.login_failed)) }
                }

                override fun onSuccess() {
                    runOnUiThread {
                        toast(getString(R.string.logout_success))
                        startActivity<LoginActivity>()
                        activity?.finish()
                    }
                }
            })
        }
    }
}