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

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CreateAccount;
import com.example.project2aireline.Models.Reservation;

import java.util.List;

public class CancelReservationLogin extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginbtn;
    CreateAccountDao accountDao;
    CreateAccountDao reservationDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.CancelUsername);
        mPassword = findViewById(R.id.cancelPassword);
        mLoginbtn = findViewById(R.id.cancelLogin);
        getDatabase();
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if(checkValidLogin(mUsername, mPassword)){
                    if(checkUsernameflights(mUsername)) {
                        Intent intent = new Intent(CancelReservationLogin.this, CancelReservation.class);
                        String user = mUsername.getText().toString();
                        Log.i("hi", user);
                        intent.putExtra("name", user);
                        startActivity(intent);
                    }else{
                        AlertDialog.Builder build = new AlertDialog.Builder(CancelReservationLogin.this);
                        build.setMessage("No reservation with the username");
                        build.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CancelReservationLogin.this, MainMenu.class);
                                startActivity(intent);
                            }
                        });

                        build.create().show();
                    }
//                }else{
//                    AlertDialog.Builder build = new AlertDialog.Builder(CancelReservationLogin.this);
//                    build.setMessage("Incorrect Username/Password");
//                    build.setNegativeButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(CancelReservationLogin.this, CancelReservation.class);
//                            startActivity(intent);
//                        }
//                    });
//                    build.setPositiveButton("Re-Try again", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    build.create().show();
//                }
            }
        });

    }
    public boolean checkUsernameflights(EditText user){

        List<Reservation> res= reservationDao.getAllReservation();
        for(Reservation r: res){
            if(r.getMUsername().trim().equals(user.getText().toString().trim())){
                return true;
            }
        }
        return false;
    }
    public boolean checkValidLogin(EditText username, EditText password){
        List<CreateAccount> accounts = accountDao.getAllUsers();
        for(CreateAccount acc: accounts){
            if(acc.getMUsername().trim().equals(username.getText().toString().trim()) && acc.getMPassword().trim().equals(password.getText().toString().trim())){
                return true;
            }
        }
        return false;
    }
    public void getDatabase(){
        accountDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        reservationDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }
}
