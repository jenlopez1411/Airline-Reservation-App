package com.example.project2aireline.Models;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Reservation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mReservationId;

    private int mReservationNum;
    private String mUsername;
    private String mFightnum;
    private String mDeparture;
    private String mArrival;
    private int mSeatsnum;
    private double mTotal;
    private String mtime;
    private Date mDate;

    public Reservation(String mUsername, String mFightnum, String mDeparture, String mArrival, int mSeatsnum, double mTotal, String mtime) {
        this.mReservationNum = 1;
        this.mUsername = mUsername;
        this.mFightnum = mFightnum;
        this.mDeparture = mDeparture;
        this.mArrival = mArrival;
        this.mSeatsnum = mSeatsnum;
        this.mTotal = mTotal;
        this.mtime = mtime;
        mDate = new Date();
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getMReservationId() {
        return mReservationId;
    }

    public void setMReservationId(int mReservationId) {
        this.mReservationId = mReservationId;
    }

    public int getMReservationNum() {
        return mReservationNum;
    }

    public void setMReservationNum(int mReservationNum) {
        this.mReservationNum = mReservationNum;
    }

    public String getMUsername() {
        return mUsername;
    }

    public void setMUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getMFightnum() {
        return mFightnum;
    }

    public void setMFightnum(String mFightnum) {
        this.mFightnum = mFightnum;
    }

    public String getMDeparture() {
        return mDeparture;
    }

    public void setMDeparture(String mDeparture) {
        this.mDeparture = mDeparture;
    }

    public String getMArrival() {
        return mArrival;
    }

    public void setMArrival(String mArrival) {
        this.mArrival = mArrival;
    }

    public int getMSeatsnum() {
        return mSeatsnum;
    }

    public void setMSeatsnum(int mSeatsnum) {
        this.mSeatsnum = mSeatsnum;
    }

    public double getMTotal() {
        return mTotal;
    }

    public void setMTotal(double mTotal) {
        this.mTotal = mTotal;
    }

    public Date getMDate() {
        return mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return "Transaction Type: (Reservation)" +
                "\nReservation Number: (" + mReservationNum + ")"+
                "\nUsername: " + mUsername +
                "\nDate: " + mDate +
                "\nFlight: " + mFightnum +
                "\nDeparture: " + mDeparture +
                "\nTime: " + mtime +
                "\n# of Tickets: " + mSeatsnum +
                "\nTotal Price: $" + String.format("%.2f",mTotal);
    }

    public String displayReservation(){
        return "Reservation: #" + mReservationNum +
                "  Username: " + mUsername +
                "  Flight: " + mFightnum + "" +
                "  Departure: " + mDeparture + "" +
                "\nTime: " + mtime +
                "   Arrival: " + mArrival +
                "   # of Tickets: " + mSeatsnum +
                "   Total of Price: $" + String.format("%.2f", mTotal);
    }
}
