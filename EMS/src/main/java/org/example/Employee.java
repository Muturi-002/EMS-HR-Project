package org.example;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Employee{
    Employee(){public class Employee {
    // Attributes (Fields)
    private String name;
    private int employeeId;
    private String position;
    private double salary;
    private String department;

    // Constructor
    public Employee(String name, int employeeId, String position, double salary, String department) {
        this.name = name;
        this.employeeId = employeeId;
        this.position = position;
        this.salary = salary;
        this.department = department;
    }

    // Getter and Setter methods for each attribute

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Method to display employee details
    public void displayEmployeeDetails() {
        System.out.println("Employee Details:");
        System.out.println("Name: " + name);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Position: " + position);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
    }

    // Method to calculate annual salary (as an example)
    public double calculateAnnualSalary() {
        return salary * 12;  // Assuming salary is monthly
    }

    // Override toString method for displaying employee information
    @Override
    public String toString() {
        return "Employee [ID=" + employeeId + ", Name=" + name + ", Position=" + position + ", Department=" + department + "]";
    }
    
    // Additional methods or logic as needed (e.g., updating information, promotions, etc.)

    // Main method for testing (optional)
    public static void main(String[] args) {
        Employee employee1 = new Employee("John Doe", 12345, "Software Engineer", 7000, "IT");
        employee1.displayEmployeeDetails();
        System.out.println("Annual Salary: " + employee1.calculateAnnualSalary());
    }
}


    }
    public static void main (String []args){

    }
}
