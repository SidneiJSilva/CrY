package com.example.tjsid.cry.Date

import java.util.*

class Dates {

    val date: Calendar = GregorianCalendar()
    val hour: Calendar = Calendar.getInstance()

    fun getAllDate(): String {
        return date.time.toString()
    }

    fun getHour(): String {
        if(hour.get(Calendar.HOUR_OF_DAY) <= 9) {
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
        }else{
            return hour.get(Calendar.HOUR_OF_DAY).toString()
        }
    }

    fun getMinute(): String {
        if(hour.get(Calendar.MINUTE) <= 9) {
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
        }else{
            return hour.get(Calendar.MINUTE).toString()
        }
    }

    fun getSecond(): String {
        if(hour.get(Calendar.SECOND) <= 9) {
            when (hour.get(Calendar.SECOND)) {
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
        }else{
            return hour.get(Calendar.SECOND).toString()
        }
    }

    fun getAllHour(): String {
        var allHour: String  = getHour()+":"+getMinute()+":"+getSecond()
        return allHour
    }

}