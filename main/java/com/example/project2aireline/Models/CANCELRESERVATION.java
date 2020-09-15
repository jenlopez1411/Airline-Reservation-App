package com.example.project2aireline.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CANCELRESERVATION implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int cancelID;

    private String username;
    private String flightnum;
    private String departure;
    private String arrival;
    private int tickets;
    private int reservationNum;
    private String date;

    public CANCELRESERVATION(String username, String flightnum, String departure, String arrival, int tickets, int reservationNum, String date) {
        this.username = username;
        this.flightnum = flightnum;
        this.departure = departure;
        this.arrival = arrival;
        this.tickets = tickets;
        this.reservationNum = reservationNum;
        this.date = date;
    }

    public int getCancelID() {
        return cancelID;
    }

    public void setCancelID(int cancelID) {
        this.cancelID = cancelID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(int reservationNum) {
        this.reservationNum = reservationNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction type: (Cancellation)" +
                "\nCustomer Username:" + username +
                "\nFlight number: " + flightnum +
                "\nDeparture: " + departure +
                "\nArrival: " + arrival +
                "\nNumber of Tickers" + tickets +
                "\nReservation Number" + reservationNum +
                "\nTime: " + date ;
    }
}
