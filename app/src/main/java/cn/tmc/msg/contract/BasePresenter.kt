package cn.tmc.msg.contract

import android.os.Handler
import android.os.Looper

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 09
 * version: 1.0
 * description
 */
interface BasePresenter {
    companion object {
        val handler by lazy { Handler(Looper.getMainLooper()) }
    }

    fun runOnMainThread(f:() -> Unit){
        handler.post { f() }
    }
}