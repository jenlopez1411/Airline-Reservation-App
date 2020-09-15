package com.example.project2aireline.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class Flight implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int flightId;

    private String mflightNumber;
    private String mdeparture;
    private String marrival;
    private String mtimeOfDeparture;
    private int mnumofseats;
    private double price;
    private Date mdate;


    public Flight(String mflightNumber, String mdeparture, String marrival, String mtimeOfDeparture, int mnumofseats, double price) {
        this.mflightNumber = mflightNumber;
        this.mdeparture = mdeparture;
        this.marrival = marrival;
        this.mtimeOfDeparture = mtimeOfDeparture;
        this.mnumofseats = mnumofseats;
        this.price = price;
        mdate = new Date();
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getMflightNumber() {
        return mflightNumber;
    }

    public void setMflightNumber(String mflightNumber) {
        this.mflightNumber = mflightNumber;
    }

    public String getMdeparture() {
        return mdeparture;
    }

    public void setMdeparture(String mdeparture) {
        this.mdeparture = mdeparture;
    }

    public String getMarrival() {
        return marrival;
    }

    public void setMarrival(String marrival) {
        this.marrival = marrival;
    }

    public String getMtimeOfDeparture() {
        return mtimeOfDeparture;
    }

    public void setMtimeOfDeparture(String mtimeOfDeparture) {
        this.mtimeOfDeparture = mtimeOfDeparture;
    }

    public int getMnumofseats() {
        return mnumofseats;
    }

    public void setMnumofseats(int mnumofseats) {
        this.mnumofseats = mnumofseats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    @Override
    public String toString() {
        return "Flight Number: " + mflightNumber +
                "\nDeparture: " + mdeparture +
                "\nTime: " + mtimeOfDeparture +
                "\nArrival: " + marrival +
                "\nAvailable Seats: " + mnumofseats +
                "\nPrice: $" + price ;
    }

    public String displayflight(){
        return "Flight Number: " + getMflightNumber() +
                " Departure: " + getMdeparture() +
                " Time: " + getMtimeOfDeparture() +
                "\nArrival: " + getMarrival() +
                " Seats: " + getMnumofseats() +
                " Price: $" + String.format("%.2f", getPrice());
    }
}
