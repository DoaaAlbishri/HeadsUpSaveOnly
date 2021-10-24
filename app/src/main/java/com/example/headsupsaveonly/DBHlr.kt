package com.example.headsupsaveonly

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHlr(context: Context? ) : SQLiteOpenHelper(context, "details.db", null, 1) {
    val sql :SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL("create table celebrity(name text, taboo1 text, taboo2 text, taboo3 text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun savedata(s1:String,s2:String,s3:String,s4:String){
        val cv = ContentValues()
        cv.put("name",s1)
        cv.put("taboo1",s2)
        cv.put("taboo2",s3)
        cv.put("taboo3",s4)
        sql.insert("celebrity",null,cv)
    }
}