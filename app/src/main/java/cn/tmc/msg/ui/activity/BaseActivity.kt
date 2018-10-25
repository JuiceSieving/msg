package cn.tmc.msg.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
abstract class BaseActivity: AppCompatActivity() {
    private val progressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    fun showProgressDialog(msg:String){
        progressDialog.setMessage(msg)
        progressDialog.show()
    }

    fun hideProgressDialog(){
        progressDialog.dismiss()
    }

    fun hideSofeInput(){
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
    }

    abstract fun init()
    abstract fun getLayoutResId(): Int
}