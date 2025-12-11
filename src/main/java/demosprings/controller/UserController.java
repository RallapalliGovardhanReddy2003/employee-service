package demosprings.controller;
import java.util.List;

import demosprings.service.EmailService;
import demosprings.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demosprings.enity.Employee;
import demosprings.service.UserService;

@RestController
@RequestMapping("/User")
@Validated
public class UserController {
	
	
	private final UserService userService;
    private final EmailService emailService;
    private final SmsService smsService;
    @Value("${twilio.from-number}")
    private String from;

    public UserController(UserService userService, EmailService emailService, SmsService smsService) {
        this.userService = userService;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostMapping("/createuser")
    public ResponseEntity<Employee> createUser(@Validated @RequestBody Employee user) {
        Employee created = userService.createUser(user);
        emailService.sendSimple(created.getEmailid(), "registration confirmataion", "successfully registered");
        String sid = smsService.sendSms("+17754025856", created.getMonbno(), "registered employee");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
	@GetMapping
    public ResponseEntity<List<Employee>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Employee> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
	
	 @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateUser(@PathVariable Integer id,
	                                           @Validated @RequestBody Employee user) {
	        return ResponseEntity.ok(userService.updateUser(id, user));
	    }
	 
	
	

	
}

