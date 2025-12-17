package demosprings.service;

import demosprings.entity.Employee;
import demosprings.exception.ResourceNotFoundException;
import demosprings.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {


    private EmployeeRepository employeeRepository;
    private EmployeeServiceImpl userService;

    @BeforeEach

    void setUp() {
    employeeRepository = mock(EmployeeRepository.class);
    userService = new EmployeeServiceImpl(employeeRepository);
}

    @Test
    void testCreateUser() {
        Employee user = new Employee();
        user.setFirstname("R Govardhan Reddy");
        user.setLastname("rallapalli");

        when(employeeRepository.save(user)).thenReturn(user);

        Employee createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        verify(employeeRepository, times(1)).save(user);
    }
    @Test
    void testCreateUser_Exception() {
        Employee user =new Employee();
        user.setFirstname("R Govardhan Reddy");
        user.setLastname("rallaplli");

        when(employeeRepository.save(user)).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(RuntimeException.class, () ->{
            userService.createUser(user);
        });
    }

    @Test
    void testUpdateUser() {
        Employee existingUser = new Employee();
        existingUser.setId(1);
        existingUser.setFirstname("R Govardhan Reddy");

        Employee updatedUser = new Employee();
        updatedUser.setFirstname("R Govardhan");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(employeeRepository.save(existingUser)).thenReturn(existingUser);

        Employee result = userService.updateUser(1, updatedUser);

        assertEquals("R Govardhan", result.getFirstname());
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(existingUser);
    }
    @Test
    void testUpdateUser_NotFound() {
        Employee updatedUser =new Employee();
        updatedUser.setFirstname("R Govardhan");

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1,updatedUser);
        });
    }
    @Test
    void testDeleteUser() {
        Employee user = new Employee();
        user.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(employeeRepository, times(1)).delete(user);
    }
    @Test
    void testDeleteUser_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(1);
        });
    }
    @Test
    void testGetAllUsers() {
        userService.getAllUsers();

        verify(employeeRepository, times(1)).findAllActiveEmployees();
    }
    @Test
    void testGetAllUsersWithDeleted() {
        userService.getAllUsersWithDeleted();
        verify(employeeRepository,times(1)).findAll();
    }


    @Test
    void testGetUserById() {
        Employee user = new Employee();
        user.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(user));

        Employee result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(employeeRepository, times(1)).findById(1);
    }
    @Test
    void testGetUserById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->{
            userService.getUserById(1);
        });


    }

    @Test
    void testHardDelete() {
        doNothing().when(employeeRepository).deleteById(1);

        userService.hardDelete(1);

        verify(employeeRepository, times(1)).deleteById(1);
    }
    @Test
    void testHardDelete_Exception() {
        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteById(1);
        assertThrows(RuntimeException.class, () -> {
            userService.hardDelete(1);
        });
    }

    @Test
    void testSoftDeleteUser() {
        Employee user = new Employee();
        user.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(user));
        when(employeeRepository.save(user)).thenReturn(user);

        userService.softDeleteUser(1);

        assertTrue(user.isDeleted());
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(user);
    }

    @Test
    void testSoftDeleteUser_NotFound(){
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.softDeleteUser(1);
        });
    }
}