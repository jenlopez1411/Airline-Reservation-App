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

import java.util.List;

public class CreateFlightInfo extends AppCompatActivity {
    EditText mFlightNumberField;
    EditText mDepartureLocationField;
    EditText mArrivalLocationField;
    EditText mDepartureTimeField;
    EditText mFlightCapacityField;
    EditText mPriceField;
    Button mEnterFlightButton;
    private List<Flight> flights;
    private CreateAccountDao mFlightDao;
    private Flight newFlight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight_info);

        mFlightNumberField = findViewById(R.id.mflightnumberfield);
        mDepartureLocationField = findViewById(R.id.mdeparture);
        mArrivalLocationField = findViewById(R.id.marrivallocation);
        mDepartureTimeField = findViewById(R.id.mfdeparturetime);
        mFlightCapacityField = findViewById(R.id.mfFlightcapacity);
        mPriceField = findViewById(R.id.mfprice);
        mEnterFlightButton = findViewById(R.id.menterflightbtn);

        getDatabase();

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

        mEnterFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
              if(isEmptyfield(mFlightNumberField) || isEmptyfield(mDepartureLocationField) || isEmptyfield(mArrivalLocationField) || isEmptyfield(mDepartureTimeField) || isEmptyfield(mFlightCapacityField) || isEmptyfield(mPriceField)){
                    Toast.makeText(getApplicationContext(), "Each field must be filled out", Toast.LENGTH_LONG).show();
               }else{
                    if(!checkFlight(mFlightNumberField.getText().toString())){
                        Log.i("CreateFlight", "Check for valid username" );
                        newFlight = new Flight(mFlightNumberField.getText().toString(), mDepartureLocationField.getText().toString(), mArrivalLocationField.getText().toString(), mDepartureTimeField.getText().toString(), Integer.parseInt(mFlightCapacityField.getText().toString()), Double.parseDouble(mPriceField.getText().toString()));
                        alertDialog();
                    }else {
                        Toast.makeText(getApplicationContext(), mFlightNumberField.getText().toString() + " already exists!", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder build = new AlertDialog.Builder(CreateFlightInfo.this);
                        build.setMessage("Error: Flight " + mFlightNumberField.getText().toString() + " already exists");
                        build.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CreateFlightInfo.this, MainMenu.class);
                                startActivity(intent);
                            }
                        });
                        build.create().show();
                    }
                  Log.i("CreateFlight", "lol" +  flights.get(3).getMflightNumber());
               }
            }
        });

    }
    public void alertDialog(){
        final AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("CONFIRM");
        build.setMessage(displayConfirmation());
        build.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CreateFlightInfo.this, MainMenu.class);
                startActivity(intent);
            }
        });
        build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFlightDao.insert(newFlight);
                Toast.makeText(getApplicationContext(), "FLIGHT INFORMATION:\n"+ displayConfirmation() + "\nADDED SUCCESSFULLY!", Toast.LENGTH_LONG).show();
                Intent newIntent = new Intent(CreateFlightInfo.this, MainMenu.class);
                startActivity(newIntent);
            }
        });
        build.create().show();
    }
    public String displayConfirmation(){
        return "Flight Number: " + mFlightNumberField.getText().toString() +
                "\nDeparture: " + mDepartureLocationField.getText().toString() +
                "\nTime: " + mDepartureTimeField.getText().toString() +
                "\nArrival: " + mArrivalLocationField.getText().toString() +
                "\nAvailable Seats: " + mFlightCapacityField.getText().toString() +
                "\nPrice: $" + mPriceField.getText().toString() ;
    }
    public void getValues(){

    }
    public boolean checkFlight(String flight){
        flights = mFlightDao.getAllFlights();
        for(Flight mflight: flights){
            if(mflight.getMflightNumber().trim().equals(flight.trim())){
                return true;
            }
        }
        return false;
    }
    private boolean isEmptyfield(EditText check){
        return check.getText().toString().trim().length() == 0;
    }


    public void getDatabase(){
        mFlightDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }
}
