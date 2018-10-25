package cn.tmc.msg.contract

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
interface AddFriendContract {
    interface Presenter:BasePresenter{
        fun search(key:String)
    }

    interface View{
        fun onSearchSuccess()
        fun onSearchFail()
    }
}