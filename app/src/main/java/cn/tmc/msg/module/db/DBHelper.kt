package cn.tmc.msg.module.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import cn.tmc.msg.MsgApplication
import org.jetbrains.anko.db.*

/**
 * Email: 76534779@qq.com
 * created by nbb on 2018/10/24 14
 * version: 1.0
 * description
 */
class DBHelper(ctx: Context=MsgApplication.appContext) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME="CONTACT.db"
        val DB_VERSION=1
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TblContact.TABLE_NAME,true,TblContact.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,TblContact.USER_NAME to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(TblContact.TABLE_NAME,true)
        onCreate(db)
    }
}