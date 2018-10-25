package cn.tmc.msg.extension

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 10
 * version: 1.0
 * description
 */
fun String.isValidUserName():Boolean = this.matches(Regex("^[a-zA-Z]\\w{2,19}$"))
fun String.isValidPassword():Boolean = this.matches(Regex("^\\d{3,20}$"))

fun<K,V> MutableMap<K,V>.toVarargArray():Array<Pair<K,V>>{
    return map {
        Pair(it.key,it.value)
    }.toTypedArray()
}