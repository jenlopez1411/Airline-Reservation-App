package com.example.project2aireline.AirelineDatabase.TypeConverters;

import androidx.room.TypeConverter;

import java.util.Date;


public class DataTypeConverter {
    @TypeConverter
    public long ConvertDateToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date ConvertLongtoDate(Long time){
        return new Date(time);
    }
}
