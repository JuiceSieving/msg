package cn.tmc.msg.presenter

import cn.tmc.msg.adapter.EMCallbackAdapter
import cn.tmc.msg.contract.LoginContract
import cn.tmc.msg.extension.isValidPassword
import cn.tmc.msg.extension.isValidUserName
import com.hyphenate.chat.EMClient

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {
    override fun checkLogin(userName: String, password: String) {
        if(userName.isValidUserName()){
            if(password.isValidPassword()){
                view.onStartLogin()
                loginEaseMob(userName,password)
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun loginEaseMob(userName: String, password: String) {
        EMClient.getInstance().login(userName,password,object : EMCallbackAdapter(){
            override fun onError(p0: Int, p1: String?) {
                runOnMainThread { view.onLoginFail() }
            }

            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                runOnMainThread { view.onLoginSuccess() }
            }
        })
    }
}