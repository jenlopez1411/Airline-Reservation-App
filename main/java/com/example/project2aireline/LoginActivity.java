package com.example.project2aireline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2aireline.AirelineDatabase.CreateAccountDao;
import com.example.project2aireline.AirelineDatabase.CreateAccountDatabase;
import com.example.project2aireline.Models.CreateAccount;

import java.util.List;

//*Name: Jennifer S Lopez
//*Date: 4/24/2020
//*Title: LoginActivity
//*Description: this program starts the log in program where the I used to Toast to
// capture the username "Admin" and password"password" entered by the user.
public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "AccountCreated_log";

    private Button mbtn;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private String mUsername;
    private String mPassword;
    int count;
    private List<CreateAccount> users;
    private CreateAccountDao mCreateDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameField = findViewById(R.id.editTextUsername);
        mPasswordField = findViewById(R.id.editTextPassword);
        mbtn = findViewById(R.id.buttonLogin);
        Intent num = getIntent();
        String getnum= num.getStringExtra("zero");
        count = Integer.parseInt(getnum);
        getDatabase();
        users = mCreateDao.getAllUsers();

        if(users.size() <= 0){
            CreateAccount defaultUser;
            defaultUser = new CreateAccount("alice5", "csumb100");
            CreateAccount defaultUser2;
            defaultUser2 = new CreateAccount("brian77", "123ABC");
            CreateAccount defaultUser3;
            defaultUser3 = new CreateAccount("chris21", "CHRIS21");
            CreateAccount defaultUser4 = new CreateAccount("sophia123", "SOPHIA123");
            CreateAccount account = new CreateAccount("Luis2020", "brother2020");
            mCreateDao.insert(defaultUser);
            mCreateDao.insert(defaultUser2);
            mCreateDao.insert(defaultUser3);
            mCreateDao.insert(defaultUser4);
            mCreateDao.insert(account);


        }

        checkTries();
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(CheckUserInDB()){
                    Log.i(TAG, "pass!");
                    count++;
                    checkTries();
                    Toast.makeText(getApplicationContext(), "Already " + mUsername + " exists", Toast.LENGTH_LONG).show();
                }else{
                    checkTries();
                    if(CheckForValidUsername()) {
                        if(CheckForValidPassword()) {
                            Log.i(TAG, "Check for valid username" );
                            CreateAccount newAccount = new CreateAccount(mUsername, mPassword);
                            mCreateDao.insert(newAccount);
                            Toast.makeText(getApplicationContext(), "Account Created Successfully!", Toast.LENGTH_LONG).show();
                            validBackToMainMenu();
                        }else{
                            Toast.makeText(getApplicationContext(), "Password Error: Not Valid Password", Toast.LENGTH_LONG).show();
                            count++;
                            checkTries();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Username Error: Not Valid Username ", Toast.LENGTH_LONG).show();
                        count++;
                        checkTries();
                    }

                    checkTries();
                }
            }
        });

    }
    public void validBackToMainMenu(){
        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(intent);
    }
    public void checkTries(){
        Log.i("check counter", String.valueOf(count) );
        if(count == 2){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Create Account Info");
            builder.setMessage("You are returning to Main Menu");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                    startActivity(intent);
                }
            });

            builder.create().show();
        }
    }
    public boolean CheckForValidPassword(){
        if(mPassword.trim().length() >= 4 && mPassword.matches("(?=.*[a-zA-Z])(?=.*[a-zA-Z])(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")){
            return true;
        }
        return false;
    }
    public boolean CheckForValidUsername(){
        if(mUsername.trim().length() >= 4 && mUsername.matches("(?=.*[a-zA-Z])(?=.*[a-zA-Z])(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")){
            return true;
        }
        return false;
    }
    private boolean CheckUserInDB(){
        List<CreateAccount> accounts = mCreateDao.getAllUsers();
        for(CreateAccount acc: accounts) {
            if(mUsername.trim().equals(acc.getMUsername().trim())){
                return true;
            }
        }
        return false;
    }
    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }
    public void getDatabase(){
        mCreateDao = Room.databaseBuilder(this, CreateAccountDatabase.class, CreateAccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCreateAccountDao();
    }

}
