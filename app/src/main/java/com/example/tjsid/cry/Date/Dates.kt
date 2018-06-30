package com.example.tjsid.cry.Date

import android.content.Context
import java.util.*

class Dates private constructor(context: Context) {

    companion object {
        fun getInstance(context: Context): Dates {
            if (INSTANCE == null) {
                INSTANCE = Dates(context)
            }
            return INSTANCE as Dates
        }

        private var INSTANCE: Dates? = null
    }

    fun getAllDate(): String {
        var date: Calendar = GregorianCalendar()
        return date.time.toString()
    }

    fun getHour(): String {
        var hour: Calendar = Calendar.getInstance()
        if (hour.get(Calendar.HOUR_OF_DAY) <= 9) {
            when (hour.get(Calendar.HOUR_OF_DAY)) {
                1 -> return "01"
                2 -> return "02"
                3 -> return "03"
                4 -> return "04"
                5 -> return "05"
                6 -> return "06"
                7 -> return "07"
                8 -> return "08"
                9 -> return "09"
                0 -> return "00"
                else -> return ""
            }
        } else {
            return hour.get(Calendar.HOUR_OF_DAY).toString()
        }
    }

    fun getMinute(): String {
        var hour: Calendar = Calendar.getInstance()
        if (hour.get(Calendar.MINUTE) <= 9) {
            when (hour.get(Calendar.MINUTE)) {
                1 -> return "01"
                2 -> return "02"
                3 -> return "03"
                4 -> return "04"
                5 -> return "05"
                6 -> return "06"
                7 -> return "07"
                8 -> return "08"
                9 -> return "09"
                0 -> return "00"
                else -> return ""
            }
        } else {
            return hour.get(Calendar.MINUTE).toString()
        }
    }

    fun getSecond(): String {
        var hour: Calendar = Calendar.getInstance()
        var segundo = ""
        if (hour.get(Calendar.SECOND) <= 9) {
            when (hour.get(Calendar.SECOND)) {
                1 -> segundo = "01"
                2 -> segundo = "02"
                3 -> segundo = "03"
                4 -> segundo = "04"
                5 -> segundo = "05"
                6 -> segundo = "06"
                7 -> segundo = "07"
                8 -> segundo = "08"
                9 -> segundo = "09"
                0 -> segundo = "00"
                else -> segundo = ""
            }
        } else {
            segundo = hour.get(Calendar.SECOND).toString()
        }
        return segundo
    }

    fun getAllHour(): String {
        var allHour: String = getHour() + ":" + getMinute() + ":" + getSecond()
        return allHour
    }

    fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
    }

}