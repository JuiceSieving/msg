package cn.tmc.msg.presenter

import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.tmc.msg.contract.RegisterContract
import cn.tmc.msg.extension.isValidPassword
import cn.tmc.msg.extension.isValidUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
class RegisterPresenter(val view: RegisterContract.View): RegisterContract.Presenter {
    override fun checkRegister(userName: String, password: String, confirmPassword: String) {
        if(userName.isValidUserName()){
            if(password.isValidPassword()){
                if(confirmPassword == password){
                    view.onStartRegister()
                    registerBmob(userName,password)
                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val bmobUser = BmobUser()
        bmobUser.username=userName
        bmobUser.setPassword(password)
        bmobUser.signUp(object : SaveListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                if(p1==null){
                    registerEaseMob(userName,password)
                }else{
                    if(p1.errorCode == 202){
                        view.onUserAlreadyExist()
                    }else runOnMainThread { view.onRegisterFail() }
                }
            }
        })
    }

    private fun registerEaseMob(userName: String, password: String) {
        doAsync {
            try {
                EMClient.getInstance().createAccount(userName,password)
                uiThread { view.onRegisterSuccess() }
            }catch (e: HyphenateException){
                uiThread { view.onRegisterFail() }
            }
        }
    }
}