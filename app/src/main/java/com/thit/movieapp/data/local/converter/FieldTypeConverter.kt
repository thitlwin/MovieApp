package com.thit.movieapp.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class FieldTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    var gson = Gson()
    @TypeConverter
    fun intObjectListToString(someObjects: List<Int>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToIntObjectList(data: String?): List<Int>? {

        if (data == null){
            return Collections.emptyList()
        }
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}