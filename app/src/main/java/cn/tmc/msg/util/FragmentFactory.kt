package cn.tmc.msg.util

import android.support.v4.app.Fragment
import cn.tmc.msg.R
import cn.tmc.msg.ui.fragment.ContactFragment
import cn.tmc.msg.ui.fragment.ConversationFragment
import cn.tmc.msg.ui.fragment.DynamicFragment

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class FragmentFactory private constructor(){
    companion object {
        val fragmentFactory by lazy { FragmentFactory() }
    }

    private val conversationFragment by lazy { ConversationFragment() }
    private val contactFragment by lazy { ContactFragment() }
    private val dynamicFragment by lazy { DynamicFragment() }

    fun getFragment(tabId:Int):Fragment{
        return when(tabId){
            R.id.tab_conversation->conversationFragment
            R.id.tab_contacts->contactFragment
            R.id.tab_dynamic->dynamicFragment
            else->conversationFragment
        }
    }
}