package demosprings.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import demosprings.entity.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false")
    List<Employee> findAllActiveEmployees();
    @Query("SELECT e FROM Employee e")
    List<Employee> findAll();
}

