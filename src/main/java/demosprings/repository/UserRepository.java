package demosprings.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import demosprings.enity.Employee;

public interface UserRepository  extends JpaRepository<Employee,Integer>{
	

}

