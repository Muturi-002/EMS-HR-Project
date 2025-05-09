package org.example.HR;

import org.example.Standard;
import org.example.DB.Database;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Leaves extends Standard {
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    // Constructor
    public Leaves(String employeeId, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public boolean isValid() {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween <= 61; // Allow up to 2 months
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }
}
