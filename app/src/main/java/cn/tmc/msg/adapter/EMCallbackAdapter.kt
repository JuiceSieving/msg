package cn.tmc.msg.adapter

import com.hyphenate.EMCallBack

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
open class EMCallbackAdapter:EMCallBack {
    override fun onSuccess() {}

    override fun onProgress(p0: Int, p1: String?) {}

    override fun onError(p0: Int, p1: String?) {}
}