package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.dto.EmployeeInfo;
import com.example.medicalsupplieswebsite.dto.EmployeeUserDetailDto;
import com.example.medicalsupplieswebsite.entity.Employee;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface IEmployeeService extends IService<Employee>{


    Employee findByUsername(String username);
    List<Employee> findAll();

    /**
     * A0722I1-KhanhNL
     */
    EmployeeUserDetailDto findUserDetailByUsername(String username);
    void save(EmployeeInfo employeeInfo);

    List<Employee> findByPhone(String phone);

    List<Employee> findByIdCard(String idCard);

    List<Employee> findByEmail(String email);

    void updateEmployee(EmployeeInfo employeeInfo, Long id);
    /**
     * this function could return a list of employee ,that can display all employee or combines search with 3 params
     * @param name
     * @param date
     * @param position
     * @return list of employee
     */
    List<Employee> findAllEmWithNameAndDateAndPositions(String name, String date, String position);
    /**
     * this function could return 1 employee of employee table by id employee
     * @param id
     * @return employee
     */
    void deleteEmployee(Long id);

    /**
     * this function could delete employee by id employee
     * @param id
     * @return none
     */
    Employee findEmployeeByID(Long id);

    Optional<Employee> findEmployeeIdByUserName(String userName);
    Employee findEmployeeByUserName(String userName);

    /*
     * NhanTQ
     */

    void updateEmployeeByFieldsDTO(String employeeName, String employeeImg, boolean gender, Date dateOfBirth, String employeeAddress, String phone, String email, String username);
}