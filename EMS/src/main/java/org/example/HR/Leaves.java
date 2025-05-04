package org.example.HR;

import org.example.Standard;
import org.example.DB.Database;
import javax.swing.*;
import java.awt.*;

public class Leaves extends Standard {
    public Leaves(){
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    public LeaveRequest(String employeeId, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public boolean isValid() {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween <= 61; // Allow up to 2 months
    }
    }
}