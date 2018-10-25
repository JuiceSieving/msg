package cn.tmc.msg.ui.activity

import android.os.Handler
import cn.tmc.msg.R
import cn.tmc.msg.contract.SplashContract
import cn.tmc.msg.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
class SplashActivity: BaseActivity(), SplashContract.View {
    private val handler by lazy { Handler() }
    private val DELAY_TIME= 1000L
    override fun onLogin() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onNotLogin() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        },DELAY_TIME)
    }

    override fun init() {
        val presenter = SplashPresenter(this)
        presenter.checkLoginStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun getLayoutResId()= R.layout.activity_splash
}