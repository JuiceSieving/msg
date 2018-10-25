package cn.tmc.msg.presenter

import cn.tmc.msg.contract.SplashContract
import com.hyphenate.chat.EMClient

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
class SplashPresenter(val view: SplashContract.View): SplashContract.Presenter {
    override fun checkLoginStatus() {
        if(getLoginStatus()){
            view.onLogin()
        }else view.onNotLogin()
    }

    private fun getLoginStatus(): Boolean {
        return EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
    }
}