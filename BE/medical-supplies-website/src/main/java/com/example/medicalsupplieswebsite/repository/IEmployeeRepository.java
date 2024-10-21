package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {



    /**
     * Create by: PhongTD
     * Date create: 12/07/2023
     * @param employeeName
     * @param email
     * @param phone
     * @param employeeAddress
     * @param gender
     * @param idCard
     * @param dateOfBirth
     * @param position
     * @param id
     */
    @Modifying
    @Query("UPDATE Employee SET employeeName = ?1, email = ?2, phone = ?3, employeeAddress = ?4, gender = ?5, idCard = ?6," +
            " dateOfBirth = ?7,employeeImg = ?8 ,position = ?9 WHERE employeeId = ?10")
    void updateEmployee(String employeeName, String email, String phone, String employeeAddress, Boolean gender,
                        String idCard, Date dateOfBirth, String avatar, Position position, Long id);

    @Query("SELECT employee FROM Employee employee WHERE employee.email = ?1")
    List<Employee> findAllByEmail(String email);

    @Query("SELECT employee FROM Employee employee WHERE employee.phone = ?1")
    List<Employee> findAllByPhone(String phone);

    @Query("SELECT employee FROM Employee employee WHERE employee.idCard = ?1")
    List<Employee> findAllByIdCard(String idCard);

    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @return Employee was found by id
     */
    @Query(value =
            "select e.employee_id, e.employee_code, e.employee_name, e.email, e.phone, " +
                    "e.employee_address, e.gender, e.date_of_birth, e.id_card, e.salary, e.employee_img, " +
                    "e.is_enable, p.position_id, a.account_id " +
                    "from employee e " +
                    "inner join position p on e.position_id = p.position_id " +
                    "inner join account a on e.account_id = a.account_id " +
                    "where (e.is_enable = true) and (a.is_enable = true) and (a.username = :username)",
            nativeQuery = true)
    Optional<Employee> findByUsername(@Param("username") String username);

    /**
     * A0722I1-KhanhNL
     */
    @Query(value =
            "select e.employee_id, e.employee_code, e.employee_name, e.phone, " +
                    "e.employee_address, e.gender, e.date_of_birth, e.id_card, e.salary, e.employee_img, " +
                    "e.is_enable, p.position_name, a.username, a.email " +
                    "from employee e " +
                    "inner join position p on e.position_id = p.position_id " +
                    "inner join account a on e.account_id = a.account_id " +
                    "where (e.is_enable = true) and (a.is_enable = true) and (a.username = :username)",
            nativeQuery = true)
    Optional<Tuple> findUserDetailByUsername(@Param("username") String username);

    /**
     * this function could return a list of employee ,that can display all employee or combines search with 3 params
     * @param nameEmployee
     * @param nameEmployee
     * @param position
     * @return list of employee
     */
    @Query(value = "SELECT employee.employee_id, " +
            "date_of_birth, employee_address," +
            " employee_img, employee_name, gender, " +
            "employee.is_enable, salary, employee.account_id, " +
            "employee.position_id, position_name, username,employee.email," +
            "employee_code,id_card,phone,employee.salary\n" +
            "FROM employee\n" +
            "LEFT JOIN account ON employee.account_id = account.account_id\n" +
            "LEFT JOIN position ON employee.position_id = position.position_id\n" +
            "WHERE employee_name LIKE %?1% \n" +
            "  AND date_of_birth LIKE %?2% \n" +
            "  AND position_name LIKE %?3% \n" +
            "  AND employee.is_enable = true\n" +
            "LIMIT 0, 300;"
            ,nativeQuery = true)
    List<Employee> findAllByNameAndDobAndAndPosition(String nameEmployee, String dof, String position);
    /**
     * this function could return 1 employee of employee table by id employee
     * @param id
     * @return employee
     */
    @Modifying
    @Query(value = "UPDATE employee \n" +
            "SET is_enable = false \n" +
            "WHERE employee_id = ?1 AND is_enable = true; \n",nativeQuery = true)
    void deleteEmployeeByID(Long id);

    /**
     * this function could delete employee by id employee
     * @param id
     * @return none
     */
    @Query(value = "SELECT employee.employee_id, " +
            "date_of_birth, employee_address, employee_img," +
            " employee_name, gender, employee.is_enable, salary, " +
            "employee.account_id, employee.position_id, position_name, " +
            "username,employee.email,employee_code,id_card,phone\n" +
            "FROM employee\n" +
            "LEFT JOIN account ON employee.account_id = account.account_id\n" +
            "LEFT JOIN position ON employee.position_id = position.position_id\n" +
            "WHERE employee.employee_id = ? \n" +
            "  AND employee.is_enable = true \n" +
            "LIMIT 0, 300;",nativeQuery = true)
    Employee getEmployeeById(Long id);

    /*PhucND tìm kiếm employeeId theo username*/
    @Query(value = "select employee_id,date_of_birth,employee_address,employee_img,employee_name,gender,e.is_enable,salary,e.account_id,position_id,e.email,employee_code,id_card,phone  from employee as e inner join account as a on e.account_id = a.account_id where username = ?1", nativeQuery = true)
    Optional<Employee> findEmployeeIdByUserName(String userName);
    @Query(value = "select employee_id,date_of_birth,employee_address,employee_img,employee_name,gender,e.is_enable,salary,e.account_id,position_id,e.email,employee_code,id_card,phone  from employee as e inner join account as a on e.account_id = a.account_id where username = ?1", nativeQuery = true)
    Optional<Employee> findEmployeeByUserName(String userName);

    @Modifying
    @Query(value = "UPDATE employee e " +
            "inner join account a using(account_id)" +
            "SET e.employee_name= :employee_name,e.employee_img= :employee_img, " +
            "e.gender =:gender, e.date_of_birth =:date_of_birth, e.employee_address =:employee_address," +
            "e.phone =:phone, a.email = :email " +
            "where (e.is_enable = true) and (a.is_enable = true) and (a.username = :username)",
            nativeQuery = true)
    void updateEmployeeDto(@Param("employee_name") String name,
                           @Param("employee_img") String employeeImg,
                           @Param("gender") boolean gender,
                           @Param("date_of_birth") Date date,
                           @Param("employee_address") String employeeAddress,
                           @Param("phone") String phone,
                           @Param("email") String email,
                           @Param("username") String username);
}