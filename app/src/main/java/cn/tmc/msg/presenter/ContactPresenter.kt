package cn.tmc.msg.presenter

import cn.tmc.msg.contract.ContactContract
import cn.tmc.msg.module.ContactBean
import cn.tmc.msg.module.db.Contact
import cn.tmc.msg.module.db.ContactDatabase
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 12
 * version: 1.0
 * description
 */
class ContactPresenter(val view: ContactContract.View): ContactContract.Presenter {
    val list= mutableListOf<ContactBean>()
    override fun loadContact() {
        doAsync {
            list.clear()
            try {
                ContactDatabase.contactDatabase.deleteAllContact()
                val contact = EMClient.getInstance().contactManager().allContactsFromServer
                contact.sort()
                contact.forEachIndexed { index, s ->
                    val isShowLetter = index==0 || s[0]!=contact[index-1][0]
                    val contactBean = ContactBean(s, s[0].toUpperCase(),isShowLetter)
                    list.add(contactBean)

                    val user = Contact(mutableMapOf("name" to s))
                    ContactDatabase.contactDatabase.saveContact(user)
                }
                uiThread { view.onLoadContactSuccess() }
            }catch (e: HyphenateException){
                uiThread { view.onLoadContactFail() }
            }
        }
    }
}