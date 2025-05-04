package org.example.HR;

import org.example.Standard;
import org.example.DB.Database;

import javax.swing.*;
import java.awt.*;

public class Attendance extends Standard{
    public Attendance(){
    private String employeeId;
    private boolean isPresent;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    // Constructor
    public Attendance(String employeeId) {
        this.employeeId = employeeId;
        this.isPresent = false;
    }

    // Methods to mark check-in and check-out
    public void markAttendance(LocalTime checkIn, LocalTime checkOut) {
        if (checkIn.isAfter(LocalTime.of(8, 0)) || checkOut.isBefore(LocalTime.of(17, 0))) {
            System.out.println("Warning: Outside standard time range (8 AM - 5 PM)");
        }
        this.checkInTime = checkIn;
        this.checkOutTime = checkOut;
        this.isPresent = true;
    }

    public boolean isPresent() {
        return isPresent;
    }

    // Getters
    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }
    }
}

