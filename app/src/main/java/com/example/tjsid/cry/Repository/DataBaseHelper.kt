package com.example.tjsid.cry.Repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tjsid.cry.Constants.ValuesConstants

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME: String = "last_prices.db"
        private val DATABASE_VERSION: Int = 1
    }

    private val createTableUser = """
         CREATE TABLE ${ValuesConstants.VALUE.TABLE_NAME} (
            ${ValuesConstants.VALUE.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ValuesConstants.VALUE.LAST_PRICE} TEXT,
            ${ValuesConstants.VALUE.HOUR} TEXT,
            ${ValuesConstants.VALUE.DATE} TEXT,
            ${ValuesConstants.VALUE.TYPE} TEXT
         );
    """

    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}