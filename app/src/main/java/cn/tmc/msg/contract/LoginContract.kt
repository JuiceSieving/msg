package cn.tmc.msg.contract

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
interface LoginContract {
    interface Presenter: BasePresenter {
        fun checkLogin(userName:String,password:String)
    }

    interface View{
        fun onUserNameError()
        fun onPasswordError()
        fun onStartLogin()
        fun onLoginSuccess()
        fun onLoginFail()
    }
}