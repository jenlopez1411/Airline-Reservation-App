package com.example.project2aireline;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReserveSeatActivity extends AppCompatActivity {
    EditText mDeparturefield;
    EditText mArrivalField;
    EditText mTicketsField;
    Button mSearchBtn;
    TextView mflightsInfo;
    Button mreserveEnter;
    private String mDeparture;
    private String mArrival;
    private String mTickets;

    List<Flight> mflights;
    List<CreateAccount> mAccounts;

    CreateAccountDao flightsDAO;
    CreateAccountDao accountsDAO;
    ArrayList<String> flyhigh;
    ListView listView;
    String flightNumber;
    ArrayList<String> fly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat);
        getDatabase();
        listView = findViewById(R.id.ListReserve);
        mDeparturefield = findViewById(R.id.resversteaDeparture);
        mArrivalField = findViewById(R.id.reserveseatArrival);
        mTicketsField = findViewById(R.id.reserveseatnumberofseats);
        mSearchBtn = findViewById(R.id.mReserveSeatBtn);
        mreserveEnter = findViewById(R.id.mreserveflightbtn);

        mflights = flightsDAO.getAllFlights();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if(searchFlights(mDeparture, mArrival)){
                    fly = new ArrayList<>();
                    flightDisplay(mDeparturefield, mArrivalField);

                    ArrayAdapter adapter = new ArrayAdapter(ReserveSeatActivity.this, android.R.layout.simple_expandable_list_item_1, fly);
                    listView.setAdapter(adapter);
                    if(checkNumberTickets(mTickets)) {
                        Log.i("ReserveSeat", "True match flights");
//                       // mflightsInfo.setText(flightDisplay(mDeparturefield, mArrivalField));

                        if (!checkCapacity(mTickets, mDeparturefield, mArrivalField)) {
                            AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatActivity.this);
                            build.setTitle("NOT ENOUGH SEATS");
                            build.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            build.create().show();
                        }


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });
                        mreserveEnter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatActivity.this);
                                build.setMessage("Remember the Flight Number you wish to reserve");
                                build.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                build.setNegativeButton("Next ->", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ReserveSeatActivity.this, ReserveSeatLoginActivity.class);
                                        intent.putExtra("numSeats", mTickets);

                                        startActivity(intent);
                                    }
                                });
                                build.create().show();
                            }
                        });

                    }else{
                        AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatActivity.this);
                        build.setTitle("SORRY CAN ONLY REQUEST UP TO 7 TICKETS");
                        build.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ReserveSeatActivity.this, MainMenu.class);
                                startActivity(intent);
                            }
                        });
                        build.create().show();
                    }

                }else{
                    Log.i("Reserve Seat", "false match flights");
                    AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatActivity.this);
                    build.setTitle("NO FLIGHT AVAILABLE");
                    build.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ReserveSeatActivity.this, MainMenu.class);
                            startActivity(intent);
                        }
                    });
                    build.create().show();
                    }

                }
            });

        }


    public boolean checkCapacity(String tickets, EditText departure, EditText arrival){
        for(Flight fly: mflights){
            if(fly.getMdeparture().trim().equals(departure.getText().toString().trim()) && fly.getMarrival().trim().equals(arrival.getText().toString().trim())){
                if(Integer.parseInt(tickets) < fly.getMnumofseats() || fly.getMnumofseats() == 0){
                    return true;
                }
            }
        }
        return false;
    }
//    public void click(){
//        mflightsInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(ReserveSeatActivity.this, ReserveSeatLoginActivity.class);
//                intent.putExtra("FlightNumber", flightNumber);
//                intent.putExtra("numSeats", mTickets);
//                startActivity(intent);
//            }
//        });
//    }
//
//    public boolean checkLogin(String username, String password){
//        List<CreateAccount> accounts = accountsDAO.getAllUsers();
//        for(CreateAccount acc: accounts){
//            if(acc.getMUsername().equals(username) && acc.getMUsername().equals(password)){
//                return true;
//            }
//        }
//        return false;
//    }

    public void flightDisplay(EditText departure, EditText arrival){
        for(Flight flight: mflights){
            if(flight.getMdeparture().trim().equals(departure.getText().toString().trim()) && flight.getMarrival().trim().equals(arrival.getText().toString().trim())){
               flightNumber = flight.getMflightNumber();

               fly.add(flight.displayflight());
            }
        }

    }
    public boolean checkNumberTickets(String numTickets){
        if(Integer.parseInt(numTickets.trim()) <=7){
            return true;
        }
        return false;
    }

    public boolean searchFlights(String departure, String arrival){

        for(Flight flight: mflights){
            Log.i("res", flight.getMdeparture());
            if(flight.getMdeparture().trim().equals(departure.trim()) && flight.getMarrival().trim().equals(arrival.trim())){

                return true;
            }
        }
        return false;

    }
    public void getValues(){
        mDeparture = mDeparturefield.getText().toString();
        mArrival = mArrivalField.getText().toString();
        mTickets = mTicketsField.getText().toString();
    }

    public void getDatabase(){
        flightsDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }
}
