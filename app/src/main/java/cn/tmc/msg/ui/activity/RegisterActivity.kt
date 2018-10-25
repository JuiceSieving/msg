package cn.tmc.msg.ui.activity

import cn.tmc.msg.R
import cn.tmc.msg.contract.RegisterContract
import cn.tmc.msg.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
class RegisterActivity: BaseActivity(), RegisterContract.View {
    private val presenter by lazy { RegisterPresenter(this) }
    override fun onUserAlreadyExist() {
        hideProgressDialog()
        toast(getString(R.string.user_already_exist))
    }

    override fun onUserNameError() {
        userName.error=getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error=getString(R.string.password_error)
    }

    override fun onConfirmPasswordError() {
        confirmPassword.error=getString(R.string.confirm_password_error)
    }

    override fun onStartRegister() {
        showProgressDialog(getString(R.string.registering))
    }

    override fun onRegisterSuccess() {
        hideProgressDialog()
        toast(getString(R.string.register_success))
        finish()
    }

    override fun onRegisterFail() {
        hideProgressDialog()
        toast(getString(R.string.register_failed))
    }

    override fun init() {
        register.setOnClickListener { register() }
        confirmPassword.setOnEditorActionListener { v, actionId, event ->
            register()
            true
        }
    }

    private fun register(){
        hideSofeInput()
        val userName = userName.text.trim().toString()
        val password = password.text.toString()
        val confirmPassword = confirmPassword.text.toString()
        presenter.checkRegister(userName,password,confirmPassword)
    }

    override fun getLayoutResId()= R.layout.activity_register
}