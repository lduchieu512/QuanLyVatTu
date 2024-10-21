package com.example.medicalsupplieswebsite.controller;

import com.example.medicalsupplieswebsite.dto.EmployeeInfo;
import com.example.medicalsupplieswebsite.dto.InvalidDataException;
import com.example.medicalsupplieswebsite.dto.ValidationError;
import com.example.medicalsupplieswebsite.dto.EmployeeDTO;
import com.example.medicalsupplieswebsite.dto.EmployeeUserDetailDto;
import com.example.medicalsupplieswebsite.entity.Employee;
import com.example.medicalsupplieswebsite.service.IEmployeeService;
import com.example.medicalsupplieswebsite.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService iEmployeeService;

    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @param employeeInfo
     * @param bindingResult
     * @return if info of employee valid return httpStatus.CREATED else return error
     */
    @PostMapping("")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeInfo employeeInfo, BindingResult bindingResult) {
        new EmployeeInfo().validate(employeeInfo, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        } else {
            iEmployeeService.save(employeeInfo);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Employee> getAllEmployee() {
        return iEmployeeService.findAll();
    }

    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @param id
     * @param employeeInfo
     * @param bindingResult
     * @return if info of employee valid return httpStatus.OK else return error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @PathVariable Long id, @RequestBody EmployeeInfo employeeInfo, BindingResult bindingResult) {
        new EmployeeInfo().validate(employeeInfo, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        } else {
            iEmployeeService.updateEmployee(employeeInfo, id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

        /**
         * A0722I1-KhanhNL
         */
        @GetMapping("/detail")
        public ResponseEntity<EmployeeUserDetailDto> getDetail() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            EmployeeUserDetailDto employeeUserDetailDto = iEmployeeService.findUserDetailByUsername(username);

            if (employeeUserDetailDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(employeeUserDetailDto, HttpStatus.OK);
        }

    /**
     * this function could return a list of employee ,that can display all employee or combines search with 3 params
     *
     * @param name
     * @param date
     * @param pos
     * @return list of employee
     */
    @GetMapping("")
    public ResponseEntity<List<Employee>> findAllEmployees(@RequestParam(name = "name",defaultValue = "") String name,
                                                           @RequestParam(name="date",defaultValue = "") String date,
                                                           @RequestParam(name="pos",defaultValue = "") String pos) {
        List<Employee> listEmployee = iEmployeeService.findAllEmWithNameAndDateAndPositions(name, date, pos);
        return new ResponseEntity<>(listEmployee, HttpStatus.OK);
    }


    /**
     * this function could return 1 employee of employee table by id employee
     *
     * @param id_employee
     * @return employee
     */
    @DeleteMapping("/delete/{id_employee}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id_employee) {
        iEmployeeService.deleteEmployee(id_employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDataException(InvalidDataException ex) {
        List<ValidationError> errors1 = ex.getErrors();
        Map<String, String> errors = new HashMap<>();
        errors1.forEach((error) -> {

            String fieldName = error.getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);

    }

    /**
     * this function could delete employee by id employee
     *
     * @param id_employee
     * @return none
     */

    @GetMapping("/{id_employee}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id_employee) {
        Employee employee = iEmployeeService.findEmployeeByID(id_employee);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    /*
     *NhanTQ
     */
    @PatchMapping("/update-employee")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (iEmployeeService.findByUsername(username) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    });
            return ResponseEntity.badRequest().body(errors);
        }
        iEmployeeService.updateEmployeeByFieldsDTO(employeeDTO.getEmployeeName(), employeeDTO.getEmployeeImg(),
                employeeDTO.isGender(), employeeDTO.getDateOfBirth(), employeeDTO.getEmployeeAddress(),
                employeeDTO.getPhone(), employeeDTO.getEmail(), username);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    /*
     *NhanTQ
     */
    @GetMapping("/user-detail-update")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Employee employee = iEmployeeService.findByUsername(username);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
