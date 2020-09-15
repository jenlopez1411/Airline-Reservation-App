package com.example.project2aireline.AirelineDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;
import com.example.project2aireline.Models.Reservation;
import com.example.project2aireline.Models.CANCELRESERVATION;

import java.util.List;

//should be called aireline not Createaccount sorry this is my first time using room database
@Dao
public interface CreateAccountDao {
    //CreateAccount
    @Insert
    void insert(CreateAccount... Accounts);

    @Update
    void update(CreateAccount... createAccounts);

    @Delete
    void delete(CreateAccount createAccount);

    @Query("SELECT * FROM CREATEACCOUNT WHERE mUsername= :username and mPassword= :password")
    CreateAccount getAccount(String username, String password);

    @Query(" SELECT * FROM CREATEACCOUNT ORDER BY mdate DESC")
    List<CreateAccount> getAllUsers();

    @Query(" SELECT * FROM CREATEACCOUNT ")
    List<CreateAccount> getUsers();

    @Query(" SELECT * FROM CreateAccount WHERE mUsername= :username")
    CreateAccount getUserByUsername(String username);

    //Flight
    @Insert
    void insert(Flight... flights);

    @Update
    void update(Flight... flights);

    @Delete
    void delete(Flight flight);

    @Query("DELETE FROM FLIGHT")
    void deleteALL();

    @Query(" SELECT * FROM  FLIGHT ORDER BY mDate DESC ")
    List<Flight> getAllFlights();

    //reservation
    @Insert
    void insert(Reservation... reservations);

    @Update
    void update(Reservation... reservations);

    @Delete
    void delete(Reservation reservation);

    @Query(" SELECT * FROM RESERVATION ORDER BY mDate DESC")
    List<Reservation> getAllReservation();

    @Query(" SELECT * FROM RESERVATION ")
    List<Reservation> getReservations();

    //Cancellation
    @Insert
    void insert(CANCELRESERVATION... cancelreservations);

    @Update
    void update(CANCELRESERVATION... cancelreservations);

    @Delete
    void delete(CANCELRESERVATION cancelreservation);

    @Query(" SELECT * FROM CANCELRESERVATION ")
    List<CANCELRESERVATION> getcancelreservations();

}
