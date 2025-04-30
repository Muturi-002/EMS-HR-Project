package org.example.DB;

import java.sql.*;
import org.example.LoadEnv;

public class Database {
    Connection conn ;
    Statement stmt;
    public Database() {
        try {
            createDatabase();
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createDatabase(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            stmt = conn.createStatement();
            String checkDB = "SHOW DATABASES";
            ResultSet rs = stmt.executeQuery(checkDB);
            if (rs.next()) {
                System.out.println("Database EMS already exists.\n\n");
                stmt.executeUpdate("USE EMS");
            } else {
                String createDB = "CREATE DATABASE EMS";
                stmt.executeUpdate(createDB);
                System.out.println("Database EMS created successfully.\n\n");
                stmt.executeUpdate("USE EMS");
            }
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "root", "12345678");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createTables(){
        try {
            String departmentTable = (
                    "CREATE TABLE IF NOT EXISTS Departments (" +
                            "DepartmentCode INT PRIMARY KEY AUTO_INCREMENT," +
                            "DepartmentName VARCHAR(100) NOT NULL );"
            );
            stmt.executeUpdate(departmentTable);
            System.out.println("Department table created successfully.");
            String divisionTable = (
                    "CREATE TABLE IF NOT EXISTS Division (" +
                            "DivisionID INT PRIMARY KEY AUTO_INCREMENT," +
                            "DivisionName VARCHAR(100) NOT NULL," +
                            "DepartmentID INT," +
                            "FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentCode) ON DELETE SET NULL" +
                            ");"
            );
            stmt.executeUpdate(divisionTable);
            System.out.println("Division table created successfully.");
            String employeeTable = (
                    "CREATE TABLE IF NOT EXISTS Employees (" +
                            "EmployeeID INT PRIMARY KEY AUTO_INCREMENT," +
                            "FirstName VARCHAR(50) NOT NULL," +
                            "MiddleName VARCHAR(50)," +
                            "LastName VARCHAR(50) NOT NULL," +
                            "YearOfBirth DATE NOT NULL," +
                            "NationalIDNo VARCHAR(20) UNIQUE NOT NULL," +
                            "EmailAddress VARCHAR(100) UNIQUE NOT NULL," +
                            "PhysicalAddress TEXT NOT NULL," +
                            "Disabilities BOOLEAN NOT NULL DEFAULT FALSE," +
                            "KRAPIN VARCHAR(20) UNIQUE NOT NULL," +
                            "DepartmentDivision INT," +
                            "Branch VARCHAR(100) NOT NULL," +
                            "Status ENUM('Active', 'Inactive') NOT NULL DEFAULT 'Active'," +
                            "FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL" +
                            ");"
            );
            stmt.executeUpdate(employeeTable);
            System.out.println("EmployeeEntry table created successfully.");
            String temporaryStaffTable = (
                    "CREATE TABLE IF NOT EXISTS Temporary (" +
                            " TempID INT PRIMARY KEY AUTO_INCREMENT," +
                            " FirstName VARCHAR(50) NOT NULL," +
                            " MiddleName VARCHAR(50)," +
                            " LastName VARCHAR(50) NOT NULL," +
                            " WorkLevel ENUM('Intern', 'Attache') NOT NULL," +
                            " YearOfBirth DATE NOT NULL," +
                            " NationalIDNo VARCHAR(20) UNIQUE NOT NULL," +
                            " PhysicalAddress TEXT NOT NULL," +
                            " Disabilities ENUM('YES', 'NO') NOT NULL," +
                            " KRAPIN VARCHAR(20) UNIQUE," +
                            " DepartmentDivision INT," +
                            " FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL," +
                            " CHECK (WorkLevel = 'Intern' AND KRAPIN IS NOT NULL OR WorkLevel = 'Attache' AND KRAPIN IS NULL)" +
                            ")"
            );
            stmt.executeUpdate(temporaryStaffTable);
            System.out.println("Temporary staff table created successfully.");
            String attendanceTable = (
                    "CREATE TABLE IF NOT EXISTS Attendance (" +
                            "AttendanceID INT PRIMARY KEY AUTO_INCREMENT," +
                            "EmployeeID INT," +
                            "Date DATE NOT NULL," +
                            "Status ENUM('Present', 'Absent', 'Leave') NOT NULL," +
                            "FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE" +
                            ");"
            );
            stmt.executeUpdate(attendanceTable);
            System.out.println("Attendance table created successfully.");
            String leaveTable = (
                    "CREATE TABLE IF NOT EXISTS LeaveRequests (" +
                            "LeaveID INT PRIMARY KEY AUTO_INCREMENT," +
                            "EmployeeID INT," +
                            "StartDate DATE NOT NULL," +
                            "EndDate DATE NOT NULL," +
                            "Reason TEXT NOT NULL," +
                            "Status ENUM('Pending', 'Approved', 'Rejected') NOT NULL DEFAULT 'Pending'," +
                            "FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE" +
                            ");"
            );
            stmt.executeUpdate(leaveTable);
            System.out.println("Leave requests table created successfully.");
            String authTable = (
                    "CREATE TABLE IF NOT EXISTS Authorization (" +
                            "AuthID INT PRIMARY KEY AUTO_INCREMENT," +
                            "AssociatedEmployeeID INT NOT NULL," +
                            "Username VARCHAR(50) UNIQUE NOT NULL," +
                            "Password VARCHAR(255) NOT NULL," +
                            "Role ENUM('Admin', 'HR', 'Senior HR Manager') NOT NULL," +
                            "FOREIGN KEY (AssociatedEmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE" +
                            ");"
            );
            stmt.executeUpdate(authTable);
            System.out.println("Authorization table created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main (String []args){
        new Database();
    }
}