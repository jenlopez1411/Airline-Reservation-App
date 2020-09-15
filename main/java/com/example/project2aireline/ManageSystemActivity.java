package com.example.project2aireline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CANCELRESERVATION;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Flight;
import com.example.project2aireline.Models.Reservation;

import java.util.List;

public class ManageSystemActivity extends AppCompatActivity {
    List<CreateAccount> mCreateAccounts;
    CreateAccountDao mCreateAccountDAO;
    CreateAccountDao flightsDAO;
    CreateAccountDao cancelDAO;
    TextView mMainDisplay;
    Button mContinuebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        getDatabase();
        Log.i("checking", "here");
        mMainDisplay = findViewById(R.id.listaccounts);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
        mContinuebtn = findViewById(R.id.manageSystemContinueBtn);
        accountDisplay();

        mContinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

    }

    public void alertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to add new flight info?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ManageSystemActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ManageSystemActivity.this, CreateFlightInfo.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    private void accountDisplay(){
        mCreateAccounts = mCreateAccountDAO.getUsers();
        if(mCreateAccounts.size() >= 0){
            mMainDisplay.setText("No New Accounts");
        }
        StringBuilder sb = new StringBuilder();
        for(CreateAccount acc: mCreateAccounts){
            sb.append(acc);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-");
            sb.append("\n");
        }
        //mMainDisplay.setText(sb.toString());

        List<Reservation> reservations = mCreateAccountDAO.getReservations();
        StringBuilder reserve = new StringBuilder();
        for(Reservation reservation: reservations){
            reserve.append(reservation);
            reserve.append("\n");
            reserve.append("====================");
            reserve.append("\n");
        }
        List<CANCELRESERVATION> cancelreservations = cancelDAO.getcancelreservations();
        StringBuilder cancel = new StringBuilder();
        for(CANCELRESERVATION cc: cancelreservations){
            cancel.append(cc);
            cancel.append("\n");
            cancel.append("====================");
            cancel.append("\n");
        }

            String display = sb.toString() + reserve.toString() + cancel.toString();
            mMainDisplay.setText(display);



    }


    public void getDatabase(){
        mCreateAccountDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        flightsDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build().getCreateAccountDao();
        cancelDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build().getCreateAccountDao();
    }
}
