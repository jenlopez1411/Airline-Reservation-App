package com.example.project2aireline.AirelineDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2aireline.AirelineDatabase.TypeConverters.DataTypeConverter;
import com.example.project2aireline.Models.CANCELRESERVATION;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;
import com.example.project2aireline.Models.Reservation;

//this should be called airelinedatabase sorry this is my first time doing room database

@Database(entities = {CreateAccount.class, Flight.class, Reservation.class, CANCELRESERVATION.class}, version =  1)
@TypeConverters(DataTypeConverter.class)
public abstract class CreateAccountDatabase extends RoomDatabase {
    public static final String DB_NAME = "Flight_DATABASE";
    public static final String CREATE_ACCOUNT_TABLE = "CREATE_ACCOUNT_TABLE";
    public static final String FLIGHT_TABLE = "FLIGHT_TABLE";
    private static CreateAccountDatabase object;

    public abstract CreateAccountDao getCreateAccountDao();

    public CreateAccountDatabase createAccountDatabase(final Context context) {
        if(object == null){
            object = Room.databaseBuilder(context, CreateAccountDatabase.class, DB_NAME).build();

        }
        return object;
    }
}
