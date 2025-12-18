package demosprings.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import demosprings.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false")
    List<Employee> findAllActiveEmployees();
    @Query("SELECT e FROM Employee e")
    List<Employee> findAll();



    @Query(value = """
        SELECT e.*
        FROM employee e
        JOIN addresses a
          ON e.employee_id = a.employee_id
        WHERE a.address = :address
      """,
            nativeQuery = true
    )
    List<Employee> findEmployeesByAddress(@Param("address") String address);




    @Query(value = """
SELECT e.id AS id,
       e.firstname AS firstname,
       e.emailid AS emailid,
       e.is_deleted AS isDeleted,
       a.address AS address,
       a.permanent_address AS permanentAddress
FROM employee e
JOIN addresses a ON e.employee_id = a.employee_id
WHERE e.id = :id
""", nativeQuery = true)
    List<Employee> findEmployeeWithAddress(@Param("id") Integer id);


    List<Employee> findEmployeesByEmployeeId(Integer employeeId);

}

