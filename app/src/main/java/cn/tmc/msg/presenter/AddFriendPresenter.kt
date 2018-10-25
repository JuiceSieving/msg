package cn.tmc.msg.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.tmc.msg.contract.AddFriendContract
import cn.tmc.msg.module.FriendBean
import cn.tmc.msg.module.db.ContactDatabase
import com.hyphenate.chat.EMClient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
class AddFriendPresenter(val view:AddFriendContract.View):AddFriendContract.Presenter {
    val list = mutableListOf<FriendBean>()
    override fun search(key: String) {
        val bmobQuery = BmobQuery<BmobUser>()
        bmobQuery.addWhereContains("username",key)
                .addWhereNotEqualTo("username",EMClient.getInstance().currentUser)
        bmobQuery.findObjects(object : FindListener<BmobUser>() {
            override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                if(p1==null){
                    doAsync {
                        list.clear()
                        val allContact = ContactDatabase.contactDatabase.getAllContact()
                        p0?.forEach {
                            var isAdd=false
                            for (contact in allContact){
                                if(contact.name == it.username){
                                    isAdd = true
                                }
                            }
                            val friendBean = FriendBean(it.username, it.createdAt, isAdd)
                            list.add(friendBean)
                        }
                        uiThread { view.onSearchSuccess() }
                    }
                }else runOnMainThread { view.onSearchFail() }
            }
        })
    }
}