package cn.tmc.msg.module.db

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 15
 * version: 1.0
 * description
 */
data class Contact(val map:MutableMap<String,Any?>) {
    val _id by map
    val name by map
}