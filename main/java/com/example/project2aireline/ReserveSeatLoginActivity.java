package com.example.project2aireline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;
import com.example.project2aireline.Models.Reservation;

import java.util.List;

public class ReserveSeatLoginActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mEnterFlightnum;
    private Button mReservebtn;

    CreateAccountDao flightsDAO;
    CreateAccountDao accountsDAO;
    CreateAccountDao reservationDAO;

    List<Flight> mflights;
    List<CreateAccount> mAccounts;
    List<Reservation> mReservation;

    String mUsername;
    String mPassword;
    String flightNum;
    String stringtickets;
    int tickets;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat_login);
        mUsernameField = findViewById(R.id.userRS);
        mPasswordField = findViewById(R.id.newPassword);
        mEnterFlightnum = findViewById(R.id.mEnterflightnum);
        mReservebtn = findViewById(R.id.reserveseatloginENTERbtn);
        Intent intent = getIntent();
        flightNum = intent.getStringExtra("FlightNumber");
        stringtickets = intent.getStringExtra("numSeats");
        tickets = Integer.parseInt(stringtickets);
        getValues();
        getDatabase();
        mReservebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUserAccount(mUsernameField, mPasswordField)){
                  Toast.makeText(getApplicationContext(), "Match account", Toast.LENGTH_LONG).show();
                  //Intent in = getIntent();
                  //String reserveFlight = in.getStringExtra("")
                    if(checkFlight(mEnterFlightnum)) {
                        final AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatLoginActivity.this);
                        build.setTitle("Confirm Reservation!");
                        build.setMessage(displayConfirmation(mUsernameField, mEnterFlightnum, tickets));
                        build.setNegativeButton("CANCEL RESERVATION", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkConfirmation();
                            }
                        });
                        build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = checkExistingReservation(mEnterFlightnum, mUsernameField);
                                //displayExistingReservation();
                                Log.i("Test", "pass");

                                insertNewReservation(mEnterFlightnum, mUsernameField, tickets, c);

                                Toast.makeText(getApplicationContext(), mUsernameField.getText().toString() + " YOUR RESERVATION WAS SUCCESSFULL ", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ReserveSeatLoginActivity.this, MainMenu.class);
                                startActivity(i);
                            }
                        });
                        build.create().show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect Flight Number", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(count == 1){
                        returntoMenu();
                    }
                    count++;
                    Toast.makeText(getApplicationContext(), "Not Match account", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public boolean checkFlight(EditText flightnum){
        List<Flight> flights = flightsDAO.getAllFlights();
        for(Flight sky:flights){
            if(sky.getMflightNumber().trim().equals(flightnum.getText().toString().trim())){
                return true;
            }
        }
        return false;

    }
    public void checkConfirmation(){
        AlertDialog.Builder confirm = new AlertDialog.Builder(ReserveSeatLoginActivity.this);

        confirm.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "ERROR: RESERVATION FAILED", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ReserveSeatLoginActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
        confirm.setMessage("RE-CONFIRM CANCELLATION");
        confirm.setNegativeButton("DON'T CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirm.create().show();
    }
    public String displayConfirmation(EditText user, EditText flight, int tick){
        List<Flight> airplane = flightsDAO.getAllFlights();
        String mflight = "";
        String mDeparture = "";
        String mTime = "";
        String mArrival = "";
        double mPrice = 0.00;
        for(Flight air: airplane){
            if(air.getMflightNumber().trim().equals(flight.getText().toString().trim())){
                mflight = air.getMflightNumber();
                mDeparture = air.getMdeparture();
                mTime = air.getMtimeOfDeparture();
                mArrival = air.getMarrival();
                mPrice = air.getPrice() * tick;
            }
        }
        String reservationnumbers = displayReservationNumber(user.getText().toString());
        //String totalPrice = String.valueOf(mPrice);
        return
                "Username: " + user.getText().toString() +
                "\nFlight: " + mflight +
                "\nDeparture: " + mDeparture +
                        "\nArrival: " + mArrival +
                "\nTime: " + mTime +
                "\n# of Tickets: " + String.valueOf(tick) +
                        "\nReservation: # "+ reservationnumbers +
                "\nTotal Price: $" + String.format("%.2f", mPrice);
    }

    public String displayReservationNumber(String f){
        List<Reservation> reserva = reservationDAO.getAllReservation();
        for(Reservation r: reserva){
            if(r.getMUsername().trim().equals(f.trim())){
                Log.i("Test", r.getMFightnum() + "/" + f + "+" + r.getMReservationNum());
                return String.valueOf(r.getMReservationNum() + 1);
            }
        }
        return "1";
    }
    public void insertNewReservation(EditText flightNum, EditText mUsername, int tick, int num){
        List<Flight> flight = flightsDAO.getAllFlights();
        String mflight = "";
        String mDeparture = "";
        String mTime = "";
        String mArrival = "";
        Double mPrice = 0.00;
        for(Flight fly: flight){
            if(fly.getMflightNumber().trim().equals(flightNum.getText().toString().trim())){
                mflight = fly.getMflightNumber();
                mDeparture = fly.getMdeparture();
                mTime = fly.getMtimeOfDeparture();
                mArrival = fly.getMarrival();
                mPrice = fly.getPrice() * tick;
            }
        }
        updateFlightCapacity(tick, flightNum);
        Reservation newReservation = new Reservation(mUsernameField.getText().toString(),mflight,mDeparture,mArrival,tick, mPrice, mTime);
        newReservation.setMReservationNum(newReservation.getMReservationNum()+num);
        reservationDAO.insert(newReservation);

    }
    public void updateFlightCapacity(int tickeet, EditText fli){
        List<Flight> flight = flightsDAO.getAllFlights();
        for(Flight fly:flight){
            if(fly.getMflightNumber().trim().equals(fli.getText().toString().trim())){
                Log.i("updateflight", fly.getMflightNumber() + "/" +fli + " tickets :" + tickeet);
                fly.setMnumofseats(fly.getMnumofseats() - tickeet);
                flightsDAO.update(fly);
                Log.i("updateflight", " tickets :" + fly.getMnumofseats());
            }
        }
    }

     public int checkExistingReservation(EditText flightnum, EditText mUsername){
         List<Reservation> reserva = reservationDAO.getAllReservation();
    //     updateFlightCapacity(tickets, flightnum);
         int cont = 0;
         for(Reservation f: reserva){
             if(f.getMUsername().trim().equals(mUsername.getText().toString().trim())){
                 Log.i("Test", f.getMFightnum() + "/" + flightnum + "+" + cont);
                cont++;



             }
         }
         return cont;
     }


    public void returntoMenu(){
        AlertDialog.Builder build = new AlertDialog.Builder(ReserveSeatLoginActivity.this);
        build.setTitle("TO MANY TRIES");
        build.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ReserveSeatLoginActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
        build.create().show();
    }
    public boolean checkUserAccount(EditText username, EditText password){
        List<CreateAccount> Accs = accountsDAO.getAllUsers();
        for(CreateAccount acc: Accs){
           //checking every account Log.i("Login", username + " /" + acc.getMUsername() + password + " /" + acc.getMPassword());
            if(acc.getMUsername().trim().equals(username.getText().toString().trim()) && acc.getMPassword().trim().equals(password.getText().toString().trim())){
               // check if it works Log.i("Login", username.getText().toString() + " /" + acc.getMUsername() + password.getText().toString() + " /" + acc.getMPassword());
                return true;
            }
        }

        return false;
    }
    public void getDatabase(){
        flightsDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        accountsDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        reservationDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }
    public void getValues(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }
}
