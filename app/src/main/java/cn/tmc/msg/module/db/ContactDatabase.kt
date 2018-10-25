package cn.tmc.msg.module.db

import cn.tmc.msg.extension.toVarargArray
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 15
 * version: 1.0
 * description
 */
class ContactDatabase {
    companion object {
        val dbHelper=DBHelper()
        val contactDatabase=ContactDatabase()
    }

    fun saveContact(contact:Contact){
        dbHelper.use {
            insert(TblContact.TABLE_NAME,*contact.map.toVarargArray())
        }
    }

    fun getAllContact():List<Contact>{
        return dbHelper.use {
            select(TblContact.TABLE_NAME).parseList(object :MapRowParser<Contact>{
                override fun parseRow(columns: Map<String, Any?>): Contact=Contact(columns.toMutableMap())
            })
        }
    }

    fun deleteAllContact(){
        dbHelper.use {
            delete(TblContact.TABLE_NAME,null,null)
        }
    }
}