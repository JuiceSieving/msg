package cn.tmc.msg.contract

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
interface SplashContract {
    interface Presenter: BasePresenter {
        fun checkLoginStatus()
    }

    interface View{
        fun onLogin()
        fun onNotLogin()
    }
}