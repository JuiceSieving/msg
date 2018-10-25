package cn.tmc.msg.contract

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 11
 * version: 1.0
 * description
 */
interface ContactContract {
    interface Presenter: BasePresenter {
        fun loadContact()
    }

    interface View{
        fun onLoadContactSuccess()
        fun onLoadContactFail()
    }
}