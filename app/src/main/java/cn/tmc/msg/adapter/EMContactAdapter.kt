package cn.tmc.msg.adapter

import com.hyphenate.EMContactListener

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
open class EMContactAdapter:EMContactListener {
    override fun onContactInvited(p0: String?, p1: String?) {}

    override fun onContactDeleted(p0: String?) {}

    override fun onFriendRequestAccepted(p0: String?) {}

    override fun onContactAdded(p0: String?) {}

    override fun onFriendRequestDeclined(p0: String?) {}
}