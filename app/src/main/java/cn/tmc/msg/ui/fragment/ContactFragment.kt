package cn.tmc.msg.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.tmc.msg.R
import cn.tmc.msg.adapter.ContactAdapter
import cn.tmc.msg.adapter.EMContactAdapter
import cn.tmc.msg.contract.ContactContract
import cn.tmc.msg.presenter.ContactPresenter
import cn.tmc.msg.ui.activity.AddFriendActivity
import cn.tmc.msg.widget.Slidebar
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
class ContactFragment: BaseFragment(), ContactContract.View {
    private val presenter by lazy { ContactPresenter(this) }
    private val contactListener =object :EMContactAdapter(){
        override fun onContactDeleted(p0: String?) {
            presenter.loadContact()
        }

        override fun onContactAdded(p0: String?) {
            presenter.loadContact()
        }
    }
    override fun onLoadContactSuccess() {
        swipeRefreshLayout.isRefreshing=false
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onLoadContactFail() {
        swipeRefreshLayout.isRefreshing=false
        toast(getString(R.string.load_contacts_failed))
    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_contacts,null)
    }

    override fun init() {
        headerTitle.text=getString(R.string.contact)
        add.visibility=View.VISIBLE
        swipeRefreshLayout.apply {
            isRefreshing=true
            setColorSchemeResources(R.color.qq_blue)
            setOnRefreshListener { presenter.loadContact() }
        }
        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter= ContactAdapter(context,presenter.list)
        }
        presenter.loadContact()

        //widget listener
        slideBar.onSectionChangeListener = object : Slidebar.OnSectionChangeListener{
            override fun onSelectSection(letter: String) {
                section.visibility=View.VISIBLE
                section.text = letter
                recyclerView.smoothScrollToPosition(getPositionByLetter(letter))
            }

            override fun onSlideDone() {
                section.visibility=View.GONE
            }
        }
        add.setOnClickListener {
            startActivity<AddFriendActivity>()
        }

        EMClient.getInstance().contactManager().setContactListener(contactListener)
    }

    private fun getPositionByLetter(letter: String): Int {
        var position= presenter.list.binarySearch { it.firstLetter.minus(letter[0]) }
        if(position>presenter.list.size-1){
            position=presenter.list.size-1
        }else if (position<0){
            position=0
        }
        return position
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().contactManager().removeContactListener(contactListener)
    }
}