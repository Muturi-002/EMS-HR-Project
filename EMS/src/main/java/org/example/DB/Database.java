package org.example.DB;

import java.sql.*;
import org.example.LoadEnv;

public class Database {
    static Connection conn ;
    Statement stmt;
    String ipAddress = LoadEnv.getIP();
    String port = LoadEnv.getPort();
    String databaseUser = LoadEnv.getDatabaseUser();
    String databasePassword = LoadEnv.getDatabasePassword();
    String databaseName = LoadEnv.getDatabaseName();
    String url= "jdbc:mysql://"+ipAddress+":"+port+"/";
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
            conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            stmt = conn.createStatement();
            String dropDB = "DROP DATABASE IF EXISTS " + databaseName;
            stmt.executeUpdate(dropDB);
            String createDB = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(createDB);
            System.out.println("Database "+databaseName+" created successfully. Now using it.");
            stmt.executeUpdate("USE EMS");
            conn= DriverManager.getConnection(url+databaseName, databaseUser, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createTables(){
        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            // Drop all existing tables
            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println("Dropped table: " + tableName);
                String dropSQL = "DROP TABLE IF EXISTS `" + tableName + "`";
                stmt.executeUpdate(dropSQL);
            }
            //New tables
            String departmentTable = (
                    "CREATE TABLE Departments (" +
                            "DepartmentCode INT PRIMARY KEY AUTO_INCREMENT," +
                            "DepartmentName VARCHAR(100) NOT NULL );"
            );
            stmt.executeUpdate(departmentTable);
            System.out.println("Department table created successfully.");
            String divisionTable = (
                    "CREATE TABLE Division (" +
                            "DivisionID INT PRIMARY KEY AUTO_INCREMENT," +
                            "DivisionName VARCHAR(100) NOT NULL," +
                            "DepartmentID INT," +
                            "FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentCode) ON DELETE SET NULL" +
                            ");"
            );
            stmt.executeUpdate(divisionTable);
            System.out.println("Division table created successfully.");
            String employeeTable = (
                    "CREATE TABLE Employees (" +
                            "EmployeeID INT PRIMARY KEY AUTO_INCREMENT," +
                            "FirstName VARCHAR(50) NOT NULL," +
                            "MiddleName VARCHAR(50)," +
                            "LastName VARCHAR(50) NOT NULL," +
                            "YearOfBirth DATE NOT NULL," +
                            "NationalIDNo VARCHAR(20) UNIQUE NOT NULL," +
                            "EmailAddress VARCHAR(100) UNIQUE NOT NULL," +
                            "PhysicalAddress TEXT ," +
                            "Disabilities ENUM('YES', 'NO') DEFAULT 'NO'," +
                            "KRAPIN VARCHAR(20) UNIQUE NOT NULL," +
                            "DepartmentDivision INT," +
                            "Status ENUM('Active', 'Inactive') NOT NULL DEFAULT 'Active'," +
                            "FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL" +
                            ");"
            );
            stmt.executeUpdate(employeeTable);
            System.out.println("EmployeeEntry table created successfully.");
            String temporaryStaffTable = (
                    "CREATE TABLE Temporary (" +
                            " TempID INT PRIMARY KEY AUTO_INCREMENT," +
                            " FirstName VARCHAR(50) NOT NULL," +
                            " MiddleName VARCHAR(50)," +
                            " LastName VARCHAR(50) NOT NULL," +
                            " WorkLevel ENUM('Intern', 'Attache') NOT NULL," +
                            " YearOfBirth DATE NOT NULL," +
                            " NationalIDNo VARCHAR(20) UNIQUE NOT NULL," +
                            " EmailAddress VARCHAR(100) UNIQUE NOT NULL," +
                            " PhysicalAddress TEXT ," +
                            " Disabilities ENUM('YES', 'NO') DEFAULT 'NO'," +
                            " KRAPIN VARCHAR(20) UNIQUE," +
                            " DepartmentDivision INT," +
                            " FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL," +
                            " CHECK (WorkLevel = 'Intern' AND KRAPIN IS NOT NULL OR WorkLevel = 'Attache' AND KRAPIN IS NULL)" +
                            ")"
            );
            stmt.executeUpdate(temporaryStaffTable);
            System.out.println("Temporary staff table created successfully.");
            String attendanceTable = (
                    "CREATE TABLE Attendance (" +
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
                    "CREATE TABLE LeaveRequests (" +
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
                    "CREATE TABLE Authorization (" +
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