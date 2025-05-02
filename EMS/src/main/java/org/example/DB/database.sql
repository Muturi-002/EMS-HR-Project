DROP DATABASE IF EXISTS 'EMS';
CREATE DATABASE 'EMS';
USE 'EMS';

CREATE TABLE Departments
(
    DepartmentCode INT PRIMARY KEY AUTO_INCREMENT,
    DepartmentName VARCHAR(100) NOT NULL
);
CREATE TABLE Division (
    DivisionID INT PRIMARY KEY AUTO_INCREMENT,
    DivisionName VARCHAR(100) NOT NULL,
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentCode) ON DELETE SET NULL
);
CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    MiddleName VARCHAR(50),
    LastName VARCHAR(50) NOT NULL,
    YearOfBirth DATE NOT NULL,
    NationalIDNo VARCHAR(20) UNIQUE NOT NULL,
    EmailAddress VARCHAR(100) UNIQUE NOT NULL,
    PhysicalAddress TEXT ,
    Disabilities ENUM('YES', 'NO') DEFAULT 'NO',
    KRAPIN VARCHAR(20) UNIQUE NOT NULL,
    DepartmentDivision INT,
    Status ENUM('Active', 'Inactive') NOT NULL DEFAULT 'Active',
    FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL
);
CREATE TABLE Temporary (
    TempID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    MiddleName VARCHAR(50),
    LastName VARCHAR(50) NOT NULL,
    WorkLevel ENUM('Intern', 'Attache') NOT NULL,
    YearOfBirth DATE NOT NULL,
    NationalIDNo VARCHAR(20) UNIQUE NOT NULL,
    EmailAddress VARCHAR(100) UNIQUE NOT NULL,
    PhysicalAddress TEXT ,
    Disabilities ENUM('YES', 'NO') DEFAULT 'NO',
    KRAPIN VARCHAR(20) UNIQUE,
    DepartmentDivision INT,
    FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL,
    CHECK (WorkLevel = 'Intern' AND KRAPIN IS NOT NULL OR WorkLevel = 'Attache' AND KRAPIN IS NULL)
);
CREATE TABLE Attendance (
    AttendanceID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    Date DATE NOT NULL,
    Status ENUM('Present', 'Absent', 'Leave') NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);
CREATE TABLE LeaveRequests (
    LeaveID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason TEXT NOT NULL,
    Status ENUM('Pending', 'Approved', 'Rejected') NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);
CREATE TABLE Authorization (
    AuthID INT PRIMARY KEY AUTO_INCREMENT,
    AssociatedEmployeeID INT NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'HR', 'Senior HR Manager') NOT NULL,
    FOREIGN KEY (AssociatedEmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);


INSERT INTO Departments (DepartmentName)
VALUES (
    'ADMINISTRATION',
    'CUSTOMER SERVICE & CORPORATE AFFAIRS',
    'LEGAL AFFAIRS',
    'FINANCE & ACCOUNTING',
    'INFORMATION, COMMUNICATION & TECHNOLOGY'
);
INSERT INTO Division (DivisionName, DepartmentID)
VALUES
    ('HUMAN RESOURCE MANAGEMENT',1),
    ('ADMINISTRATION',1),
    ('RECORDS AND ARCHIVE MANAGEMENT',1),
    ('PUBLIC RELATIONS AND MEDIA ENGAGEMENT',2),
    ('STAKEHOLDER & GOVERNMENT RELATIONS',2),
    ('LEGAL ADVISORY',3),
    ('CONTRACTS AND AGREEMENTS MANAGEMENT',3),
    ('SUPPLY CHAIN MANAGEMENT AND PROCUREMENT',4),
    ('FINANCE',4),
    ('ACCOUNTING',4),
    ('IT INFRASTRUCTURE',5),
    ('SYSTEMS DEVELOPMENT & MAINTENANCE',5),
    ('REVENUE COLLECTION & MANAGEMENT',3);

