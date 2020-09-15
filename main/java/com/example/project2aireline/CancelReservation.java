package com.example.project2aireline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CANCELRESERVATION;
import com.example.project2aireline.Models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class CancelReservation extends AppCompatActivity {
    ListView listView;
    private ArrayList<String> arrayList;
    CreateAccountDao reservationDAO;
    CreateAccountDao cancelDao;
    private TextView mviewAccounts;
    private EditText mflightnum;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);


        mviewAccounts = findViewById(R.id.listaccounts);
        mviewAccounts.setMovementMethod(new ScrollingMovementMethod());
        mflightnum = findViewById(R.id.part1);
        Button flightnumbtn = findViewById(R.id.part22);
        getDatabase();
 //       listView = findViewById(R.id.Listview);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
//        Log.i("before", username);
//        addtoList(username);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayList);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

            int count = getAccountsReservation(username);
            if(validReservation(username)) {
                Log.i("t", "hello");
               displayAllReservations(username);
            }else {
                AlertDialog.Builder none = new AlertDialog.Builder(CancelReservation.this);
                none.setMessage("No Reservation with that username");
                none.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CancelReservation.this, MainMenu.class);
                        startActivity(intent);
                    }
                });
            }
        flightnumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mflightnum.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(),"Must enter fields", Toast.LENGTH_LONG).show();

                }else{
                    alert();
                }
            }
        });


    }

    public void alert(){
        AlertDialog.Builder build = new AlertDialog.Builder(CancelReservation.this);
        build.setMessage("Confirm "+ mflightnum.getText().toString() +"Cancellation");
        build.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteReservation(username, mflightnum);
                Intent intent = new Intent(CancelReservation.this, MainMenu.class);
                startActivity(intent);
            }
        });
        build.setNegativeButton("Don't Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent back = new Intent(CancelReservation.this, MainMenu.class);
                startActivity(back);
            }
        });
        build.create().show();
    }
    public void deleteReservation(String user, EditText flightnum){
        List<Reservation> reserve = reservationDAO.getAllReservation();
        for(Reservation ree: reserve){
            if(ree.getMUsername().trim().equals(user.trim()) && ree.getMFightnum().trim().equals(flightnum.getText().toString().trim())){
                Toast.makeText(getApplicationContext(),"CANCELLATION WAS A SUCCESS", Toast.LENGTH_LONG).show();
                saveCancellation(username, flightnum);
                reservationDAO.delete(ree);
            }
        }
    }

    public void saveCancellation(String user, EditText fly){
        List<Reservation> reservations = reservationDAO.getAllReservation();

        for(Reservation myreserve: reservations){
            if(myreserve.getMUsername().trim().equals(user.trim()) && myreserve.getMFightnum().trim().equals(fly.getText().toString().trim())){
                CANCELRESERVATION newcancel = new CANCELRESERVATION(myreserve.getMUsername(), myreserve.getMFightnum(), myreserve.getMDeparture(), myreserve.getMArrival(), myreserve.getMSeatsnum(), myreserve.getMReservationNum(), myreserve.getMDate().toString());
                cancelDao.insert(newcancel);
            }
        }
    }
//    List<Reservation> reservations = mCreateAccountDAO.getReservations();
//    StringBuilder reserve = new StringBuilder();
//        for(Reservation reservation: reservations){
//        reserve.append(reservation);
//        reserve.append("\n");
//        reserve.append("====================");
//        reserve.append("\n");
//    }
//    String display = sb.toString() + reserve.toString();
//        mMainDisplay.setText(display);
    public void displayAllReservations(String user){
        List<Reservation> flights = reservationDAO.getAllReservation();
        StringBuilder reservall = new StringBuilder();
        for(Reservation reserve: flights){
            if(reserve.getMUsername().trim().equals(user.trim())){
                reservall.append(reserve);
                reservall.append("\n");
                reservall.append("====================");
                reservall.append("\n");
            }
        }
        mviewAccounts.setText(reservall);
    }
    public boolean validReservation(String username){
        Log.i("tet", username);
        boolean vald = false;
        List<Reservation> reservations = reservationDAO.getAllReservation();
        for(Reservation rres: reservations){
            if(rres.getMUsername().trim().equals(username.trim())){
                return true;
            }
        }
        return false;
    }
    public int getAccountsReservation(String username){
        Log.i("test", username);
        int count = 0;
        List<Reservation> reservations = reservationDAO.getAllReservation();
        for(Reservation rres: reservations){
            if(rres.getMUsername().trim().equals(username.trim())){
                count++;
            }
        }
        return count;
    }
//    public void addtoList(String user){
//        List<Reservation> reservations = reservationDAO.getAllReservation();
//        for(Reservation res: reservations){
//            if(res.getMUsername().equals(user)){
//                Log.i("Cancel", res.getMUsername() + "\n" + res.displayReservation());
//                arrayList.add(res.displayReservation());
//            }
//        }
//        Log.i("Cancel", user + "Failed");
//
//    }
    public void getDatabase() {
        reservationDAO = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
        cancelDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }
}
