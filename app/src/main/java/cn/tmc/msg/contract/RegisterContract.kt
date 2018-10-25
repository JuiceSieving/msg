package cn.tmc.msg.contract

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
interface RegisterContract {
    interface Presenter: BasePresenter {
        fun checkRegister(userName:String,password:String,confirmPassword:String)
    }

    interface View{
        fun onUserNameError()
        fun onPasswordError()
        fun onConfirmPasswordError()
        fun onStartRegister()
        fun onRegisterSuccess()
        fun onRegisterFail()
        fun onUserAlreadyExist()
    }
}