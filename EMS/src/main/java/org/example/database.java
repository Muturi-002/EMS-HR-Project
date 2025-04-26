// Step 1: We don't create a database in Java code like in SQL. It's handled separately.

// Step 2: Departments table
public class Department {
    private int id;
    private String name;
    private String description;
}

// Step 3: Roles table
public class Role {
    private int id;
    private String title;
    private String description;
}

// Step 4: Employees table
public class Employee {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int departmentId;
    private int roleId;
    private java.sql.Date hireDate;
    private String status; // Should be 'active' or 'inactive'
}

// Step 5: Attendance table
public class Attendance {
    private int id;
    private int employeeId;
    private java.sql.Date date;
    private java.sql.Time clockIn;
    private java.sql.Time clockOut;
    private String status; // 'Present', 'Absent', or 'Leave'
}

// Step 6: Leaves table
public class Leave {
    private int id;
    private int employeeId;
    private String leaveType; // 'Sick', 'Casual', or 'Annual'
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private String reason;
    private String approvalStatus; // 'Pending', 'Approved', or 'Rejected'
}

// Step 7: Payroll table
public class Payroll {
    private int id;
    private int employeeId;
    private java.math.BigDecimal basicSalary;
    private java.math.BigDecimal bonus;
    private java.math.BigDecimal deductions;
    private java.sql.Date payDate;
}

// Step 8: Users table
public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role; // 'HR' or 'Admin'
}
