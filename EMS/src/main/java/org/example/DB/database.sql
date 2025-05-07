-- In Oracle, 'DROP Database' statement is invalid, especially for Autonomous Transaction Processing databases.

CREATE TABLE Departments (
    DepartmentCode NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DepartmentName VARCHAR2(100) NOT NULL
);
CREATE TABLE Division (
    DivisionID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DivisionName VARCHAR2(100) NOT NULL,
    DepartmentID NUMBER,
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentCode) ON DELETE SET NULL
);
CREATE TABLE Employees (
    EmployeeID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    FirstName VARCHAR2(50) NOT NULL,
    MiddleName VARCHAR2(50),
    LastName VARCHAR2(50) NOT NULL,
    YearOfBirth DATE NOT NULL,
    NationalIDNo VARCHAR2(20) UNIQUE NOT NULL,
    EmailAddress VARCHAR2(100) UNIQUE NOT NULL,
    PhysicalAddress CLOB, -- TEXT in MySQL often maps to CLOB in Oracle
    Disabilities VARCHAR2(3) DEFAULT 'NO' CHECK (Disabilities IN ('YES', 'NO')), -- ENUM to VARCHAR2 with CHECK constraint
    KRAPIN VARCHAR2(20) UNIQUE NOT NULL,
    DepartmentDivision NUMBER,
    Status VARCHAR2(8) DEFAULT 'Active' CHECK (Status IN ('Active', 'Inactive')), -- ENUM to VARCHAR2 with CHECK constraint
    FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL
);
CREATE TABLE Temporary (
    TempID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    FirstName VARCHAR2(50) NOT NULL,
    MiddleName VARCHAR2(50),
    LastName VARCHAR2(50) NOT NULL,
    WorkLevel VARCHAR2(7) NOT NULL CHECK (WorkLevel IN ('Intern', 'Attache')), -- ENUM to VARCHAR2 with CHECK constraint
    YearOfBirth DATE NOT NULL,
    NationalIDNo VARCHAR2(20) UNIQUE NOT NULL,
    EmailAddress VARCHAR2(100) UNIQUE NOT NULL,
    PhysicalAddress CLOB, -- TEXT in MySQL often maps to CLOB in Oracle
    Disabilities VARCHAR2(3) DEFAULT 'NO' CHECK (Disabilities IN ('YES', 'NO')), -- ENUM to VARCHAR2 with CHECK constraint
    KRAPIN VARCHAR2(20) UNIQUE,
    DepartmentDivision NUMBER,
    FOREIGN KEY (DepartmentDivision) REFERENCES Division(DivisionID) ON DELETE SET NULL,
    CONSTRAINT temp_worklevel_krapin_check
    CHECK (
        (WorkLevel = 'Intern' AND KRAPIN IS NOT NULL) OR
        (WorkLevel = 'Attache' AND KRAPIN IS NULL)
    )
);
CREATE TABLE Attendance (
    AttendanceID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    EmployeeID NUMBER,
    Date DATE NOT NULL,
    Status VARCHAR2(7) NOT NULL CHECK (Status IN ('Present', 'Absent', 'Leave')), -- ENUM to VARCHAR2 with CHECK constraint
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);
CREATE TABLE LeaveRequests (
    LeaveID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    EmployeeID NUMBER,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason CLOB NOT NULL, -- TEXT in MySQL often maps to CLOB in Oracle
    Status VARCHAR2(8) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Approved', 'Rejected')), -- ENUM to VARCHAR2 with CHECK constraint
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);
CREATE TABLE Authorization (
    AuthID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    AssociatedEmployeeID NUMBER NOT NULL,
    Username VARCHAR2(50) UNIQUE NOT NULL,
    Password VARCHAR2(255) NOT NULL,
    Role VARCHAR2(19) NOT NULL CHECK (Role IN ('Admin', 'HR', 'Senior HR Manager')), -- ENUM to VARCHAR2 with CHECK constraint
    FOREIGN KEY (AssociatedEmployeeID) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);
INSERT INTO Departments (DepartmentName)
VALUES
    ('ADMINISTRATION'),
    ('CUSTOMER SERVICE & CORPORATE AFFAIRS'),
    ('LEGAL AFFAIRS'),
    ('FINANCE & ACCOUNTING'),
    ('INFORMATION, COMMUNICATION & TECHNOLOGY');

INSERT INTO Division (DivisionName, DepartmentID)
VALUES
    ('HUMAN RESOURCE MANAGEMENT', 1),
    ('ADMINISTRATION', 1),
    ('RECORDS AND ARCHIVE MANAGEMENT', 1),
    ('PUBLIC RELATIONS AND MEDIA ENGAGEMENT', 2),
    ('STAKEHOLDER & GOVERNMENT RELATIONS', 2),
    ('LEGAL ADVISORY', 3),
    ('CONTRACTS AND AGREEMENTS MANAGEMENT', 3),
    ('SUPPLY CHAIN MANAGEMENT AND PROCUREMENT', 4),
    ('FINANCE', 4),
    ('ACCOUNTING', 4),
    ('IT INFRASTRUCTURE', 5),
    ('SYSTEMS DEVELOPMENT & MAINTENANCE', 5),
    ('REVENUE COLLECTION & MANAGEMENT', 3);