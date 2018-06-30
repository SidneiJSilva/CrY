package com.example.tjsid.cry.Repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.example.tjsid.cry.Constants.ValuesConstants
import com.example.tjsid.cry.Entities.ValueEntity
import java.security.AccessController.getContext

class ValueRepository private constructor(context: Context) {

    private var mDataBaseHelper: DataBaseHelper = DataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): ValueRepository {
            if (INSTANCE == null) {
                INSTANCE = ValueRepository(context)
            }
            return INSTANCE as ValueRepository
        }

        private var INSTANCE: ValueRepository? = null
    }

    fun get(index: Int, type: String): ArrayList<String> {

        var valueEntity: ValueEntity? = null
        var dados: ArrayList<String> = ArrayList()

        try {

            val cursor: Cursor

            val db = mDataBaseHelper.readableDatabase

            var sql = "select * from ${ValuesConstants.VALUE.TABLE_NAME} " +
                    "where ${ValuesConstants.VALUE.TYPE} = '$type' " +
                    "order by ${ValuesConstants.VALUE.ID} desc limit $index"

            cursor = db.rawQuery(sql, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (cursor.count > 0) {
//                    val valueID = cursor.getInt(cursor.getColumnIndex(ValuesConstants.VALUE.ID))
                    val valueLastPrice = cursor.getString(cursor.getColumnIndex(ValuesConstants.VALUE.LAST_PRICE))
//                    val valueHour = cursor.getString(cursor.getColumnIndex(ValuesConstants.VALUE.HOUR))
                    val valueDate = cursor.getString(cursor.getColumnIndex(ValuesConstants.VALUE.DATE))
                    dados.add(valueLastPrice)
                    cursor.moveToNext()
                }
            }else{
                dados.add("0,00")
            }

            cursor.close()

        } catch (e: Exception) {
            return dados
        }
        return dados
    }

    fun insert(lastPrice: String, hour: String, date: String, type: String) {

        val db = mDataBaseHelper.writableDatabase
        val insertValues = ContentValues()
        insertValues.put(ValuesConstants.VALUE.LAST_PRICE, lastPrice)
        insertValues.put(ValuesConstants.VALUE.HOUR, hour)
        insertValues.put(ValuesConstants.VALUE.DATE, date)
        insertValues.put(ValuesConstants.VALUE.TYPE, type)

        db.insert(ValuesConstants.VALUE.TABLE_NAME, null, insertValues)

    }

}