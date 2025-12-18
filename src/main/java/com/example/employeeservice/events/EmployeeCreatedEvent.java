package com.example.employeeservice.events;


import com.example.employeeservice.entity.Employee;

public class EmployeeCreatedEvent {
    private final Employee employee;
    public EmployeeCreatedEvent(Employee employee) { this.employee = employee; }
    public Employee getEmployee() { return employee; }
}
