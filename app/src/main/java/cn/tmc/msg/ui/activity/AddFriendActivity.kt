package cn.tmc.msg.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.tmc.msg.R
import cn.tmc.msg.adapter.AddFriendAdapter
import cn.tmc.msg.contract.AddFriendContract
import cn.tmc.msg.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 13
 * version: 1.0
 * description
 */
class AddFriendActivity:BaseActivity(),AddFriendContract.View {
    private val presenter by lazy { AddFriendPresenter(this) }
    override fun onSearchSuccess() {
        hideProgressDialog()
        toast(R.string.search_success)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onSearchFail() {
        hideProgressDialog()
        toast(R.string.search_failed)
    }

    override fun init() {
        headerTitle.text = getString(R.string.add_friend)
        back.visibility = View.VISIBLE
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendAdapter(context,presenter.list)
        }

        back.setOnClickListener { finish() }
        search.setOnClickListener { search() }
        userName.setOnEditorActionListener { v, actionId, event ->
            search()
            true
        }
    }

    private fun search(){
        hideSofeInput()
        showProgressDialog(getString(R.string.searching))
        val key = userName.text.trim().toString()
        presenter.search(key)
    }

    override fun getLayoutResId()= R.layout.activity_add_friend
}