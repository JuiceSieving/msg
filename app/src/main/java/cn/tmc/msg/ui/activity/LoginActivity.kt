package cn.tmc.msg.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import cn.tmc.msg.R
import cn.tmc.msg.contract.LoginContract
import cn.tmc.msg.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
class LoginActivity: BaseActivity(), LoginContract.View {
    private val presenter by lazy { LoginPresenter(this) }
    override fun onUserNameError() {
        userName.error=getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error=getString(R.string.password_error)
    }

    override fun onStartLogin() {
        showProgressDialog(getString(R.string.logging))
    }

    override fun onLoginSuccess() {
        hideProgressDialog()
        startActivity<MainActivity>()
        finish()
    }

    override fun onLoginFail() {
        hideProgressDialog()
        toast(R.string.login_failed)
    }

    override fun init() {
        newUser.setOnClickListener { startActivity<RegisterActivity>() }
        login.setOnClickListener { login() }
        password.setOnEditorActionListener { v, actionId, event ->
            login()
            true
        }
    }

    private fun login(){
        hideSofeInput()
        if(hasWritePermission()) {
            val username = userName.text.trim().toString()
            val password = password.text.toString()
            presenter.checkLogin(username, password)
        }else requestWritePermission()
    }

    private fun requestWritePermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this,permissions,0)
    }

    private fun hasWritePermission(): Boolean {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val checkSelfPermission = ActivityCompat.checkSelfPermission(this, permission)
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            login()
        }else toast(getString(R.string.permission_denied))
    }

    override fun getLayoutResId()= R.layout.activity_login
}