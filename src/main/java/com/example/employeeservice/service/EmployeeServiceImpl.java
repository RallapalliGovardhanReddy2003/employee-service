package com.example.employeeservice.service;

import java.util.List;

import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employeeservice.entity.Employee;

@Service
@Transactional

public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
		
	}
	
	
	public Employee createUser(Employee user) {
		logger.info("creating user: "+user.getFirstname(),user.getLastname());
		try {
			Employee saveUser = employeeRepository.save(user);
			logger.info("user created successfully with id: " + saveUser.getId());
			return saveUser;
		} catch (Exception ex){
			logger.error("error ocuurred while creating user: " +ex.getMessage());
			throw ex;
		}
	}
	@Override
	public Employee updateUser( Integer id ,Employee user) {
		logger.info("Updating user with id: {}", id);
		Employee existing = employeeRepository.findById(id).
				orElseThrow(() -> {
					logger.error("user not found with id: " +id);
					return new ResourceNotFoundException(" user not found"+id);
				});
		
		existing.setFirstname(user.getFirstname());
		existing.setLastname(user.getLastname());
		existing.setMobileno(user.getMobileno());
		existing.setEmailid(user.getEmailid());


		Employee updatedUser = employeeRepository.save(existing);
		logger.info("User updated successfully with id: {}", updatedUser.getId());
		return updatedUser;
	}
	
	public void deleteUser(Integer id) {
		logger.info("Deleting user with id: {}", id);
		Employee existing = employeeRepository.findById(id).
				orElseThrow(()-> {
					logger.error("User not found with id: {}", id);
					return new ResourceNotFoundException("User not found with id: " + id);

				});
		employeeRepository.delete(existing);
		logger.info("User deleted successfully with id: {}", id);
	}
	public Employee getUserById(Integer id) {
		logger.info("Fetching user with id: {}", id);
		return employeeRepository.findById(id).
				orElseThrow(()->{
					logger.error("User not found with id: {}", id);
					return new ResourceNotFoundException("User not found with id: " + id);
				});
	}
	
	public List<Employee> getAllUsers(){
		logger.info("Fetching all active users");
		return employeeRepository.findAllActiveEmployees();
	}
	public List<Employee> getAllUsersWithDeleted(){
		logger.info("Fetching all users, including soft-deleted ones");
		return employeeRepository.findAll();
	}

	public void hardDelete(Integer id){
		logger.info("Hard deleting user with id: {}", id);
		try {
			employeeRepository.deleteById(id);
			logger.info("User hard deleted successfully with id: {}", id);
		} catch (Exception ex) {
			logger.error("Error occurred while hard deleting user with id: {}", id, ex);
			throw ex;
		}
	}

	public void softDeleteUser(Integer id) {
			logger.info("Soft deleting user with id: {}", id);
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> {
						logger.error("User not found with id: {}", id);
						return new RuntimeException("Employee not found with id: " + id);
					});
			employee.setDeleted(true);
			employeeRepository.save(employee);
		logger.info("User soft-deleted successfully with id: {}", id);
	}

	public List<Employee> getEmployeesByLocation(String address) {
		logger.info("Fetching employees by location: {}", address);
		return employeeRepository.findEmployeesByAddress(address);
	}
	public List<Employee> getEmployeesByEmployeeId(Integer employeeId) {
		logger.info("Fetching employees by employeeId: {}", employeeId);
		return employeeRepository.findEmployeesByEmployeeId(employeeId);
	}

	public List<Employee> findEmployeeWithAddress(Integer id) {
		logger.info("Fetching employees with address for Id: {}", id);
		return employeeRepository.findEmployeeWithAddress(id);

	}

}

