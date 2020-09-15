package com.example.project2aireline.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class CreateAccount implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mUsername;
    private String mPassword;
    private Date mdate;
    public CreateAccount(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        mdate = new Date();
    }

    public int getMId() {
        return mId;
    }

    public void setMId(int mId) {
        this.mId = mId;
    }

    public String getMUsername() {
        return mUsername;
    }

    public void setMUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    @Override
    public String toString() {
        return "Transaction Type: (New Account) " +
                "\nLog ID:" + mId +
                "\nUsername: " + mUsername +
                "\nDate: " + mdate;
    }
}
