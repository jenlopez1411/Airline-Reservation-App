package com.example.project2aireline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;

import java.util.List;
//Name: Jennifer S Lopez
//Date: 5/9/2020
//Title: Project 2 CST 338
//Description: This file contains room database to create a airline app that takes,
//accounts, reservations, flight information, and cancel reservations.
public class MainMenu extends AppCompatActivity {
    private CreateAccountDao mCreateDao;
    List<CreateAccount> users;
    private Button mManageSystemBtn;
    private Button mReserveSeatBtn;
    private Button mCancelReservation;
    List<Flight> flights;
    CreateAccountDao mFlightDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getDatabase();
        Button mbCreateAccount = findViewById(R.id.bCreateAccount);
        mManageSystemBtn = findViewById(R.id.ManageSystembutton);
        users = mCreateDao.getAllUsers();
        mReserveSeatBtn = findViewById(R.id.reserveseatbtn);
        mCancelReservation = findViewById(R.id.cancelReservation);
        if(users.size() <= 0){
            CreateAccount defaultUser = new CreateAccount("alice5", "csumb100");
            CreateAccount defaultUser2 = new CreateAccount("brian77", "123ABC");
            CreateAccount defaultUser3 = new CreateAccount("chris21", "CHRIS21");
            CreateAccount defaultUser4 = new CreateAccount("sophia123", "SOPHIA123");
            mCreateDao.insert(defaultUser);
            mCreateDao.insert(defaultUser2);
            mCreateDao.insert(defaultUser3);
            mCreateDao.insert(defaultUser4);

        }

        flights = mFlightDao.getAllFlights();
        if(flights.size() <= 0){
            Log.i("flights", "pass");

            Flight flight1 = new Flight("Otter101", "Monterey", "Los Angeles", "10:00(AM)", 10, 150.00);
            Flight flight2 = new Flight("Otter102", "Los Angeles", "Monterey", "1:00(PM)", 10, 150.00);
            Flight flight3 = new Flight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.50);
            Flight flight4 = new Flight("Otter205", "Monterey", "Seattle", "3:00(PM)", 15, 150.00);
            Flight flight5 = new Flight("Otter202", "Seattle", "Monterey", "2:00(PM)", 5, 200.50);
            mFlightDao.insert(flight1);
            mFlightDao.insert(flight2);
            mFlightDao.insert(flight3);
            mFlightDao.insert(flight4);
            mFlightDao.insert(flight5);
        }
        mbCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AccountAct = new Intent(MainMenu.this, LoginActivity.class);
                AccountAct.putExtra("zero", "0");
                startActivity(AccountAct);
            }
        });

        mManageSystemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ManageSystem = new Intent(MainMenu.this, AdminLogin.class);
                startActivity(ManageSystem);
            }
        });

        mReserveSeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reserveActivity = new Intent(MainMenu.this, ReserveSeatActivity.class);
                startActivity(reserveActivity);
            }
        });
        mCancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelReservation = new Intent(MainMenu.this, CancelReservationLogin.class);
                startActivity(cancelReservation);
            }
        });
    }

    public void getDatabase(){
        mCreateDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        mFlightDao  = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }

}
