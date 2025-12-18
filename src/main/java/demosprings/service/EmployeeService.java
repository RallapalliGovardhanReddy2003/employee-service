package demosprings.service;


import java.util.List;

import demosprings.entity.Employee;

public interface EmployeeService {
	
	Employee createUser(Employee user);
	
	Employee updateUser(Integer id, Employee user);
	
	void deleteUser(Integer id);
	
	Employee getUserById(Integer id);
	
	List<Employee> getAllUsers();

	List<Employee> getAllUsersWithDeleted();


	void softDeleteUser(Integer id);

	List<Employee> getEmployeesByLocation(String address);

	List<Employee> getEmployeesByEmployeeId(Integer employeeId);
}


