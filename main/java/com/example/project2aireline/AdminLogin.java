package com.example.project2aireline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    private Button mAdminLoginBtn;
    private EditText mAdminUsernamefield;
    private EditText mAdminPasswordfield;
    private String mAdminUsername;
    private String mAdminPassword;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAdminLoginBtn = findViewById(R.id.adminLoginBtn);
        mAdminUsernamefield = findViewById(R.id.adminUsername);
        mAdminPasswordfield = findViewById(R.id.adminPassword);

        mAdminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if(FieldEmpty(mAdminUsernamefield) || FieldEmpty(mAdminPasswordfield)){
                    Toast.makeText(getApplicationContext(), "Each field must be filled out.", Toast.LENGTH_LONG).show();
                }else{
                    if(AdminAccount(mAdminUsername, mAdminPassword)){
                        Toast.makeText(getApplicationContext(), "WELCOME ADMINISTRATOR!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AdminLogin.this, ManageSystemActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Wrong username & password", Toast.LENGTH_LONG).show();
                        count++;
                        checkAdminerror(count);
                    }
                }

            }
        });
    }
    public void checkAdminerror(int count){
        if(count ==3){
            AlertDialog.Builder build = new AlertDialog.Builder(AdminLogin.this);
            build.setMessage("ERROR: Login Failed");
            build.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(AdminLogin.this, MainMenu.class);
                    startActivity(intent);
                }
            });


        }
    }
    public boolean AdminAccount(String username, String password){
        return mAdminUsername.trim().equals("admin2") && mAdminPassword.trim().equals("admin2");
    }
    public boolean FieldEmpty(EditText checkText){
        return checkText.getText().toString().trim().length() == 0;
    }
    public void getValues(){
        mAdminUsername = mAdminUsernamefield.getText().toString();
        mAdminPassword = mAdminPasswordfield.getText().toString();
    }
}
