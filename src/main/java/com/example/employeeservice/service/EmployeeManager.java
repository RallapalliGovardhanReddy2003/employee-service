package com.example.employeeservice.service;



import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.events.EmployeeCreatedEvent;

@Service
public class EmployeeManager {

    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher publisher;

    public EmployeeManager(EmployeeRepository employeeRepository, ApplicationEventPublisher publisher) {
        this.employeeRepository = employeeRepository;
        this.publisher = publisher;
    }

    @Transactional
    public Employee create(Employee e) {
        Employee saved = employeeRepository.save(e);
        // publish event (listener will react AFTER COMMIT)
        publisher.publishEvent(new EmployeeCreatedEvent(saved));
        return saved;
    }

    @Transactional
    public Employee update(Integer id, Employee payload) {
        Employee existing = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        existing.setFirstname(payload.getFirstname());
        existing.setLastname(payload.getLastname());
        existing.setMobileno(payload.getMobileno());
        existing.setEmailid(payload.getEmailid());
        return employeeRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public java.util.List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Integer id) {
    	employeeRepository.deleteById(id);
    }


}
