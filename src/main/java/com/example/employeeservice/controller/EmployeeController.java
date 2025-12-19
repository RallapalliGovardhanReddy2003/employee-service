package com.example.employeeservice.controller;
import java.util.List;


import com.example.employeeservice.service.EmailService;
import com.example.employeeservice.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {


	private final EmployeeService employeeService;
    private final EmailService emailService;
    private final SmsService smsService;
    @Value("${twilio.from-number}")
    private String from;

    public EmployeeController(EmployeeService employeeService, EmailService emailService, SmsService smsService) {
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostMapping("/createuser")
    public ResponseEntity<Employee> createUser(@Validated @RequestBody Employee user,
                                               @RequestParam(defaultValue = "false") boolean sendEmail,
                                               @RequestParam(defaultValue = "false") boolean sendSms) {
        Employee created = employeeService.createUser(user);
        if(sendEmail){
        emailService.sendSimple(created.getEmailid(), "registration confirmataion",
                "successfully registered");}
        if(sendSms){
        String sid = smsService.sendSms("+17754025856", created.getMobileno(), "registered employee");
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
	@GetMapping("/getallusers")
    public ResponseEntity<List<Employee>> getAllUsers() {
        return ResponseEntity.ok(employeeService.getAllUsers());
    }


	@GetMapping("/getuser/{id}")
    public ResponseEntity<Employee> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getUserById(id));
    }

	 @PutMapping("/updateuser/{id}")
	    public ResponseEntity<Employee> updateUser(@PathVariable Integer id,
	                                           @Validated @RequestBody Employee user) {
	        return ResponseEntity.ok(employeeService.updateUser(id, user));
	    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<String> hardDelete(@PathVariable Integer id){
        employeeService.deleteUser(id);
       return ResponseEntity.ok("deleted successfully");
    }
    @DeleteMapping ("/softdelete/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Integer id){
        employeeService.softDeleteUser(id);
       return ResponseEntity.ok("deleted successfully");

    }
    @GetMapping("/getalluserwithdeleted")
    public ResponseEntity<List<Employee>> getAllUsersWithDeleted(){
        return ResponseEntity.ok(employeeService.getAllUsersWithDeleted());
    }

    @GetMapping("/by-location/{address}")
    public ResponseEntity<List<Employee>> getEmployeesByLocation(@PathVariable String address){
        return ResponseEntity.ok(employeeService.getEmployeesByLocation(address));
    }
    @GetMapping("/by-employeeid/{employeeId}")
    public ResponseEntity<List<Employee>> getEmployeesByEmployeeId(@PathVariable Integer employeeId){
        return ResponseEntity.ok(employeeService.getEmployeesByEmployeeId(employeeId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeByIdSimple(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getUserById(id));
    }


    @GetMapping("/employees-with-address/{id}/details")
    public ResponseEntity<List<Employee>> getEmployeesWithAddress(@PathVariable Integer id) {
        return ResponseEntity.ok( employeeService.findEmployeeWithAddress(id));
    }







}

