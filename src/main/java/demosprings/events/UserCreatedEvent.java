package demosprings.events;


import demosprings.enity.Employee;

public class UserCreatedEvent {
    private final Employee employee;
    public UserCreatedEvent(Employee employee) { this.employee = employee; }
    public Employee getEmployee() { return employee; }
}
